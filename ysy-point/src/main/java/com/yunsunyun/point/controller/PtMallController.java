package com.yunsunyun.point.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.dictionary.entity.KnDictionary;
import com.yunsunyun.dictionary.service.DictionaryService;
import com.yunsunyun.point.po.PtGoods;
import com.yunsunyun.point.po.PtMall;
import com.yunsunyun.point.po.PtMallGoods;
import com.yunsunyun.point.po.PtMallMerchant;
import com.yunsunyun.point.service.IPtGoodsService;
import com.yunsunyun.point.service.IPtMallGoodsService;
import com.yunsunyun.point.service.IPtMallMerchantService;
import com.yunsunyun.point.service.IPtMallService;
import com.yunsunyun.point.vo.PtMallVo;
import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.yunsunyun.xsimple.dao.system.KnEmployeeDao;
import com.yunsunyun.xsimple.entity.system.KnEmployee;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2018-03-30
 */
@Controller
@RequestMapping(value = "/point/mall")
public class PtMallController {
    @Autowired
    private IPtMallService iPtMallService;

    @Autowired
    private IPtMallMerchantService iPtMallMerchantService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private KnEmployeeDao knEmployeeDao;

    @Autowired
    private IPtGoodsService goodsService;

    @Autowired
    private IPtMallGoodsService iPtMallGoodsService;

    private static Logger logger = LoggerFactory.getLogger(PtMallController.class);

    /**
     * 商城列表跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("point-mall")
    public String mallList(){
        return  "point/mallList";
    }
    /**
     * 商城列表
     * @param mallName
     * @param dt
     * @return
     */
    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtMallVo> getMallList(@RequestParam(value = "mallName", required = false) String mallName, DataTable<PtMallVo> dt){
        Page<PtMallVo> page = new Page<>();
        page.setCurrent(dt.pageNo()+1);
        page.setSize(dt.getiDisplayLength());

        String [] sort = new String[]{"name","description","app_name","merchant_num"};


        Page<PtMallVo> mallPage = iPtMallService.getMallPage(page,mallName);
        dt.setAaData(mallPage.getRecords());
        dt.setiTotalDisplayRecords(mallPage.getTotal());
        return  dt;
    }

    private Sort getSort(DataTable dt, String[] column) {
        Sort.Direction d = Sort.Direction.DESC;
        if ("asc".equals(dt.getsSortDir_0())) {
            d = Sort.Direction.ASC;
        }
        int index = Integer.parseInt(dt.getiSortCol_0()) - 1;
        return new Sort(d, column[index]);
    }

    /**
     * 商城新增跳转
     * @param model
     * @return
     */
    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String toCreateMall(Model model){
        PtMall ptMall = new PtMall();
        model.addAttribute("ptMall", ptMall);
        model.addAttribute("action", "create");
        List<KnDictionary> appNameDictList =  dictionaryService.findSonDictForFatherDictKey("app_name");
        model.addAttribute("appNameDictList",appNameDictList);
        return "point/mallForm";
    }

