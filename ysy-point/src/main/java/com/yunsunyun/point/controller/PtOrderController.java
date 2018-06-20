package com.yunsunyun.point.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.dictionary.entity.KnDictionary;
import com.yunsunyun.dictionary.service.DictionaryService;
import com.yunsunyun.point.po.*;
import com.yunsunyun.point.service.*;
import com.yunsunyun.point.vo.MemberPointVo;
import com.yunsunyun.point.vo.PtOrderVo;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.ShiroUser;
import com.yunsunyun.xsimple.api.common.DataTable;
import com.yunsunyun.xsimple.dao.system.KnEmployeeDao;
import com.yunsunyun.xsimple.entity.system.KnEmployee;
import com.yunsunyun.xsimple.entity.system.KnRole;
import com.yunsunyun.xsimple.entity.system.KnUser;
import com.yunsunyun.xsimple.service.system.ResourceService;
import com.kingnode.diva.web.Servlets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.ServletRequest;
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
@RequestMapping("/point/order")
public class PtOrderController {

    @Autowired
    private  ResourceService resourceService;

    @Autowired
    private IPtMallMerchantService iPtMallMerchantService;

    @Autowired
    private IPtMallService iPtMallService;

    @Autowired
    private KnEmployeeDao empDao;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private IPtOrderService iPtOrderService;

    @Autowired
    private IPtOrderItemsService iPtOrderItemsService;

    @Autowired
    private IPtAddressService iPtAddressService;

    @Autowired
    IPtRedemptionRecordService iPtRedemptionRecordService;

    @Autowired
    private IPtTipService iPtTipService;
    @Autowired
    private IPtGoodsService iPtGoodsService;

    private static Logger logger = LoggerFactory.getLogger(PtOrderController.class);

    @RequiresPermissions("point-order")
    @RequestMapping(method = RequestMethod.GET)
    public String toOrderList(Model model){
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
        KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
        List<KnRole> knRoleList =  knUser.getRole();
        int flag = 0;
        for (KnRole knRole:knRoleList){
            if ("platformma".equals(knRole.getCode())){
                flag = 2;
            }else if("merchant".equals(knRole.getCode())){
                flag = 1;
            }
        }
        //拥有平台管理员的角色
        if (flag==2){
            Wrapper<PtMall> wrapper = new EntityWrapper<PtMall>();
            //设置未删除的商城
            wrapper.and("is_deleted = {0}",0);
            List<PtMall> mallList = iPtMallService.selectList(wrapper);
            List<KnEmployee> merchantList =  empDao.findMerchantEnable();
            model.addAttribute("mallList",mallList);
            model.addAttribute("merchantList",merchantList);
            model.addAttribute("userRole","platformma");
            //拥有经销商的角色
        }else if (flag==1){
            List<Long> mallIds = iPtMallMerchantService.getMallIdsByMerchantId(loginUser.getId());
            if (mallIds!=null&&mallIds.size()>0){
                List<PtMall> mallList =  iPtMallService.selectBatchIds(mallIds);
                model.addAttribute("mallList",mallList);
            }
            model.addAttribute("userRole","merchant");
        }

        Setting.OrderStatus[]  orderStatusList =  Setting.OrderStatus.values();
        List<KnDictionary>  knDictionaryList =   dictionaryService.findSonDictForFatherDictKey("order_range");
        List<KnDictionary>  expressList  =   dictionaryService.findSonDictForFatherDictKey("order_express");
        model.addAttribute("orderStatusList",orderStatusList);
        model.addAttribute("knDictionaryList",knDictionaryList);
        model.addAttribute("expressList",expressList);
        return  "point/orderList";
    }


    @RequiresPermissions("point-order")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public DataTable<PtOrderVo> getOrderList(DataTable<PtOrderVo> dt, ServletRequest request){
        try {
            Page<PtOrderVo> page = new Page<>();
            page.setCurrent(dt.pageNo()+1);
            page.setSize(dt.getiDisplayLength());
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

            //判断当前的用户角色
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            KnUser knUser = resourceService.FindUserByLoginName(loginUser.getLoginName());
            List<KnRole> knRoleList =  knUser.getRole();
            for (KnRole knRole:knRoleList){
                if("merchant".equals(knRole.getCode())){
                    searchParams.put("merchantId",loginUser.getId());
                    break;
                }
            }

            page  = iPtOrderService.getOrderVoList(page,searchParams);
            dt.setiTotalDisplayRecords(page.getTotal());
            dt.setAaData(page.getRecords());
        }catch (Exception e){
            logger.error("获取订单列表错误：{}", e);
        }
        return  dt;
    }

