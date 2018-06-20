package com.yunsunyun.point.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.dictionary.service.DictionaryService;
import com.yunsunyun.point.po.*;
import com.yunsunyun.point.vo.PtGoodsGalleryVo;
import com.yunsunyun.point.service.*;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.yunsunyun.xsimple.entity.system.KnEmployee;
import com.yunsunyun.xsimple.entity.system.KnRole;
import com.yunsunyun.xsimple.entity.system.KnUser;
import com.yunsunyun.xsimple.service.system.EmployeeService;
import com.yunsunyun.xsimple.service.system.ResourceService;
import com.kingnode.diva.web.Servlets;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import java.io.File;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2018-03-30
 */
@Controller
@RequestMapping("/goods/stuff")
public class PtGoodsController {

    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private IPtMallService mallService;
    @Autowired
    private IPtGoodsService goodsService;
    @Autowired
    private IPtGoodsCatagoryService catagoryService;
    @Autowired
    private IPtGoodsGalleryService galleryService;
    @Autowired
    private IPtMallGoodsService iPtMallGoodsService;
    @Autowired
    private IPtMallMerchantService iPtMallMerchantService;
    @Autowired
    private IPtCatagoryGoodsService iPtCatagoryGoodsService;
    @Autowired
    private IPtMallService iPtMallService;
    @Autowired
    private ResourceService resourceService;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PtGoodsController.class);

    @RequiresPermissions("goods-stuff")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("mallIdSelect", 0);
        model.addAttribute("priceList", dictionaryService.ReadDics("goods_price"));
        model.addAttribute("pointList", dictionaryService.ReadDics("goods_point"));

        //判断当前的用户角色
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
        KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
        List<KnRole> knRoleList = knUser.getRole();
        for (KnRole knRole : knRoleList) {
            if ("merchant".equals(knRole.getCode())) {
                List<KnEmployee> merchantList = new ArrayList<>();
                merchantList.add(employeeService.findMerchantById(loginUser.getId()));
                model.addAttribute("merchantList", merchantList);

                List<Long> mallIds = iPtMallMerchantService.getMallIdsByMerchantId(loginUser.getId());
                if (mallIds != null && mallIds.size() > 0) {
                    List<PtMall> mallList = iPtMallService.selectBatchIds(mallIds);
                    model.addAttribute("mallList", mallList);
                }
                break;
            } else if ("platformma".equals(knRole.getCode())) {
                model.addAttribute("mallList", mallService.selectList(new EntityWrapper<PtMall>().where("is_deleted = 0")));
                model.addAttribute("merchantList", employeeService.findMerchantEnable());
                break;
            }
        }

        return "/goods/goodsList";
    }

    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtGoods> list(DataTable<PtGoods> dt, ServletRequest request, Model model, @RequestParam(value = "mallId", required = false) Long mallId) {
        if (mallId == null) {
            mallId = 0L;
        }
        model.addAttribute("mallIdSelect", mallId);
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        //判断当前的用户角色
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
        KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
        List<KnRole> knRoleList = knUser.getRole();
        for (KnRole knRole : knRoleList) {
            if ("merchant".equals(knRole.getCode())) {
                searchParams.put("EQ_merchant", loginUser.getId());
                break;
            }
        }
        boolean point = false;
        if ("5".equals(dt.getiSortCol_0())) {
            point = true;
        }
        boolean asc = true;
        if ("desc".equals(dt.getsSortDir_0())){
            asc = false;
        }
        Page<PtGoods> page = new Page<>();
        page.setCurrent(dt.pageNo() + 1);
        page.setSize(dt.getiDisplayLength());
        page = goodsService.getGoodsPage(page, searchParams, mallId, Setting.AuditState.audited.getAuditStateCode(),point,asc);
        dt.setiTotalDisplayRecords(page.getTotal());
        dt.setAaData(page.getRecords());
        return dt;
    }

    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
        model.addAttribute("action", "create");
        model.addAttribute("merchantName", loginUser.getName());
        model.addAttribute("cataList", catagoryService.selectList(new EntityWrapper<PtGoodsCatagory>().eq("is_deleted", 0)));
        model.addAttribute("typeList", dictionaryService.ReadDics("home_recommend"));
        return "/goods/goodsForm";
    }

    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(PtGoods goods, @RequestParam(value = "carouselImage", required = false) List<String> carouselImage, @RequestParam(value = "detailImage", required = false) List<String> detailImage, @RequestParam(value = "cataIds", required = false) Long[] cataIds, @RequestParam(value = "goodsImage") MultipartFile goodsImage, RedirectAttributes redirectAttributes) {
        try {
            Boolean flag = goodsService.createGoods(goods, cataIds, goodsImage, detailImage, carouselImage);
            if (flag != null && flag) {
                redirectAttributes.addFlashAttribute("stat", true);
                redirectAttributes.addFlashAttribute("message", "保存成功,请待平台管理员审核！");
            } else {
                redirectAttributes.addFlashAttribute("stat", false);
                redirectAttributes.addFlashAttribute("message", "保存失败,请联系管理员");
            }
        } catch (Exception e) {
            logger.error("商品新建失败：errorMsg——" + e);
            redirectAttributes.addFlashAttribute("stat", false);
            redirectAttributes.addFlashAttribute("message", "保存失败,请联系管理员");
        }
        return "redirect:/goods/stuff";
    }

    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(PtGoods goods, @RequestParam(value = "carouselImage", required = false) List<String> carouselImage, @RequestParam(value = "detailImage", required = false) List<String> detailImage, @RequestParam(value = "cataIds", required = false) Long[] cataIds, @RequestParam(value = "goodsImage") MultipartFile goodsImage, RedirectAttributes redirectAttributes) {
        try {
            Integer flag = goodsService.updateGoods(goods, cataIds, goodsImage, detailImage, carouselImage);
            if (flag != null && flag == 1) {
                redirectAttributes.addFlashAttribute("stat", true);
                redirectAttributes.addFlashAttribute("message", "保存成功,请待平台管理员审核！审核中商品可在审核列表查看");
            } else if (flag != null && flag == 2) {
                redirectAttributes.addFlashAttribute("stat", true);
                redirectAttributes.addFlashAttribute("message", "保存成功,请待平台管理员审核通过后商品修改才可生效！审核中商品可在审核列表查看");
            } else {
                redirectAttributes.addFlashAttribute("stat", false);
                redirectAttributes.addFlashAttribute("message", "保存失败,请联系管理员");
            }
        } catch (Exception e) {
            logger.error("商品新建修改：errorMsg——" + e);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("stat", false);
            redirectAttributes.addFlashAttribute("message", "保存失败,请联系管理员");
        }
        return "redirect:/goods/stuff";
    }


    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id") Integer goodsId, Model model) {
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
        model.addAttribute("action", "update");
        model.addAttribute("merchantName", loginUser.getName());
        model.addAttribute("cataList", catagoryService.selectList(new EntityWrapper<PtGoodsCatagory>().eq("is_deleted", 0)));
        model.addAttribute("typeList", dictionaryService.ReadDics("home_recommend"));
        //当前修改的商品
        PtGoods goods = goodsService.selectById(goodsId);
        model.addAttribute("goods", goods);
        //当前修改商品分类
        List<PtCatagoryGoods> goodsCataList = iPtCatagoryGoodsService.selectList(new EntityWrapper<PtCatagoryGoods>().eq("goods_id", goodsId));
        model.addAttribute("goodsCataList", goodsCataList);
        //当前修改商品的相册
        List<PtGoodsGallery> goodsGalleryList = galleryService.selectList(new EntityWrapper<PtGoodsGallery>().eq("goods_id", goodsId).eq("type", Setting.GoodsGallery.DETAIL.getGalleryType()));
        List<PtGoodsGalleryVo> ptGoodsGalleryVoList = galleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList);
        model.addAttribute("detailImageList", ptGoodsGalleryVoList);
        List<PtGoodsGallery> goodsGalleryList2 = galleryService.selectList(new EntityWrapper<PtGoodsGallery>().eq("goods_id", goodsId).eq("type", Setting.GoodsGallery.CAROUSEL.getGalleryType()));
        List<PtGoodsGalleryVo> ptGoodsGalleryVoList2 = galleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList2);
        model.addAttribute("carouselImageList", ptGoodsGalleryVoList2);
        return "goods/goodsForm";
    }


    @RequiresPermissions("goods-review")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String getGoodsDetail(@PathVariable("id") Integer goodsId, Model model) {
        //当前修改的商品
        PtGoods goods = goodsService.selectById(goodsId);
        model.addAttribute("goods", goods);

        model.addAttribute("action", "update");
        model.addAttribute("merchantName", employeeService.findMerchantById(goods.getMerchantId()).getUserName());
        model.addAttribute("cataList", catagoryService.selectList(new EntityWrapper<PtGoodsCatagory>().eq("is_deleted", 0)));
        model.addAttribute("typeList", dictionaryService.ReadDics("home_recommend"));

        //当前修改商品分类
        List<PtCatagoryGoods> goodsCataList = iPtCatagoryGoodsService.selectList(new EntityWrapper<PtCatagoryGoods>().eq("goods_id", goodsId));
        model.addAttribute("goodsCataList", goodsCataList);
        //当前修改商品的相册
        List<PtGoodsGallery> goodsGalleryList = galleryService.selectList(new EntityWrapper<PtGoodsGallery>().eq("goods_id", goodsId).eq("type", Setting.GoodsGallery.DETAIL.getGalleryType()));
        List<PtGoodsGalleryVo> ptGoodsGalleryVoList = galleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList);
        model.addAttribute("detailImageList", ptGoodsGalleryVoList);
        List<PtGoodsGallery> goodsGalleryList2 = galleryService.selectList(new EntityWrapper<PtGoodsGallery>().eq("goods_id", goodsId).eq("type", Setting.GoodsGallery.CAROUSEL.getGalleryType()));
        List<PtGoodsGalleryVo> ptGoodsGalleryVoList2 = galleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList2);
        model.addAttribute("carouselImageList", ptGoodsGalleryVoList2);
        return "goods/goodsDetail";
    }


    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delGoods(Long id) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            PtGoods ptGoods = new PtGoods();
            ptGoods.setIsDeleted(1);
            boolean flag = goodsService.update(ptGoods, new EntityWrapper<PtGoods>().eq("id", id));
            if (flag) {
                resMap.put("stat", flag);
            } else {
                resMap.put("stat", flag);
                resMap.put("msg", "删除出错，请联系管理员");
            }
        } catch (Exception e) {
            resMap.put("stat", false);
            resMap.put("msg", "删除出错，请联系管理员");
            logger.error("删除应用错误信息：{}", e);
        }
        return resMap;
    }

    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/delete-all", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delAllGoods(@RequestParam(value = "ids") List<Long> ids) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            PtGoods ptGoods = new PtGoods();
            ptGoods.setIsDeleted(1);
            boolean flag = goodsService.update(ptGoods, new EntityWrapper<PtGoods>().in("id", ids));

            if (flag) {
                resMap.put("stat", flag);
            } else {
                resMap.put("stat", flag);
                resMap.put("msg", "删除出错，请联系管理员");
            }
        } catch (Exception e) {
            resMap.put("stat", false);
            resMap.put("msg", "删除出错，请联系管理员");
            logger.error("删除应用错误信息：{}", e);
        }
        return resMap;
    }


    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/save-detail-image", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveDetail(@RequestParam(value = "file") MultipartFile file) {
        Map<String, Object> resMap = null;
        try {
            resMap = galleryService.saveGalleryImage(file);
        } catch (Exception e) {
            logger.error("新建商品详情图失败：errorMsg——" + e);
            resMap = new HashMap<>();
            resMap.put("stat", false);
            resMap.put("msg", "图片添加失败！");
        }
        return resMap;
    }

    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/delete-image", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteDetail(String filePath) {
        Map<String, Object> resMap = null;
        try {
            resMap = galleryService.deleteGalleryImage(filePath);
        } catch (Exception e) {
            resMap = new HashMap<>();
            resMap.put("stat", false);
            resMap.put("msg", "删除出错，请联系管理员");
            logger.error("删除轮播图错误：{}", e);
        }
        return resMap;
    }

    /**
     * @param id
     * @param isNotSelect 是否是批量选择
     * @return
     */
    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/get-check-mall", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCheckInMall(@RequestParam(required = false, value = "id", defaultValue = "-1") Long id, Integer isNotSelect) {
        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> mallNotInList = new ArrayList<>();
        List<Map<String, Object>> mallInList = new ArrayList<>();


        try {
            Boolean flag = false;
            //判断当前的用户角色
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
            List<KnRole> knRoleList = knUser.getRole();
            for (KnRole knRole : knRoleList) {
                if ("merchant".equals(knRole.getCode())) {
                    flag = true;
                    break;
                }
            }

            List<Long> merchantMallIds = null;
            if (flag) {
                merchantMallIds = iPtMallMerchantService.getMallIdsByMerchantId(loginUser.getId());
            }

            //没有已选择的
            List<Long> mallIds = iPtMallGoodsService.selectMallIdByGoodsId(id);
            if (isNotSelect == 0 || (mallIds == null || mallIds.size() == 0)) {
                Wrapper<PtMall> wrapper = new EntityWrapper<PtMall>().where("is_deleted = 0");
                if (merchantMallIds != null && merchantMallIds.size() > 0) {
                    wrapper.in("id", merchantMallIds);
                } else {
                    wrapper.in("id", "-1");
                }
                List<PtMall> ptMallList = mallService.selectList(wrapper);
                for (PtMall mall : ptMallList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("mallId", mall.getId());
                    map.put("mallName", mall.getName());
                    mallNotInList.add(map);
                }
            } else if (isNotSelect == 1) {
                //有已选择的
                if (mallIds != null && mallIds.size() > 0) {

                    Wrapper<PtMall> wrapper = new EntityWrapper<PtMall>().where("is_deleted = 0").in("id", mallIds);
                    if (merchantMallIds != null && merchantMallIds.size() > 0) {
                        wrapper.in("id", merchantMallIds);
                    } else {
                        wrapper.in("id", "-1");
                    }
                    List<PtMall> InMalls = mallService.selectList(wrapper);
                    for (PtMall ptMall : InMalls) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("mallId", ptMall.getId());
                        map.put("mallName", ptMall.getName());
                        mallInList.add(map);
                    }

                    Wrapper<PtMall> wrapper1 = new EntityWrapper<PtMall>().where("is_deleted = 0").notIn("id", mallIds);
                    if (merchantMallIds != null && merchantMallIds.size() > 0) {
                        wrapper1.in("id", merchantMallIds);
                    } else {
                        wrapper1.in("id", "-1");
                    }
                    List<PtMall> notInMalls = mallService.selectList(wrapper1);
                    for (PtMall ptMall : notInMalls) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("mallId", ptMall.getId());
                        map.put("mallName", ptMall.getName());
                        mallNotInList.add(map);
                    }
                }
            }
            resMap.put("mallNotInList", mallNotInList);
            resMap.put("mallInList", mallInList);
            resMap.put("stat", true);
            resMap.put("msg", "");
        } catch (Exception e) {
            resMap.put("stat", false);
            resMap.put("msg", "删除出错，请联系管理员");
            logger.error("删除应用错误信息：{}", e);
        }
        return resMap;
    }

    @RequiresPermissions("goods-stuff")
    @RequestMapping(value = "/save-check-mall", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveCheckInMall(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "ids", required = false) List<Long> ids, @RequestParam(value = "mallIds", required = false) List<Long> mallIds, Integer saveType) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            //批量保存
            if (saveType == 0) {
                //先删除之前的
                boolean flag = iPtMallGoodsService.delete(new EntityWrapper<PtMallGoods>().in("goods_id", ids));
                if (flag) {
                    //设置全部下架
                    PtGoods ptGoods = new PtGoods();
                    ptGoods.setMarketState(0);
                    goodsService.update(ptGoods, new EntityWrapper<PtGoods>().in("id", ids));
                }
                if (ids != null && ids.size() > 0 && mallIds != null && mallIds.size() > 0) {
                    List<PtMallGoods> ptMallGoods = new ArrayList<>();
                    for (Long aId : ids) {
                        for (Long mallId : mallIds) {
                            PtMallGoods mallGoods = new PtMallGoods();
                            mallGoods.setGoodsId(aId);
                            mallGoods.setMallId(mallId);
                            ptMallGoods.add(mallGoods);

                        }
                    }
                    //设置全部上架
                    PtGoods ptGoods = new PtGoods();
                    ptGoods.setMarketState(1);
                    goodsService.update(ptGoods, new EntityWrapper<PtGoods>().in("id", ids));
                    iPtMallGoodsService.insertBatch(ptMallGoods);
                }
                resMap.put("stat", true);
                resMap.put("msg", "保存成功！");
            } else if (saveType == 1) {//单个保存
                boolean flag = iPtMallGoodsService.delete(new EntityWrapper<PtMallGoods>().eq("goods_id", id));
                if (flag) {
                    //设置全部没上架
                    PtGoods ptGoods = new PtGoods();
                    ptGoods.setMarketState(0);
                    goodsService.update(ptGoods, new EntityWrapper<PtGoods>().eq("id", id));
                }
                if (mallIds != null && mallIds.size() > 0) {
                    List<PtMallGoods> ptMallGoods = new ArrayList<>();
                    for (Long mallId : mallIds) {
                        PtMallGoods mallGoods = new PtMallGoods();
                        mallGoods.setGoodsId(id);
                        mallGoods.setMallId(mallId);
                        ptMallGoods.add(mallGoods);
                    }
                    //设置全部上架
                    PtGoods ptGoods = new PtGoods();
                    ptGoods.setMarketState(1);
                    goodsService.update(ptGoods, new EntityWrapper<PtGoods>().eq("id", id));
                    boolean flag2 = iPtMallGoodsService.insertBatch(ptMallGoods);
                }
                resMap.put("stat", true);
                resMap.put("msg", "保存成功！");
            }
        } catch (Exception e) {
            logger.error("商家入住出错", e);
            resMap.put("stat", false);
            resMap.put("msg", "操作失败,请联系管理员");
        }
        return resMap;
    }


}
