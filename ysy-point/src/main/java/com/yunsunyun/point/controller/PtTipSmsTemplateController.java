package com.yunsunyun.point.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtTipSmsTemplate;
import com.yunsunyun.point.service.IPtTipSmsTemplateService;
import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.google.common.base.Strings;
import com.kingnode.diva.web.Servlets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2018-04-04
 */
@Controller
@RequestMapping("/sms/template")
public class PtTipSmsTemplateController {

    @Autowired
    private IPtTipSmsTemplateService iPtTipSmsTemplateService;

    private static Logger logger = LoggerFactory.getLogger(PtMallController.class);

    @RequiresPermissions("sms-template")
    @RequestMapping(method = RequestMethod.GET)
    public String tempList(){
        return "sms/smsTemplateList";
    }

    @RequiresPermissions("sms-template")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtTipSmsTemplate> getTempList(DataTable<PtTipSmsTemplate> dt,ServletRequest request){
        Page<PtTipSmsTemplate> page = new Page<>();
        page.setCurrent(dt.pageNo()+1);
        page.setSize(dt.getiDisplayLength());
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Wrapper<PtTipSmsTemplate> wrapper = new EntityWrapper<>();

        if (searchParams != null && searchParams.size() != 0) {
            // 模板名
            if (searchParams.containsKey("LIKE_name") && !Strings.isNullOrEmpty(searchParams.get("LIKE_name").toString().trim())) {
                wrapper.like("name",searchParams.get("LIKE_name").toString().trim());
            }
         /*   // 短信类型
            if (searchParams.containsKey("EQ_type") && !Strings.isNullOrEmpty(searchParams.get("EQ_type").toString().trim())) {
                wrapper.eq("type",searchParams.get("EQ_type").toString().trim());
            }*/
            // 内容
            if (searchParams.containsKey("LIKE_content") && !Strings.isNullOrEmpty(searchParams.get("LIKE_content").toString().trim())) {
                wrapper.like("content",searchParams.get("LIKE_content").toString().trim());
            }
            if (searchParams.containsKey("EQ_actionType") && !Strings.isNullOrEmpty(searchParams.get("EQ_actionType").toString().trim())) {
                wrapper.eq("action_type",searchParams.get("EQ_actionType").toString().trim());
            }
            //默认
            if (searchParams.containsKey("EQ_isDefault") && !Strings.isNullOrEmpty(searchParams.get("EQ_isDefault").toString().trim())) {
                wrapper.eq("is_default",searchParams.get("EQ_isDefault").toString().trim());
            }
        }
        wrapper.orderBy(true,"is_default",false);
        page = iPtTipSmsTemplateService.selectPage(page,wrapper);
        dt.setiTotalDisplayRecords(page.getTotal());
        dt.setAaData(page.getRecords());

        return dt;
    }

    @RequiresPermissions("sms-template")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteTemp(@RequestParam("id") Long id){
        Map<String,Object> map = new HashMap<>();
        try{
            EntityWrapper<PtTipSmsTemplate>  tipSmsTemplateEntityWrapper   = new EntityWrapper<>();
            tipSmsTemplateEntityWrapper.eq("id",id);
            boolean flag = iPtTipSmsTemplateService.delete(tipSmsTemplateEntityWrapper);
            map.put("stat",flag);
        }catch (Exception e){
            map.put("stat", false);
            map.put("msg","删除出错，请联系管理员");
            logger.error("删除模板错误信息：{}", e);
        }
        return  map;
    }


