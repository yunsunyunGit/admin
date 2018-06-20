package com.yunsunyun.point.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.config.util.ConfigUtil;
import com.yunsunyun.dictionary.service.DictionaryService;
import com.yunsunyun.point.po.PtBanner;
import com.yunsunyun.point.service.IPtBannerService;
import com.yunsunyun.point.vo.PtBannerVo;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.api.common.AjaxStatus;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.yunsunyun.xsimple.util.dete.DateUtil;
import com.kingnode.diva.web.Servlets;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2018-04-08
 */
@Controller
@RequestMapping("/point/banner")
public class PtBannerController {

    private static Logger logger = LoggerFactory.getLogger(PtBannerController.class);
    @Autowired
    private IPtBannerService bannerService;
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 内容列表页面跳转
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("point-banner")
    public String list(Model model) {
        model.addAttribute("bannerType", dictionaryService.ReadDics("banner_type"));
        return "/point/bannerList";
    }

    @RequiresPermissions("point-banner")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtBannerVo> list(DataTable<PtBannerVo> dt, ServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<PtBanner> page = new Page<>();
        page.setSize(dt.getiDisplayLength());
        page.setCurrent(dt.pageNo()+1);
        EntityWrapper<PtBanner> wrapper = new EntityWrapper<PtBanner>();
        if (searchParams.containsKey("EQ_type") && StringUtils.isNotEmpty(searchParams.get("EQ_type").toString().trim())) {
            wrapper.eq("type", Long.parseLong(searchParams.get("EQ_type").toString().trim()));
        }
        if (searchParams.containsKey("EQ_state") && StringUtils.isNotEmpty(searchParams.get("EQ_state").toString().trim())) {
            wrapper.eq("state", Long.parseLong(searchParams.get("EQ_state").toString().trim()));
        }
        if (searchParams.containsKey("LIKE_content") && StringUtils.isNotEmpty(searchParams.get("LIKE_content").toString().trim())) {
            wrapper.like("content", searchParams.get("LIKE_content").toString().trim());
        }
        if (searchParams.containsKey("LIKE_name") && StringUtils.isNotEmpty(searchParams.get("LIKE_name").toString().trim())) {
            wrapper.like("name", searchParams.get("LIKE_name").toString().trim());
        }
        if (searchParams.containsKey("LIKE_url") && StringUtils.isNotEmpty(searchParams.get("LIKE_url").toString().trim())) {
            wrapper.like("url", searchParams.get("LIKE_url").toString().trim());
        }
        page = bannerService.selectPage(page, wrapper);
        List<PtBannerVo> list = new ArrayList<>();
        for (PtBanner banner : page.getRecords()) {
            PtBannerVo temp = new PtBannerVo(banner.getId(), banner.getUrl(), banner.getName(), dictionaryService.ReadDic(banner.getType()).getDicValue(), banner.getState(), banner.getContent(), DateUtil.getInstance().parseDate(banner.getCreateTime()));
            list.add(temp);
        }
        dt.setiTotalDisplayRecords(page.getTotal());
        dt.setAaData(list);
        return dt;
    }

    @RequiresPermissions("point-banner")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus delete(Long... ids) {
        if (ids == null || ids.length == 0) {
            return new AjaxStatus(false, "未选择所要删除的数据!");
        }
        boolean result = false;
        if (ids.length == 1) {
            result = bannerService.deleteById(ids[0]);
        } else {
            List<Long> list = Arrays.asList(ids);
            result = bannerService.deleteBatchIds(list);
        }
        if (result) {
            return new AjaxStatus(true, "删除成功!");
        } else {
            return new AjaxStatus(false, "删除失败,请稍后重试!");
        }
    }

    @RequiresPermissions("point-banner")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("bannerType", dictionaryService.ReadDics("banner_type"));
        return "/point/bannerForm";
    }

    @RequiresPermissions("point-banner")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("banner", bannerService.selectById(id));
        model.addAttribute("bannerType", dictionaryService.ReadDics("banner_type"));
        return "/point/bannerForm";
    }

    /**
     * 新建保存
     *
     * @param
     * @return
     */
    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PtBanner banner, MultipartFile files, RedirectAttributes redirectAttributes) {
        if (files == null) {
            redirectAttributes.addFlashAttribute("stat", false);
            redirectAttributes.addFlashAttribute("message", "保存失败");
            return "redirect:/point/banner";
        }
        try {
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            banner.setCreateId(loginUser.getId());
            banner.setCreateTime(System.currentTimeMillis());
            bannerService.insertCatagoryReturnId(banner);
            logger.debug("catagory -- id :" + banner.getId());
            String name = files.getOriginalFilename();
            int i = files.getOriginalFilename().lastIndexOf(".");
            String imageUrl = Setting.BANNERIMAGE + "/" + DateUtil.getNowYear() + DateUtil.getMonth() + "/" + DateUtil.getDay() + "/" + banner.getId() + (i >= 0 ? name.substring(i) : ".jpg");
            banner.setImageUrl(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name()) + imageUrl);
            String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name()) + imageUrl;
            if (updateImg(path, files)==null){
                redirectAttributes.addFlashAttribute("stat", false);
                redirectAttributes.addFlashAttribute("message", "新建成功，但是图片保存失败！");
                return "redirect:/point/banner";
            }
            boolean flag = bannerService.updateById(banner);
            if (flag) {
                redirectAttributes.addFlashAttribute("stat", true);
                redirectAttributes.addFlashAttribute("message", "保存成功");
            } else {
                redirectAttributes.addFlashAttribute("stat", false);
                redirectAttributes.addFlashAttribute("message", "保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存内容出错:---" + e.getMessage(), e);
            redirectAttributes.addFlashAttribute("stat", false);
            redirectAttributes.addFlashAttribute("message", "保存失败");
        }
        return "redirect:/point/banner";
    }

    /**
     * 更新保存
     *
     * @param
     * @return
     */
    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PtBanner banner, MultipartFile files, RedirectAttributes redirectAttributes) {
        try {
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            banner.setUpdateId(loginUser.getId());
            banner.setUpdateTime(System.currentTimeMillis());
            if (!files.isEmpty()) {
                logger.debug("catagory -- id :" + banner.getId());
                String name = files.getOriginalFilename();
                int i = files.getOriginalFilename().lastIndexOf(".");
                String imageUrl = Setting.BANNERIMAGE + "/" + banner.getId() + (i >= 0 ? name.substring(i) : ".jpg");
                banner.setImageUrl(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name()) + imageUrl);
                String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name()) + imageUrl;
                 if (updateImg(path, files)==null){
                     redirectAttributes.addFlashAttribute("stat", false);
                     redirectAttributes.addFlashAttribute("message", "保存失败");
                     return "redirect:/point/banner";
                 }
            }
            boolean flag = bannerService.updateById(banner);
            if (flag) {
                redirectAttributes.addFlashAttribute("stat", true);
                redirectAttributes.addFlashAttribute("message", "保存成功");
            } else {
                redirectAttributes.addFlashAttribute("stat", false);
                redirectAttributes.addFlashAttribute("message", "保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存内容出错:---" + e.getMessage(), e);
            redirectAttributes.addFlashAttribute("stat", false);
            redirectAttributes.addFlashAttribute("message", "保存失败");
        }
        return "redirect:/point/banner";
    }

    private File updateImg(String path, MultipartFile file) {
        try {
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            File localFile = new File(path);
            logger.debug("文件保存的完整路径为：" + localFile.getAbsolutePath());
            if (!localFile.getParentFile().exists()) {
                localFile.getParentFile().mkdirs();
            }
            file.transferTo(localFile);
            return localFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
