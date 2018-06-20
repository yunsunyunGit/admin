package com.yunsunyun.point.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.vo.PtGoodsCatagoryVo;
import com.yunsunyun.point.po.PtCatagoryGoods;
import com.yunsunyun.point.po.PtGoodsCatagory;
import com.yunsunyun.point.po.PtMall;
import com.yunsunyun.point.po.PtMallCatagory;
import com.yunsunyun.point.service.*;
import com.yunsunyun.xsimple.api.common.AjaxStatus;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.kingnode.diva.web.Servlets;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2018-03-30
 */
@Controller
@RequestMapping("/goods/catagory")
public class PtGoodsCatagoryController {
    private static Logger logger = LoggerFactory.getLogger(PtGoodsCatagoryController.class);

    @Autowired
    private IPtGoodsCatagoryService catagoryService;
    @Autowired
    private IPtMallService mallService;
    @Autowired
    private IPtMallCatagoryService mallCatagoryService;
    @Autowired
    private IPtGoodsService goodsService;
    @Autowired
    private IPtCatagoryGoodsService catagoryGoodsService;

    @RequiresPermissions("goods-catagory")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("mallList", mallService.selectList(new EntityWrapper<PtMall>().where("is_deleted != 1")));
        return "/goods/catagoryList";
    }

    @RequiresPermissions("goods-catagory")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtGoodsCatagoryVo> list(DataTable<PtGoodsCatagoryVo> dt, ServletRequest request) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<PtGoodsCatagory> page = new Page<>();
        page.setCurrent(dt.pageNo()+1);
        page.setSize(dt.getiDisplayLength());
        EntityWrapper<PtGoodsCatagory> wrapper = new EntityWrapper<PtGoodsCatagory>();
        wrapper.where("is_deleted = 0");
        wrapper.orderBy("seq");
        if(searchParams.containsKey("EQ_isShow") && StringUtils.isNotEmpty(searchParams.get("EQ_isShow").toString().trim())){
            wrapper.eq("is_show",Long.parseLong(searchParams.get("EQ_isShow").toString().trim()));
        }
        if(searchParams.containsKey("LIKE_name") && StringUtils.isNotEmpty(searchParams.get("LIKE_name").toString().trim())){
            wrapper.like("name",searchParams.get("LIKE_name").toString().trim());
        }
        if(searchParams.containsKey("LIKE_seq") && StringUtils.isNotEmpty(searchParams.get("LIKE_seq").toString().trim())){
            wrapper.like("seq",searchParams.get("LIKE_seq").toString().trim());
        }
        wrapper.orderBy("seq");
        page = catagoryService.selectPage(page,wrapper);
        List<PtGoodsCatagoryVo> list = new ArrayList<>();
        for(PtGoodsCatagory catagory : page.getRecords()){
            PtGoodsCatagoryVo temp = new PtGoodsCatagoryVo();
            temp.setMallNum(mallCatagoryService.selectCount(new EntityWrapper<PtMallCatagory>().where("catagory_id={0}",catagory.getId())));
            temp.setMallNameList(mallCatagoryService.getCatagoryMallNames(catagory.getId()));
            temp.setId(catagory.getId());
            temp.setImageUrl(catagory.getImageUrl());
            temp.setSeq(catagory.getSeq());
            temp.setIsShow(catagory.getIsShow());
            temp.setName(catagory.getName());
            list.add(temp);
        }
        dt.setiTotalDisplayRecords(page.getTotal());
        dt.setAaData(list);
        return dt;
    }

    @RequiresPermissions("goods-catagory")
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("action","create");
        model.addAttribute("mallList", mallService.selectList(new EntityWrapper<PtMall>().where("is_deleted != 1")));
        return "/goods/catagoryForm";
    }

    @RequiresPermissions("goods-catagory")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public String update(@PathVariable("id")Long id, Model model){
        model.addAttribute("action","update");
        model.addAttribute("mallList", mallService.selectList(new EntityWrapper<PtMall>().where("is_deleted != 1")));
        model.addAttribute("mallIds",mallCatagoryService.selectList(new EntityWrapper<PtMallCatagory>().where("catagory_id = {0}",id)));
        model.addAttribute("catagory",catagoryService.selectById(id));
        return "/goods/catagoryForm";
    }

    @RequiresPermissions("goods-catagory")
    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object readById(@PathVariable Long id) {
        return catagoryService.selectById(id);
    }

    @RequiresPermissions("goods-catagory")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid PtGoodsCatagory catagory,@RequestParam("mallIds") Long[] mallIds, @RequestParam("files") MultipartFile file, RedirectAttributes redirectAttributes) {
//        if (catagory.getId() == null || catagory.getId().compareTo(0l) <= 0) {
//            catagoryService.insertCatagoryReturnId(catagory);
//            if (catagory.getParentId().compareTo(0L) > 0) {
//                catagory.setPath(catagoryService.selectById(catagory.getParentId()).getPath() + catagory.getId() + ".");
//            } else {
//                catagory.setPath(catagory.getId() + ".");
//            }
//        }
//        if (!file.isEmpty()) {
//            logger.debug("catagory -- id :" + catagory.getId());
//            String name = file.getOriginalFilename();
//            int i = file.getOriginalFilename().lastIndexOf(".");
//            catagory.setImageUrl(Setting.CATAIMAGE + catagory.getId() + (i >= 0 ? name.substring(i) : ".jpg"));
//            updateImg(catagory, file);
//
//        }
//        if(mallIds != null && mallIds.length > 0){
//            for (Long mallId : mallIds){
//                PtMallCatagory mallCatagory = new PtMallCatagory(mallId,catagory.getId());
//                PtMallCatagory flag = mallCatagoryService.selectOne(new EntityWrapper<PtMallCatagory>().where("mall_id = {0} and catagory_id = {1}",mallId,catagory.getId()));
//                if (flag == null){
//                    mallCatagoryService.insert(mallCatagory);
//                }
//            }
//            mallCatagoryService.delete(new EntityWrapper<PtMallCatagory>().where("catagory_id = {0}",catagory.getId()).notIn("mall_id",mallIds));
//        }
//        catagoryService.updateById(catagory);
        catagoryService.saveCatagory(catagory,mallIds,file);
        String saveMsg = "保存商品分类成功！";
        redirectAttributes.addFlashAttribute("message", saveMsg);
        return "redirect:/goods/catagory";
    }

    @RequiresPermissions("goods-catagory")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public AjaxStatus delete(Long ids){
        List<PtCatagoryGoods> goods = catagoryGoodsService.selectList(new EntityWrapper<PtCatagoryGoods>().where("catagory_id = {0}",ids));
        if(goods != null && goods.size() > 0){
            return new AjaxStatus(false,"该分类中包含了商品,请先清除分类内的商品!");
        }else{
            PtGoodsCatagory temp = new PtGoodsCatagoryVo();
            temp.setId(ids);
            temp.setIsDeleted(1);
            catagoryService.updateById(temp);
            return new AjaxStatus(true,"删除资源成功");
        }
    }




}