    @RequiresPermissions("sms-template")
    @RequestMapping(value = "/set-default", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> setDefault(@RequestParam("id") Long id,@RequestParam("actionType") Integer actionType){
        Map<String,Object> map = new HashMap<>();
        try {
            EntityWrapper<PtTipSmsTemplate>  tipSmsTemplateEntityWrapper = new EntityWrapper<>();
            PtTipSmsTemplate ptTipSmsTemplate = new PtTipSmsTemplate();
            ptTipSmsTemplate.setIsDefault(0);
           /* tipSmsTemplateEntityWrapper.eq("type",type);*/
            tipSmsTemplateEntityWrapper.eq("action_type",actionType);
            boolean flag1 = iPtTipSmsTemplateService.update(ptTipSmsTemplate,tipSmsTemplateEntityWrapper);

            ptTipSmsTemplate.setIsDefault(1);
            tipSmsTemplateEntityWrapper = new EntityWrapper<>();
            tipSmsTemplateEntityWrapper.eq("id",id);
            boolean flag = iPtTipSmsTemplateService.update(ptTipSmsTemplate,tipSmsTemplateEntityWrapper);
            map.put("stat",flag);
        }catch (Exception e){
            map.put("stat", false);
            map.put("msg","删除出错，请联系管理员");
            logger.error("设置默认模板错误信息：{}", e);
        }
        return  map;
    }


    @RequiresPermissions("sms-template")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String toCreateTemp(Model model){
        PtTipSmsTemplate ptTipSmsTemplate = new PtTipSmsTemplate();
        model.addAttribute("ptTipSmsTemplate",ptTipSmsTemplate);
        model.addAttribute("action", "create");
        return "sms/smsTemplateForm";
    }


    @RequiresPermissions("sms-template")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String saveTemp(PtTipSmsTemplate ptTipSmsTemplate,RedirectAttributes redirectAttributes){
        try {
            if (ptTipSmsTemplate!=null&&ptTipSmsTemplate.getIsDefault()==1){
                PtTipSmsTemplate ptTipSms = new PtTipSmsTemplate();
                ptTipSms.setIsDefault(0);
                EntityWrapper<PtTipSmsTemplate> entityWrapper = new EntityWrapper<>();
          /*      entityWrapper.eq("type",ptTipSmsTemplate.getType());*/
                entityWrapper.eq("action_type",ptTipSmsTemplate.getActionType());
                iPtTipSmsTemplateService.update(ptTipSms,entityWrapper);
            }
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            ptTipSmsTemplate.setCreateTime(new Date().getTime());
            ptTipSmsTemplate.setCreateId(loginUser.getId());
            boolean flag  = iPtTipSmsTemplateService.insert(ptTipSmsTemplate);
            redirectAttributes.addFlashAttribute("stat", flag);
            redirectAttributes.addFlashAttribute("message", "保存成功");
        }catch (Exception e){
            logger.error("模板保存出错：{}", e);
            redirectAttributes.addFlashAttribute("stat", false);
            redirectAttributes.addFlashAttribute("message", "保存失败");
        }
        return  "redirect:/sms/template";
    }

    @RequiresPermissions("sms-template")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateTemp(PtTipSmsTemplate ptTipSmsTemplate,RedirectAttributes redirectAttributes){
        try {
            if (ptTipSmsTemplate!=null&&ptTipSmsTemplate.getIsDefault()==1){
                PtTipSmsTemplate ptTipSms = new PtTipSmsTemplate();
                ptTipSms.setIsDefault(0);
                EntityWrapper<PtTipSmsTemplate> entityWrapper = new EntityWrapper<>();
              /*  entityWrapper.eq("type",ptTipSmsTemplate.getType());*/
                entityWrapper.eq("action_type",ptTipSmsTemplate.getActionType());
                iPtTipSmsTemplateService.update(ptTipSms,entityWrapper);
            }
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            ptTipSmsTemplate.setUpdateTime(new Date().getTime());
            ptTipSmsTemplate.setUpdateId(loginUser.getId());
            boolean flag  = iPtTipSmsTemplateService.update(ptTipSmsTemplate,new EntityWrapper<PtTipSmsTemplate>().eq("id",ptTipSmsTemplate.getId()));
            redirectAttributes.addFlashAttribute("stat", flag);
            redirectAttributes.addFlashAttribute("message", "保存成功");
        }catch (Exception e){
            logger.error("模板保存出错：{}", e);
            redirectAttributes.addFlashAttribute("stat", false);
            redirectAttributes.addFlashAttribute("message", "保存失败");
        }
        return  "redirect:/sms/template";
    }


    @RequiresPermissions("sms-template")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public String updateTemp(@PathVariable("id") Long id,Model model){
        EntityWrapper<PtTipSmsTemplate> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("id",id);
        PtTipSmsTemplate ptTipSmsTemplate = iPtTipSmsTemplateService.selectOne(entityWrapper);
        model.addAttribute("ptTipSmsTemplate",ptTipSmsTemplate);
        model.addAttribute("action", "update");
        return "sms/smsTemplateForm";
    }



}