    /**
     * 新建保存
     * @param
     * @return
     */
    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String saveMall(PtMall ptMall, RedirectAttributes redirectAttributes){
        try {
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            ptMall.setCreateId(loginUser.getId());
            ptMall.setCreateTime(new Date().getTime());
            ptMall.setIsDeleted(0);
            boolean flag = iPtMallService.insert(ptMall);
            if (flag) {
                redirectAttributes.addFlashAttribute("stat", true);
                redirectAttributes.addFlashAttribute("message", "保存成功");
            } else {
                redirectAttributes.addFlashAttribute("stat", false);
                redirectAttributes.addFlashAttribute("message", "保存失败");
            }
        }catch (Exception e){
            logger.error("保存商城出错", e);
            redirectAttributes.addFlashAttribute("stat", false);
            redirectAttributes.addFlashAttribute("message", "保存失败");
        }
        return "redirect:/point/mall";
    }

    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public String updateMall(@PathVariable("id") Long id, Model model){
        PtMall ptMall = iPtMallService.selectOne(new EntityWrapper<PtMall>().where("id = {0}",id));
        model.addAttribute("ptMall", ptMall);//
        model.addAttribute("action", "update");// 跳转编辑的标示
        List<KnDictionary> appNameDictList =  dictionaryService.findSonDictForFatherDictKey("app_name");
        model.addAttribute("appNameDictList",appNameDictList);
        return "point/mallForm";
    }

    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String saveupdateMall(PtMall ptMall, RedirectAttributes redirectAttributes){
        try {
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            ptMall.setUpdateId(loginUser.getId());
            ptMall.setUpdateTime(new Date().getTime());
            boolean flag = iPtMallService.updateById(ptMall);
            if (flag) {
                redirectAttributes.addFlashAttribute("stat", true);
                redirectAttributes.addFlashAttribute("message", "保存成功");
            } else {
                redirectAttributes.addFlashAttribute("stat", false);
                redirectAttributes.addFlashAttribute("message", "保存失败");
            }
        }catch (Exception e){
            logger.error("修改商城出错", e);
            redirectAttributes.addFlashAttribute("stat", false);
            redirectAttributes.addFlashAttribute("message", "保存失败");
        }
        return "redirect:/point/mall";
    }

    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/delete-all",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deletedAllMall(@RequestParam("ids") List<Long> ids){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            PtMall ptMall = new PtMall();
            ptMall.setIsDeleted(1);
            Wrapper<PtMall> wrapper = new EntityWrapper<PtMall>();
            wrapper.in("id",ids);
            Boolean flag= iPtMallService.update(ptMall,wrapper);
            map.put("stat", flag);
        } catch (Exception e) {
            map.put("stat", false);
            map.put("msg","删除出错，请联系管理员");
            logger.error("删除应用错误信息：{}", e);
        }
        return map;
    }

    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deletedMall(@RequestParam("id") Long id){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            PtMall ptMall = new PtMall();
            ptMall.setIsDeleted(1);
            Wrapper<PtMall> wrapper = new EntityWrapper<PtMall>();
            wrapper.eq("id",id);
            Boolean flag= iPtMallService.update(ptMall,wrapper);
            map.put("stat", flag);
        } catch (Exception e) {
            map.put("stat", false);
            map.put("msg","删除出错，请联系管理员");
            logger.error("删除应用错误信息：{}", e);
        }
        return map;
    }

    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/get-check-mall",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getCheckMall(@RequestParam("id") Long id){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String,Object>> merchantInList = new ArrayList<>();
        List<Map<String,Object>> merchantNotInList = new ArrayList<>();
        List<KnEmployee> knEmployeeNotList = null;
        try {
            List<Long> merchantIds = iPtMallMerchantService.getMerchantIdsByMallId(id);
            //如果关联表里没有
            if (merchantIds!=null&&merchantIds.size()>0){
                //已入住的商家
                List<KnEmployee> knEmployeeInList = knEmployeeDao.findMerchantInIds(merchantIds);
                for (KnEmployee knEmployee: knEmployeeInList) {
                    Map<String,Object> merchantIn = new HashedMap();
                    merchantIn.put("merchantId",knEmployee.getId());
                    merchantIn.put("merchantName",knEmployee.getUserName());
                    merchantInList.add(merchantIn);
                }
                //未入住的商家
                knEmployeeNotList = knEmployeeDao.findMerchantNotInIds(merchantIds);
            }else{
                //未入住的商家
                knEmployeeNotList = knEmployeeDao.findMerchantEnable();
            }
            for (KnEmployee knEmployee: knEmployeeNotList) {
                Map<String,Object> merchantNotIn = new HashedMap();
                merchantNotIn.put("merchantId",knEmployee.getId());
                merchantNotIn.put("merchantName",knEmployee.getUserName());
                merchantNotInList.add(merchantNotIn);
            }
            map.put("stat", true);
            map.put("merchantInList",merchantInList);
            map.put("merchantNotInList",merchantNotInList);
        }catch (Exception e){
            logger.error("商家入住出错", e);
            map.put("stat", false);
            map.put("msg", "商家入住出错，请联系管理员");
        }
        return  map;
    }



    @RequiresPermissions("point-mall")
    @RequestMapping(value = "/save-check-mall",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> saveCheckMall(@RequestParam("id")Long id,@RequestParam("merchantIds") List<Long> merchantIds){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Wrapper<PtMallMerchant>  mallMerchantWrapper =  new EntityWrapper<PtMallMerchant>();
            mallMerchantWrapper.where("mall_id = {0}",id);

            //查询之前的该商城入住商家  然后把下架的商城的商品在 商城商品关联表的关联数据删掉
            List<PtMallMerchant> bfMallMerchant = iPtMallMerchantService.selectList(new EntityWrapper<PtMallMerchant>().eq("mall_id", id));
            ArrayList<Long> bfMerchant = new ArrayList<>();
            if (bfMallMerchant!=null&&bfMallMerchant.size()>0){
                for (PtMallMerchant ptMallMerchant : bfMallMerchant) {
                    Long merchantId = ptMallMerchant.getMerchantId();
                    if (!merchantIds.contains(merchantId)){
                        bfMerchant.add(merchantId);
                    }
                }
            }
            if(bfMerchant.size()>0){
                List<PtGoods> ptGoods = goodsService.selectList(new EntityWrapper<PtGoods>().in("merchant_id", bfMerchant));
                ArrayList<Long> ptGoodsId = new ArrayList<>();
                for (PtGoods ptGood : ptGoods) {
                    ptGoodsId.add(ptGood.getId());
                }
                iPtMallGoodsService.delete(new EntityWrapper<PtMallGoods>().eq("mall_id",id).and().in("goods_id",ptGoodsId));
                for (PtGoods ptGood : ptGoods){
                    int goodsCount = iPtMallGoodsService.selectCount(new EntityWrapper<PtMallGoods>().eq("goods_id", ptGood.getId()));
                    if (goodsCount==0){
                        PtGoods updatePtGoods = new PtGoods();
                        updatePtGoods.setMarketState(0);
                        goodsService.update(updatePtGoods,new EntityWrapper<PtGoods>().eq("id",ptGood.getId()));
                    }
                }
            }
            boolean flag = iPtMallMerchantService.delete(mallMerchantWrapper);
            if (merchantIds!=null&&merchantIds.size()>0){
                //插入商城和经销商的关系
                List<PtMallMerchant> ptMallMerchants = new ArrayList<>();
                for (Long merchantId:merchantIds) {
                    PtMallMerchant ptMallMerchant = new PtMallMerchant();
                    ptMallMerchant.setMallId(id);
                    ptMallMerchant.setMerchantId(merchantId);
                    ptMallMerchants.add(ptMallMerchant);
                }
                boolean falg = iPtMallMerchantService.insertBatch(ptMallMerchants);
            }

            map.put("stat", true);
            map.put("msg", "保存成功！");
        }catch (Exception e){
            logger.error("商家入住出错", e);
            map.put("stat", false);
            map.put("msg", "操作失败,请联系管理员");
        }
        return  map;
    }


}
