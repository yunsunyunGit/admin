package com.yunsunyun.point.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.dictionary.service.DictionaryService;
import com.yunsunyun.point.po.*;
import com.yunsunyun.point.service.*;
import com.yunsunyun.point.vo.PtGoodsGalleryVo;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.yunsunyun.xsimple.entity.system.KnEmployee;
import com.yunsunyun.xsimple.entity.system.KnRole;
import com.yunsunyun.xsimple.entity.system.KnUser;
import com.yunsunyun.xsimple.service.system.EmployeeService;
import com.yunsunyun.xsimple.service.system.ResourceService;
import com.kingnode.diva.web.Servlets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/goods/review")
public class PtGoodsReviewController {

    @Autowired
    private IPtGoodsService iPtGoodsService;
    @Autowired
    private IPtGoodsSnapshotService iPtGoodsSnapshotService;
    @Autowired
    private IPtGoodsGalleryService iPtGoodsGalleryService;
    @Autowired
    private IPtGoodsSnapshotGalleryService iPtGoodsSnapshotGalleryService;
    @Autowired
    private IPtCatagoryGoodsService iPtCatagoryGoodsService;
    @Autowired
    private IPtCatagorySnapshotGoodsService iPtCatagorySnapshotGoodsService;
    @Autowired
    private IPtGoodsCatagoryService catagoryService;
    @Autowired
    private IPtMallGoodsService iPtMallGoodsService;
    @Autowired
    private IPtMallService mallService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private IPtMallMerchantService iPtMallMerchantService;


    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PtGoodsReviewController.class);


    @RequiresPermissions("goods-review")
    @RequestMapping(method = RequestMethod.GET)
    public String toGoodsReview(Model model){
        model.addAttribute("mallIdSelect",0);
        model.addAttribute("mallList",mallService.selectList(new EntityWrapper<PtMall>().where("is_deleted = 0")));
        model.addAttribute("priceList",dictionaryService.ReadDics("goods_price"));
        model.addAttribute("pointList",dictionaryService.ReadDics("goods_point"));
        model.addAttribute("merchantList",employeeService.findMerchantEnable());
        ArrayList<Setting.AuditState>  auditStateList = new ArrayList<>();
        auditStateList.add(Setting.AuditState.auditWaiting);
        auditStateList.add(Setting.AuditState.AuditFailed);
        model.addAttribute("auditStateList",auditStateList);
        //判断当前的用户角色
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
        KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
        List<KnRole> knRoleList =  knUser.getRole();
        for (KnRole knRole:knRoleList){
            if("merchant".equals(knRole.getCode())){
                List<KnEmployee> merchantList  = new ArrayList<>();
                merchantList.add(employeeService.findMerchantById(loginUser.getId()));
                model.addAttribute("merchantList",merchantList);
                break;
            }
        }
        return  "goods/goodsReviewList";
    }


    @RequiresPermissions("goods-review")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtGoodsSnapshot> getGoodsSnapshot(DataTable<PtGoodsSnapshot> dt, ServletRequest request){
        Page<PtGoodsSnapshot> page = new Page<>();
        page.setCurrent(dt.pageNo()+1);
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
        //是否按积分排序
        String asc = dt.getsSortDir_0();
        Page<PtGoodsSnapshot> snapshotPage = iPtGoodsSnapshotService.getGoodsSnapshotPage(page, searchParams, asc);
        dt.setAaData(snapshotPage.getRecords());
        dt.setiTotalDisplayRecords(snapshotPage.getTotal());
        return  dt;
    }



    @RequiresPermissions("goods-review")
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    public String getGoodsDetail(@PathVariable("id")Integer goodsId,Model model){
        //当前修改的商品
        PtGoods goods = iPtGoodsService.selectById(goodsId);
        if (goods.getIsModified()==1){
            PtGoodsSnapshot ptGoodsSnapshot = iPtGoodsSnapshotService.selectOne(new EntityWrapper<PtGoodsSnapshot>().eq("goods_id",goodsId));
            model.addAttribute("goods",ptGoodsSnapshot);
            model.addAttribute("action","update");
            model.addAttribute("merchantName",employeeService.findMerchantById(goods.getMerchantId()).getUserName());
            model.addAttribute("cataList",catagoryService.selectList(new EntityWrapper<PtGoodsCatagory>().eq("is_deleted",0)));
            model.addAttribute("typeList",dictionaryService.ReadDics("home_recommend"));

            //当前修改商品分类
            List<PtCatagorySnapshotGoods> goodsCataList = iPtCatagorySnapshotGoodsService.selectList(new EntityWrapper<PtCatagorySnapshotGoods>().eq("snapshot_id", ptGoodsSnapshot.getId()));
            model.addAttribute("goodsCataList",goodsCataList);
            //当前修改商品的相册
            List<PtGoodsSnapshotGallery> goodsGalleryList = iPtGoodsSnapshotGalleryService.selectList(new EntityWrapper<PtGoodsSnapshotGallery>().eq("snapshot_id", ptGoodsSnapshot.getId()).eq("type", Setting.GoodsGallery.DETAIL.getGalleryType()));
            List<PtGoodsGalleryVo> ptGoodsGalleryVoList = iPtGoodsGalleryService.goodsGallerySnapshotToGoodsGalleryVo(goodsGalleryList);
            model.addAttribute("detailImageList",ptGoodsGalleryVoList);
            List<PtGoodsSnapshotGallery> goodsGalleryList2 = iPtGoodsSnapshotGalleryService.selectList(new EntityWrapper<PtGoodsSnapshotGallery>().eq("snapshot_id", ptGoodsSnapshot.getId()).eq("type", Setting.GoodsGallery.CAROUSEL.getGalleryType()));
            List<PtGoodsGalleryVo> ptGoodsGalleryVoList2 = iPtGoodsGalleryService.goodsGallerySnapshotToGoodsGalleryVo(goodsGalleryList2);
            model.addAttribute("carouselImageList",ptGoodsGalleryVoList2);


        }else if(goods.getIsModified()==0){
            model.addAttribute("goods",goods);
            model.addAttribute("action","update");
            model.addAttribute("merchantName",employeeService.findMerchantById(goods.getMerchantId()).getUserName());
            model.addAttribute("cataList",catagoryService.selectList(new EntityWrapper<PtGoodsCatagory>().eq("is_deleted",0)));
            model.addAttribute("typeList",dictionaryService.ReadDics("home_recommend"));
            //当前修改商品分类
            List<PtCatagoryGoods> goodsCataList = iPtCatagoryGoodsService.selectList(new EntityWrapper<PtCatagoryGoods>().eq("goods_id", goodsId));
            model.addAttribute("goodsCataList",goodsCataList);
            //当前修改商品的相册
            List<PtGoodsGallery> goodsGalleryList = iPtGoodsGalleryService.selectList(new EntityWrapper<PtGoodsGallery>().eq("goods_id", goodsId).eq("type", Setting.GoodsGallery.DETAIL.getGalleryType()));
            List<PtGoodsGalleryVo> ptGoodsGalleryVoList = iPtGoodsGalleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList);
            model.addAttribute("detailImageList",ptGoodsGalleryVoList);
            List<PtGoodsGallery> goodsGalleryList2 = iPtGoodsGalleryService.selectList(new EntityWrapper<PtGoodsGallery>().eq("goods_id", goodsId).eq("type", Setting.GoodsGallery.CAROUSEL.getGalleryType()));
            List<PtGoodsGalleryVo> ptGoodsGalleryVoList2 = iPtGoodsGalleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList2);
            model.addAttribute("carouselImageList",ptGoodsGalleryVoList2);
        }
        return  "goods/goodsReviewDetail";
    }


    @RequiresPermissions("goods-review")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id")Integer goodsId, Model model){
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
        model.addAttribute("action","update");
        model.addAttribute("merchantName",loginUser.getName());
        model.addAttribute("cataList",catagoryService.selectList(new EntityWrapper<PtGoodsCatagory>().eq("is_deleted",0)));
        model.addAttribute("typeList",dictionaryService.ReadDics("home_recommend"));
        //当前修改的商品
        PtGoodsSnapshot goodsSnapshot = null;
        goodsSnapshot = iPtGoodsSnapshotService.selectOne(new EntityWrapper<PtGoodsSnapshot>().eq("goods_id",goodsId));
        if (goodsSnapshot==null){
            PtGoods ptGoods = iPtGoodsService.selectById(goodsId);
            goodsSnapshot = iPtGoodsService.ptGoodsToPtGoodsSnapshot(ptGoods);
            model.addAttribute("goods",goodsSnapshot);
            //当前修改商品分类
            List<PtCatagoryGoods> goodsCataList = iPtCatagoryGoodsService.selectList(new EntityWrapper<PtCatagoryGoods>().eq("goods_id", goodsId));
            model.addAttribute("goodsCataList",goodsCataList);
            //当前修改商品的相册
            List<PtGoodsGallery> goodsGalleryList = iPtGoodsGalleryService.selectList(new EntityWrapper<PtGoodsGallery>().eq("goods_id", goodsId).eq("type", Setting.GoodsGallery.DETAIL.getGalleryType()));
            List<PtGoodsGalleryVo> ptGoodsGalleryVoList = iPtGoodsGalleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList);
            model.addAttribute("detailImageList",ptGoodsGalleryVoList);

            List<PtGoodsGallery> goodsGalleryList2 = iPtGoodsGalleryService.selectList(new EntityWrapper<PtGoodsGallery>().eq("goods_id", goodsId).eq("type", Setting.GoodsGallery.CAROUSEL.getGalleryType()));
            List<PtGoodsGalleryVo> ptGoodsGalleryVoList2 = iPtGoodsGalleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList2);
            model.addAttribute("carouselImageList",ptGoodsGalleryVoList2);
        }else{
            model.addAttribute("goods",goodsSnapshot);
            //当前修改商品分类
            List<PtCatagorySnapshotGoods> goodsCataList = iPtCatagorySnapshotGoodsService.selectList(new EntityWrapper<PtCatagorySnapshotGoods>().eq("snapshot_id", goodsSnapshot.getId()));
            model.addAttribute("goodsCataList",goodsCataList);
            //当前修改商品的相册
            List<PtGoodsSnapshotGallery> goodsGalleryList = iPtGoodsSnapshotGalleryService.selectList(new EntityWrapper<PtGoodsSnapshotGallery>().eq("snapshot_id", goodsSnapshot.getId()).eq("type", Setting.GoodsGallery.DETAIL.getGalleryType()));
            List<PtGoodsGalleryVo> ptGoodsGalleryVoList = iPtGoodsSnapshotGalleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList);
            model.addAttribute("detailImageList",ptGoodsGalleryVoList);
            List<PtGoodsSnapshotGallery> goodsGalleryList2 = iPtGoodsSnapshotGalleryService.selectList(new EntityWrapper<PtGoodsSnapshotGallery>().eq("snapshot_id", goodsSnapshot.getId()).eq("type", Setting.GoodsGallery.CAROUSEL.getGalleryType()));
            List<PtGoodsGalleryVo> ptGoodsGalleryVoList2 = iPtGoodsSnapshotGalleryService.goodsGalleryToGoodsGalleryVo(goodsGalleryList2);
            model.addAttribute("carouselImageList",ptGoodsGalleryVoList2);
        }
        return  "goods/goodsReviewForm";
    }


    @RequiresPermissions("goods-review")
    @RequestMapping(value = "/audit",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Map<String,Object> goodsAudit(Long goodsId,Integer auditStaut,@RequestParam(value = "denialReason",required = false)String denialReason,
                                         @RequestParam(value = "isUpMall",required = false,defaultValue = "0")Integer isUpMall){
       Map<String,Object> resMap = new HashMap<>();
       try {
           //审核通过  把商品快照表、商品相册快照表、商品分类关联快照表   里的信息覆盖到商品表、商品相册表、商品分类表
           PtGoods goods = iPtGoodsService.selectById(goodsId);
           //未 上架时修改过
           if (goods.getIsModified()==Setting.MarketStat.soldOut.getMaketStatCode()){
               PtGoods ptGoods = new PtGoods();
               ptGoods.setAuditState(auditStaut);
               ptGoods.setAuditDisableReason(denialReason);
               boolean flag = iPtGoodsService.update(ptGoods, new EntityWrapper<PtGoods>().eq("id", goodsId));
               if (flag&&isUpMall>0){
                    //上架到所有的商城
                   List<Long> merchantMallIds = iPtMallMerchantService.getMallIdsByMerchantId(goods.getMerchantId());
                   boolean flag2 = iPtMallGoodsService.delete(new EntityWrapper<PtMallGoods>().eq("goods_id", goodsId));
                   if (flag2){
                       //设置全部没上架
                       PtGoods ptGoods2 = new PtGoods();
                       ptGoods2.setMarketState(0);
                       iPtGoodsService.update(ptGoods2,new EntityWrapper<PtGoods>().eq("id",goodsId));
                   }
                   if (merchantMallIds!=null&&merchantMallIds.size()>0){
                       List<PtMallGoods> ptMallGoods = new ArrayList<>();
                       for (Long mallId : merchantMallIds) {
                           PtMallGoods mallGoods = new PtMallGoods();
                           mallGoods.setGoodsId(goodsId);
                           mallGoods.setMallId(mallId);
                           ptMallGoods.add(mallGoods);
                       }
                       //设置全部上架
                       PtGoods ptGoods3 = new PtGoods();
                       ptGoods3.setMarketState(1);
                       iPtGoodsService.update(ptGoods3,new EntityWrapper<PtGoods>().eq("id",goodsId));
                       boolean flag3 = iPtMallGoodsService.insertBatch(ptMallGoods);
                   }
                   resMap.put("stat", true);
                   resMap.put("msg","操作成功！");
               }else{
                   if (flag){
                       resMap.put("stat", true);
                       resMap.put("msg","操作成功！");
                   }else{
                       resMap.put("stat", false);
                       resMap.put("msg","操作失败！请联系管理员");
                   }
               }
           }else if(goods.getIsModified()==Setting.MarketStat.OnShelf.getMaketStatCode()){
               PtGoodsSnapshot entiy = new PtGoodsSnapshot();
               entiy.setAuditState(auditStaut);
               entiy.setAuditDisableReason(denialReason);
               boolean flag = iPtGoodsSnapshotService.update(entiy, new EntityWrapper<PtGoodsSnapshot>().eq("goods_id", goodsId));
               if (flag){
                   boolean flag2 = replaceGoods(goodsId,auditStaut);
                   if (flag2){
                       resMap.put("stat", true);
                       resMap.put("msg","操作成功！");
                   }else{
                       resMap.put("stat", false);
                       resMap.put("msg","操作失败！请联系管理员");
                   }
               }else{
                   resMap.put("stat", false);
                   resMap.put("msg","操作失败！请联系管理员");
               }
           }
       }catch (Exception e){
           logger.error("商品审核出错：{}", e);
           resMap.put("stat", false);
           resMap.put("msg","操作失败！请联系管理员");
       }
       return  resMap;
    }


    private boolean replaceGoods(Long goodsId,Integer auditStaut){
        if(Setting.AuditState.audited.getAuditStateCode()==auditStaut){
                PtGoodsSnapshot ptGoodsSnapshot = iPtGoodsSnapshotService.selectOne(new EntityWrapper<PtGoodsSnapshot>().eq("goods_id", goodsId));
                //更新商品表
                PtGoods ptGoods = iPtGoodsSnapshotService.ptGoodsSnapshotToPtGoods(ptGoodsSnapshot);
                ptGoods.setIsModified(0);
                iPtGoodsService.insertOrUpdate(ptGoods);
                //删除商品快照表
                iPtGoodsSnapshotService.delete(new EntityWrapper<PtGoodsSnapshot>().eq("id",ptGoodsSnapshot.getId()));

                //更新商品相册表
                iPtGoodsGalleryService.delete(new EntityWrapper<PtGoodsGallery>().eq("goods_id", goodsId));
                List<PtGoodsSnapshotGallery> goodsSnapshotGalleries = iPtGoodsSnapshotGalleryService.selectList(new EntityWrapper<PtGoodsSnapshotGallery>().eq("snapshot_id", ptGoodsSnapshot.getId()));
                ArrayList<PtGoodsGallery> ptGoodsGalleryArrayList = new ArrayList<>();
                for (PtGoodsSnapshotGallery snapshotGallery : goodsSnapshotGalleries) {
                    PtGoodsGallery ptGoodsGallery = new PtGoodsGallery();
                    ptGoodsGallery.setImageUrl(snapshotGallery.getImageUrl());
                    ptGoodsGallery.setGoodsId(goodsId);
                    ptGoodsGallery.setType(snapshotGallery.getType());
                    ptGoodsGalleryArrayList.add(ptGoodsGallery);
                }
                if (ptGoodsGalleryArrayList!=null&&ptGoodsGalleryArrayList.size()>0){
                    iPtGoodsGalleryService.insertBatch(ptGoodsGalleryArrayList);
                }
                //删除商品相册快照表
                iPtGoodsSnapshotGalleryService.delete(new EntityWrapper<PtGoodsSnapshotGallery>().eq("snapshot_id", ptGoodsSnapshot.getId()));

                //更新商品分类表
                iPtCatagoryGoodsService.delete(new EntityWrapper<PtCatagoryGoods>().eq("goods_id", goodsId));
                List<PtCatagorySnapshotGoods> catagorySnapshotGoods = iPtCatagorySnapshotGoodsService.selectList(new EntityWrapper<PtCatagorySnapshotGoods>().eq("snapshot_id", ptGoodsSnapshot.getId()));
                ArrayList<PtCatagoryGoods>  ptCatagoryGoodsArrayList = new ArrayList<>();
                for (PtCatagorySnapshotGoods catagorySnapshotGood : catagorySnapshotGoods) {
                    PtCatagoryGoods ptCatagoryGoods = new PtCatagoryGoods();
                    ptCatagoryGoods.setCatagoryId(catagorySnapshotGood.getCatagoryId());
                    ptCatagoryGoods.setGoodsId(goodsId);
                    ptCatagoryGoodsArrayList.add(ptCatagoryGoods);
                }
                if (ptCatagoryGoodsArrayList!=null&&ptCatagoryGoodsArrayList.size()>0){
                    iPtCatagoryGoodsService.insertBatch(ptCatagoryGoodsArrayList);
                }
                //删除商品分类快照表
                iPtCatagorySnapshotGoodsService.delete(new EntityWrapper<PtCatagorySnapshotGoods>().eq("snapshot_id",ptGoodsSnapshot.getId()));
                return true;
        }else if(Setting.AuditState.AuditFailed.getAuditStateCode()==auditStaut){//审核不通过
           return  true;
        }
        return  false;
    }



    @RequiresPermissions("goods-review")
    @RequestMapping(value = "/all-audit",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Map<String,Object> allAudit(@RequestParam("ids")List<Long> goodsIds,Integer auditStaut,@RequestParam(value = "denialReason",required = false)String denialReason,
                                       @RequestParam(value = "isUpMall",required = false,defaultValue = "0")Integer isUpMall){
        Map<String,Object> map = new HashMap<>();
        try{
            boolean flag = true;
            for (Long goodsId : goodsIds) {
                PtGoods goods = iPtGoodsService.selectById(goodsId);
                //未 上架时修改
                if (goods.getIsModified()==0){
                    PtGoods ptGoods = new PtGoods();
                    ptGoods.setAuditState(auditStaut);
                    ptGoods.setAuditDisableReason(denialReason);
                    boolean flag2 = iPtGoodsService.update(ptGoods, new EntityWrapper<PtGoods>().eq("id", goodsId));
                    if (!flag2){
                        flag = flag2;
                    }else{
                        if (isUpMall>0){
                            //上架到所有的商城
                            List<Long> merchantMallIds = iPtMallMerchantService.getMallIdsByMerchantId(goods.getMerchantId());
                            boolean flag3 = iPtMallGoodsService.delete(new EntityWrapper<PtMallGoods>().eq("goods_id", goodsId));
                            if (flag3){
                                //设置全部没上架
                                PtGoods ptGoods2 = new PtGoods();
                                ptGoods2.setMarketState(0);
                                iPtGoodsService.update(ptGoods2,new EntityWrapper<PtGoods>().eq("id",goodsId));
                            }
                            if (merchantMallIds!=null&&merchantMallIds.size()>0){
                                List<PtMallGoods> ptMallGoods = new ArrayList<>();
                                for (Long mallId : merchantMallIds) {
                                    PtMallGoods mallGoods = new PtMallGoods();
                                    mallGoods.setGoodsId(goodsId);
                                    mallGoods.setMallId(mallId);
                                    ptMallGoods.add(mallGoods);
                                }
                                //设置全部上架
                                PtGoods ptGoods3 = new PtGoods();
                                ptGoods3.setMarketState(1);
                                iPtGoodsService.update(ptGoods3,new EntityWrapper<PtGoods>().eq("id",goodsId));
                                boolean flag4 = iPtMallGoodsService.insertBatch(ptMallGoods);
                            }
                        }
                    }
                }else if(goods.getIsModified()==1){
                    PtGoodsSnapshot entiy = new PtGoodsSnapshot();
                    entiy.setAuditState(auditStaut);
                    entiy.setAuditDisableReason(denialReason);
                    boolean flag2 = iPtGoodsSnapshotService.update(entiy, new EntityWrapper<PtGoodsSnapshot>().eq("goods_id", goodsId));
                    if (flag2){
                        replaceGoods(goodsId,auditStaut);
                    }else{
                        flag = flag2;
                    }
                }
            }
            if (flag){
                map.put("stat", flag);
                map.put("msg","操作成功！");
            }else{
                map.put("stat", flag);
                map.put("msg","操作失败！");
            }
        }catch (Exception e){
            logger.error("商品批量审核出错：{}", e);
            map.put("stat", false);
            map.put("msg","操作成功！");
        }
        return  map;
    }


}