    /**
     * 发货
     * @param orderId
     * @param logiId
     * @param logiNum
     * @return
     */
    @RequiresPermissions("point-order")
    @RequestMapping(value = "/send-goods",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> sendGoods(@RequestParam("orderId")Long orderId,@RequestParam("logiId")Long logiId,@RequestParam("logiNum") String logiNum){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //改变订单状态
            PtOrder ptOrder = new PtOrder();
            ptOrder.setLogiId(logiId);
            ptOrder.setLogiNum(logiNum);
            ptOrder.setOrderStatus(Setting.OrderStatus.orderSend.getOrderTypeStatus());
            ptOrder.setShipTime(new Date().getTime());
            ptOrder.setIsNew(Setting.readStatus.unread.getReadCode());
            boolean flag = iPtOrderService.update(ptOrder,new EntityWrapper<PtOrder>().eq("id",orderId));

            //发送 发货消息
            iPtTipService.sendTip(orderId);


            map.put("stat", flag);
            map.put("msg", "保存成功！");
        }catch (Exception e){
            logger.error("订单发货错误：{}", e);
            map.put("stat", false);
            map.put("msg", "操作失败,请联系管理员");
        }
        return  map;
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @RequiresPermissions("point-order")
    @RequestMapping(value = "/cancel-order",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> cancelOrder(@RequestParam("orderId")Long orderId,@RequestParam("cancelReason") String cancelReason){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //改变订单状态
            PtOrder ptOrder = new PtOrder();
            ptOrder.setOrderStatus(Setting.OrderStatus.orderCancel.getOrderTypeStatus());
            ptOrder.setCancelTime(new Date().getTime());
            ptOrder.setCancelReason(cancelReason);
            ptOrder.setIsNew(Setting.readStatus.unread.getReadCode());
            boolean flag = iPtOrderService.update(ptOrder,new EntityWrapper<PtOrder>().eq("id",orderId));
            //插入商品兑换记录
            if (flag){

                PtOrder  selectOrder =  iPtOrderService.selectById(orderId);
                List<PtOrderItems>  ptOrderItemsList =  iPtOrderItemsService.selectList(new EntityWrapper<PtOrderItems>().eq("order_id",orderId));

                // 调用接口获取会员名称，会员ID
                Map JsonMap =  new HashMap<String, String>();
                JsonMap.put("userId",selectOrder.getMemberId()+"");
                JSONObject userInfo = PointUtil.getUserInfo(JsonMap);
                String memberName = null;
                String phoneNum = null;
                if (userInfo!=null){
                    memberName = (String)userInfo.get("name");
                    phoneNum = (String)userInfo.get("phoneNum");
                }

                for (PtOrderItems orderItems : ptOrderItemsList) {
                    //插入兑换记录
                    PtRedemptionRecord redemptionRecord = new PtRedemptionRecord();
                    redemptionRecord.setMallId(selectOrder.getMallId());
                    redemptionRecord.setMemberId(selectOrder.getMemberId());
                    redemptionRecord.setMemberName(memberName);
                    redemptionRecord.setMemberPhone(phoneNum);
                    redemptionRecord.setOrderId(orderId);
                    redemptionRecord.setImageUrl(orderItems.getImageUrl());
                    redemptionRecord.setMerchantId(selectOrder.getMerchantId());
                    redemptionRecord.setGoodsName(orderItems.getName());
                    redemptionRecord.setGoodsPoint(orderItems.getPoint()*orderItems.getNum());
                    redemptionRecord.setGoodsNum(orderItems.getNum());
                    redemptionRecord.setCreateTime(new Date().getTime());
                    redemptionRecord.setStatus(1);
                    iPtRedemptionRecordService.insert(redemptionRecord);

                    //库存回加
                    PtGoods ptGoods = new PtGoods();
                    PtGoods goods = iPtGoodsService.selectOne(new EntityWrapper<PtGoods>().eq("id", orderItems.getGoodsId()));
                    ptGoods.setStock(goods.getStock()+orderItems.getNum());
                    iPtGoodsService.update(ptGoods,new EntityWrapper<PtGoods>().eq("id",orderItems.getGoodsId()));

                }
                //发送消息
                iPtTipService.sendTip(orderId);
                //订单积分回加
                HashMap<String,Object> map2 = new HashMap();
                map2.put("userId",selectOrder.getMemberId()+"");
                map2.put("operId",selectOrder.getId()+"");
                List<MemberPointVo> memberPointVo = new ArrayList<>();
                Double transferPoint = selectOrder.getTransferPoint();
                if (transferPoint!=null&&transferPoint!=0D){
                    memberPointVo.add(new MemberPointVo("transferPoint",transferPoint));
                }
                Double rebatePoint = selectOrder.getRebatePoint();
                if (rebatePoint!=null&&rebatePoint!=0D){
                    memberPointVo.add(new MemberPointVo("rebatePoint",rebatePoint));
                }
                Double orderGoodsPoint = selectOrder.getOrderGoodsPoint();
                if (orderGoodsPoint!=null&&orderGoodsPoint!=0D){
                    memberPointVo.add(new MemberPointVo("ordinaryPoint",orderGoodsPoint));
                }
                Double vipPoint = selectOrder.getVipPoint();
                if (vipPoint!=null&&vipPoint!=0D){
                    memberPointVo.add(new MemberPointVo("vipPoint",vipPoint));
                }
                map2.put("pointList",memberPointVo);
                JSONObject jsonObject = PointUtil.updateUserIntegral(map2);
            }
            map.put("stat", flag);
            map.put("msg", "取消成功！");
        }catch (Exception e){
            logger.error("订单取消出错：{}", e);
            map.put("stat", false);
            map.put("msg", "操作失败,请联系管理员");
        }
        return  map;
    }

    @RequiresPermissions("point-order")
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    public String getOrderDetail(@PathVariable("id")int orderId,Model model){

        Wrapper<PtOrder>  ptOrderWrapper =  new EntityWrapper<>();
        PtOrder ptOrder = iPtOrderService.selectOne(ptOrderWrapper.eq("id",orderId));
        if (ptOrder!=null){
            List<PtOrderItems> orderItemsList = iPtOrderItemsService.selectList(new EntityWrapper<PtOrderItems>().eq("order_id", orderId));
            Long logiId = ptOrder.getLogiId();
            if (logiId!=null){
                KnDictionary logiCompy = dictionaryService.ReadDic(logiId);
                model.addAttribute("logiCompy",logiCompy);
            }
            KnEmployee merchant = empDao.findMerchantById(ptOrder.getMerchantId());
            PtAddress address = iPtAddressService.selectOne(new EntityWrapper<PtAddress>().eq("id", ptOrder.getAddressId()));
            //  2018/4/12 0012  会员个人信息要添加
            Map<String,String>  pamrs = new HashMap<>();
            pamrs.put("userId",ptOrder.getMemberId()+"");
            JSONObject userInfo = PointUtil.getUserInfo(pamrs);
            if (userInfo!=null){
                model.addAttribute("userName",userInfo.get("name"));
                model.addAttribute("phoneNum",userInfo.get("phoneNum"));
            }
            model.addAttribute("orderItemsList",orderItemsList);
            model.addAttribute("merchant",merchant);
            model.addAttribute("address",address);
            model.addAttribute("ptOrder",ptOrder);

        }

        return  "point/orderDetail";
    }


    @RequiresPermissions("point-order")
    @RequestMapping(value = "/detail-list/{id}")
    @ResponseBody
    public DataTable<PtOrderItems> getOrderDetailList(@PathVariable("id")int orderId,DataTable<PtOrderItems> dt){
        try {
            Page<PtOrderItems> page = new Page<>();
            page.setCurrent(dt.pageNo()+1);
            page.setSize(dt.getiDisplayLength());
            Page<PtOrderItems> ptOrderItemsPage = iPtOrderItemsService.selectPage(page, new EntityWrapper<PtOrderItems>().eq("order_id", orderId));
            dt.setiTotalDisplayRecords(ptOrderItemsPage.getTotal());
            dt.setAaData(ptOrderItemsPage.getRecords());
        } catch (Exception e) {
            logger.error("获取订单item列表出错：{}", e);
        }
        return  dt;
    }




}
