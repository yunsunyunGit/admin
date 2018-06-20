package com.yunsunyun.point.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtSms;
import com.yunsunyun.point.service.IPtSmsService;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.google.common.base.Strings;
import com.kingnode.diva.web.Servlets;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.text.SimpleDateFormat;
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
@RequestMapping("/sms/list")
public class PtSmsController {

    @Autowired
    private IPtSmsService iPtSmsService;

    private static Logger logger = LoggerFactory.getLogger(PtSmsController.class);

    @RequiresPermissions("sms-list")
    @RequestMapping(method = RequestMethod.GET)
    public String smsList(){
        return "sms/smsList";
    }

    @RequiresPermissions("sms-list")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtSms> getPtSmsList(DataTable<PtSms> dt, ServletRequest request){
        try {
            Page<PtSms> page = new Page<>();
            page.setCurrent(dt.pageNo()+1);
            page.setSize(dt.getiDisplayLength());
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
            EntityWrapper<PtSms> wrapper = new EntityWrapper<>();

            if (searchParams != null && searchParams.size() != 0) {
                if (searchParams.containsKey("LIKE_content") && !Strings.isNullOrEmpty(searchParams.get("LIKE_content").toString().trim())) {
                    wrapper.like("content",searchParams.get("LIKE_content").toString().trim());
                }
                if (searchParams.containsKey("LIKE_senderId") && !Strings.isNullOrEmpty(searchParams.get("LIKE_senderId").toString().trim())) {
                    wrapper.like("sender_Id",searchParams.get("LIKE_senderId").toString().trim());
                }
                if (searchParams.containsKey("LIKE_senderName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_senderName").toString().trim())) {
                    wrapper.like("sender_Name",searchParams.get("LIKE_senderName").toString().trim());
                }
                if (searchParams.containsKey("LIKE_receiverId") && !Strings.isNullOrEmpty(searchParams.get("LIKE_receiverId").toString().trim())) {
                    wrapper.like("receiver_Id",searchParams.get("LIKE_receiverId").toString().trim());
                }
                if (searchParams.containsKey("LIKE_receiverName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_receiverName").toString().trim())) {
                    wrapper.like("receiver_Name",searchParams.get("LIKE_receiverName").toString().trim());
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
            page = iPtSmsService.selectPage(page,wrapper);
            dt.setiTotalDisplayRecords(page.getTotal());
            dt.setAaData(page.getRecords());
        }catch (Exception e){
            logger.error("删除模板错误信息：{}", e);
        }
        return  dt;
    }



}
