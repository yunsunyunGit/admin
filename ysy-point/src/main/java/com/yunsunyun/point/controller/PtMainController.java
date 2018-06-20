package com.yunsunyun.point.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunsunyun.point.po.PtGoods;
import com.yunsunyun.point.po.PtGoodsSnapshot;
import com.yunsunyun.point.po.PtTip;
import com.yunsunyun.point.service.IPtGoodsService;
import com.yunsunyun.point.service.IPtGoodsSnapshotService;
import com.yunsunyun.point.service.IPtOrderService;
import com.yunsunyun.point.service.IPtTipService;
import com.yunsunyun.point.vo.PtOrderVo;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.entity.system.KnEmployee;
import com.yunsunyun.xsimple.entity.system.KnRole;
import com.yunsunyun.xsimple.entity.system.KnUser;
import com.yunsunyun.xsimple.service.system.EmployeeService;
import com.yunsunyun.xsimple.service.system.ResourceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/point/main")
public class PtMainController {

    @Autowired
    private IPtGoodsSnapshotService iPtGoodsSnapshotService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private IPtGoodsService iPtGoodsService;
    @Autowired
    private IPtTipService iPtTipService;
    @Autowired
    private IPtOrderService iPtOrderService;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PtMainController.class);

    @RequiresPermissions("main")
    @RequestMapping(value = "/get-num-list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getNumList(){
        Map<String,Object> map = new HashMap<>();

        try {
            //判断当前的用户角色
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
            List<KnRole> knRoleList =  knUser.getRole();
            for (KnRole knRole:knRoleList){
                if("merchant".equals(knRole.getCode())){
                    Map parmes = new HashMap<String, Object>();
                    parmes.put("EQ_merchant",loginUser.getId());
                    List<PtGoods> goodsPage = iPtGoodsService.getGoodsPage(parmes, (long) 0, Setting.AuditState.audited.getAuditStateCode());
                    List<PtTip> tipList = iPtTipService.selectList(new EntityWrapper<PtTip>().eq("receiver_id", loginUser.getId()));
                    map.put("stat",true);
                    map.put("msg","获取成功！");
                    map.put("num1",goodsPage.size());
                    map.put("num2",tipList.size());
                    break;
                }else if("platformma".equals(knRole.getCode())||"Admin".equals(knRole.getCode())){
                    List<PtGoodsSnapshot> goodsSnapshotList = iPtGoodsSnapshotService.getGoodsSnapshotPage(new HashMap<String, Object>());
                    List<KnEmployee> merchantList = employeeService.findMerchantEnable();
                    map.put("stat",true);
                    map.put("msg","获取成功！");
                    map.put("num1",goodsSnapshotList.size());
                    map.put("num2",merchantList.size());
                    break;
                }
            }
        }catch (Exception e){
            logger.error("获取失败！", e);
            map.put("stat",false);
            map.put("msg","获取失败！");
        }
        return map;
    }

    @RequiresPermissions("main")
    @RequestMapping(value = "/get-order-num",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getOrderNum(){
        Map<String,Object> map = new HashMap<>();
        try {
            ArrayList<String> dateList = new ArrayList<>();
            ArrayList<Integer> orderCountList = new ArrayList<>();

            //判断当前的用户角色
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
            List<KnRole> knRoleList =  knUser.getRole();
            for (KnRole knRole:knRoleList){
                if("merchant".equals(knRole.getCode())) {
                    map.put("EQ_merchantId",loginUser.getId());
                    break;
                }
            }

            for (int i=0;i<7;i++) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String pastDate = getPastDate(i);
                long timeStart = format.parse(pastDate).getTime();
                long timeEnd = format.parse(pastDate).getTime()+86399000;
                map.put("EQ_orderStartTime",timeStart);
                map.put("EQ_orderEndTime",timeEnd);
                List<PtOrderVo> orderVoList = iPtOrderService.getOrderVoList(map);
                int orderCount = 0;
                if (orderVoList!=null){
                    orderCount = orderVoList.size();
                }
                dateList.add(pastDate);
                orderCountList.add(orderCount);
            }
            map.clear();
            Collections.reverse(dateList);
            Collections.reverse(orderCountList);
            map.put("dateList",dateList);
            map.put("orderCountList",orderCountList);
            map.put("stat",true);
            map.put("msg","获取成功！");
            return  map;
        }catch (Exception e){
            logger.error("获取失败！", e);
            map.clear();
            map.put("stat",false);
            map.put("msg","获取失败！");
        }
        return  map;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    private  String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }



}
