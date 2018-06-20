package com.yunsunyun.point.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.service.IPtRedemptionRecordService;
import com.yunsunyun.point.vo.PtRedemptionRecordVo;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.yunsunyun.xsimple.entity.system.KnRole;
import com.yunsunyun.xsimple.entity.system.KnUser;
import com.yunsunyun.xsimple.service.system.ResourceService;
import com.yunsunyun.xsimple.util.PathUtil;
import com.yunsunyun.xsimple.util.excel.DownExcel;
import com.kingnode.diva.web.Servlets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2018-04-12
 */
@Controller
@RequestMapping("/goods/record")
public class PtRedemptionRecordController {

    @Autowired
    private IPtRedemptionRecordService iPtRedemptionRecordService;

    @Autowired
    private ResourceService resourceService;

    private static Logger logger = LoggerFactory.getLogger(PtRedemptionRecordController.class);

    @RequiresPermissions("goods-record")
    @RequestMapping(method = RequestMethod.GET)
    public String toRedemptionRecordList() {

        return "goods/goodsRecordList";
    }

    @RequiresPermissions("goods-record")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtRedemptionRecordVo> getRedemptionRecordList(DataTable<PtRedemptionRecordVo> dt, ServletRequest request) {
        try {
            Page<PtRedemptionRecordVo> page = new Page<>();
            page.setCurrent(dt.pageNo() + 1);
            page.setSize(dt.getiDisplayLength());
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");


            //判断当前的用户角色
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
            List<KnRole> knRoleList =  knUser.getRole();
            for (KnRole knRole:knRoleList){
                if("merchant".equals(knRole.getCode())){
                    searchParams.put("EQ_merchant",loginUser.getId());
                    break;
                }
            }

            page = iPtRedemptionRecordService.getRecordlist(page, searchParams);
            dt.setiTotalDisplayRecords(page.getTotal());
            dt.setAaData(page.getRecords());
        } catch (Exception e) {
            logger.error("获取商品兑换记录错误", e);
        }
        return dt;
    }

    @RequiresPermissions("goods-record")
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void exportRecord(HttpServletResponse response, ServletRequest request) {
        try {
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
            List<PtRedemptionRecordVo> recordlist = iPtRedemptionRecordService.getRecordlist(searchParams);
            Map<String, List<String[]>> recordlist2Map = recordlist2Map(recordlist);
            DownExcel downExcel =  DownExcel.getInstall();
            downExcel.downLoadFile(response, downExcel.exportXlsExcel(recordlist2Map, PathUtil.getRootPath()+ Setting.BASEADDRESS+Setting.excelAddress,"积分兑换详情"), "积分兑换详情", true);

        } catch (Exception e) {
            logger.error("获取商品兑换记录错误", e);
        }
    }

    public Map<String, List<String[]>> recordlist2Map(List<PtRedemptionRecordVo> recordlist) throws Exception {
        Map<String, List<String[]>> map = new HashMap<>();
        ArrayList<String[]> arrayList = new ArrayList<>();
        arrayList.add(new String[]{"商城名称", "会员名称", "会员手机", "商家名称", "兑换商品", "兑换数量", "总积分", "兑换时间"});
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (PtRedemptionRecordVo recordVo : recordlist) {
            arrayList.add(new String[]{recordVo.getMallName(), recordVo.getMemberName() + "(" + recordVo.getMemberId() + ")", recordVo.getMemberPhone(), recordVo.getMerchantName(), recordVo.getGoodsName(), recordVo.getGoodsNum() + "", (recordVo.getGoodsNum() * recordVo.getGoodsPoint()) + "", sdf.format(recordVo.getCreateTime()).toString()});
        }
        map.put("积分兑换详情",arrayList);
        return  map;
    }


}
