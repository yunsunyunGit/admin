package com.yunsunyun.point.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtTip;
import com.yunsunyun.point.service.IPtTipService;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.yunsunyun.xsimple.entity.system.KnRole;
import com.yunsunyun.xsimple.entity.system.KnUser;
import com.yunsunyun.xsimple.service.system.ResourceService;
import com.google.common.base.Strings;
import com.kingnode.diva.web.Servlets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@Controller
@RequestMapping("/sms/tip")
public class PtTipController {

    @Autowired
    private IPtTipService iPtTipService;
    @Autowired
    private ResourceService resourceService;

    private static Logger logger = LoggerFactory.getLogger(PtSmsController.class);

    @RequiresPermissions("sms-tip")
    @RequestMapping(method = RequestMethod.GET)
    public String tipList(){
        return "sms/smsTipList";
    }

    @RequiresPermissions("sms-tip")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtTip> getTipList(DataTable<PtTip> dt, ServletRequest request){
        try {
            Page<PtTip> page = new Page<>();
            page.setCurrent(dt.pageNo()+1);
            page.setSize(dt.getiDisplayLength());
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
            EntityWrapper<PtTip> wrapper = new EntityWrapper<>();

            //判断当前的用户角色
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
            List<KnRole> knRoleList =  knUser.getRole();
            for (KnRole knRole:knRoleList){
                if("merchant".equals(knRole.getCode())){
                    searchParams.put("LIKE_receiverId",loginUser.getId());
                    break;
                }
            }

            if (searchParams != null && searchParams.size() != 0) {
                if (searchParams.containsKey("LIKE_content") && !Strings.isNullOrEmpty(searchParams.get("LIKE_content").toString().trim())) {
                    wrapper.like("content",searchParams.get("LIKE_content").toString().trim());
                }
                if (searchParams.containsKey("LIKE_senderId") && !Strings.isNullOrEmpty(searchParams.get("LIKE_senderId").toString().trim())) {
                    wrapper.like("sender_id",searchParams.get("LIKE_senderId").toString().trim());
                }
                if (searchParams.containsKey("LIKE_senderName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_senderName").toString().trim())) {
                    wrapper.like("sender_name",searchParams.get("LIKE_senderName").toString().trim());
                }
                if (searchParams.containsKey("LIKE_receiverName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_receiverName").toString().trim())) {
                    wrapper.like("receiver_name",searchParams.get("LIKE_receiverName").toString().trim());
                }
                if (searchParams.containsKey("LIKE_receiverId") && !Strings.isNullOrEmpty(searchParams.get("LIKE_receiverId").toString().trim())) {
                    wrapper.like("receiver_id",searchParams.get("LIKE_receiverId").toString().trim());
                }
                if (searchParams.containsKey("LIKE_sendTime") && !Strings.isNullOrEmpty(searchParams.get("LIKE_sendTime").toString().trim())) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    wrapper.like("send_time",dateFormat.parse(searchParams.get("LIKE_sendTime").toString().trim()).getTime()+"");
                }
                if (searchParams.containsKey("LIKE_receiveTime") && !Strings.isNullOrEmpty(searchParams.get("LIKE_receiveTime").toString().trim())) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    wrapper.like("receive_time",dateFormat.parse(searchParams.get("LIKE_receiveTime").toString().trim()).getTime()+"");
                }
                if (searchParams.containsKey("EQ_readStatus") && !Strings.isNullOrEmpty(searchParams.get("EQ_readStatus").toString().trim())) {
                    wrapper.eq("read_status",searchParams.get("EQ_readStatus").toString().trim());
                }
            }
            wrapper.orderBy("send_time",false);
            page = iPtTipService.selectPage(page,wrapper);
            dt.setiTotalDisplayRecords(page.getTotal());
            dt.setAaData(page.getRecords());
        }catch (Exception e){
            logger.error("删除模板错误信息：{}", e);
        }
        return  dt;
    }


    @RequiresPermissions("sms-tip")
    @RequestMapping(value = "/set-read",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> setRead(Long id){
        Map<String,Object> map = new HashMap<>();
        try {
            PtTip ptTip = new PtTip();
            ptTip.setReadStatus(Setting.readStatus.haveRead.getReadCode());
            ptTip.setReceiveTime(new Date().getTime());
            boolean update = iPtTipService.update(ptTip, new EntityWrapper<PtTip>().eq("id", id));
            map.put("stat", update);
            if (update){
                map.put("msg", "操作成功！");
            }else{
                map.put("msg", "操作失败！");
            }
        }catch (Exception e){
            logger.error("设置已读失败：{}", e);
            map.put("stat", false);
            map.put("msg", "操作失败！");
        }
        return  map;
    }


    @RequiresPermissions("sms-tip")
    @RequestMapping(value = "/set-read-list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> setReadList(@RequestParam(value = "ids") List<Long> ids){
        Map<String,Object> map = new HashMap<>();
       try {
           boolean flag = true;
           for (Long id : ids) {
               PtTip ptTip = new PtTip();
               ptTip.setReadStatus(Setting.readStatus.haveRead.getReadCode());
               ptTip.setReceiveTime(new Date().getTime());
               boolean update = iPtTipService.update(ptTip, new EntityWrapper<PtTip>().eq("id", id));
               if (!update){
                   flag = false;
               }
           }
           map.put("stat", flag);
           if (flag){
               map.put("msg", "操作成功！");
           }else {
               map.put("msg", "操作失败！");
           }
       }catch (Exception e){
           logger.error("设置已读失败：{}", e);
           map.put("stat", false);
           map.put("msg", "操作失败！");
       }
        return  map;
    }


}
