package com.yunsunyun.point.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunsunyun.dictionary.entity.KnDictionary;
import com.yunsunyun.dictionary.service.DictionaryService;
import com.yunsunyun.point.mapper.PtOrderMapper;
import com.yunsunyun.point.po.PtOrder;
import com.yunsunyun.point.po.PtOrderItems;
import com.yunsunyun.point.po.PtTip;
import com.yunsunyun.point.mapper.PtTipMapper;
import com.yunsunyun.point.po.PtTipSmsTemplate;
import com.yunsunyun.point.service.IPtOrderItemsService;
import com.yunsunyun.point.service.IPtTipService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.service.IPtTipSmsTemplateService;
import com.yunsunyun.point.service.PointUtil;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.dao.system.KnEmployeeDao;
import com.yunsunyun.xsimple.entity.system.KnEmployee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@Service
public class PtTipServiceImpl extends ServiceImpl<PtTipMapper, PtTip> implements IPtTipService {
    @Autowired
    private PtTipMapper ptTipMapper;
    @Autowired
    private PtOrderMapper ptOrderMapper;
    @Autowired
    private IPtTipSmsTemplateService iPtTipSmsTemplateService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private IPtOrderItemsService iPtOrderItemsService;
    @Autowired
    private KnEmployeeDao knEmployeeDao;


    @Override
    public boolean sendTip(Long orderId) {
        PtOrder ptOrder = ptOrderMapper.selectById(orderId);
        if (ptOrder!=null){
            KnEmployee merchant = knEmployeeDao.findMerchantById(ptOrder.getMerchantId());

            PtTip ptTip = new PtTip();
            ptTip.setMallId(ptOrder.getMallId());

            Map<String,String> parmas = new HashMap<>();
            parmas.put("userId",ptOrder.getMemberId()+"");
            JSONObject userInfo = PointUtil.getUserInfo(parmas);
            String name = null;
            if (userInfo!=null){
                name =  (String)userInfo.get("name");
            }

            //获取获取  消息模板
            Integer orderStatus = ptOrder.getOrderStatus();
            //用户 已经下单，订单处于待发货状态   用户发消息给经销商
            EntityWrapper<PtTipSmsTemplate> templateEntityWrapper = new EntityWrapper<>();
            templateEntityWrapper.eq("is_default",1);
            PtTipSmsTemplate template = null;
            if (orderStatus!=null&&orderStatus== Setting.OrderStatus.orderWaiting.getOrderTypeStatus()){
                ptTip.setSenderId(ptOrder.getMemberId());
                ptTip.setReceiverId(ptOrder.getMerchantId());
                ptTip.setReceiverName(merchant.getUserName());
                if (name!=null){
                    ptTip.setSenderName(name);
                }
                template = iPtTipSmsTemplateService.selectOne(templateEntityWrapper.eq("action_type", Setting.TempActionType.ordering.getTempActionTypeCode()));
            }
            //经销商 已经发货，订单处于已发货状态  经销商发消息给用户
            if (orderStatus!=null&&orderStatus==Setting.OrderStatus.orderSend.getOrderTypeStatus()){
                ptTip.setReceiverId(ptOrder.getMemberId());
                ptTip.setSenderId(ptOrder.getMerchantId());
                ptTip.setSenderName(merchant.getUserName());
                if (name!=null){
                    ptTip.setReceiverName(name);
                }
                template = iPtTipSmsTemplateService.selectOne(templateEntityWrapper.eq("action_type", Setting.TempActionType.ship.getTempActionTypeCode()));
            }
            //用户已经收货，订单处于 已完成状态  用户发消息给经销商
            if (orderStatus!=null&&orderStatus==Setting.OrderStatus.orderComplete.getOrderTypeStatus()){
                ptTip.setSenderId(ptOrder.getMemberId());
                ptTip.setReceiverId(ptOrder.getMerchantId());
                ptTip.setReceiverName(merchant.getUserName());
                if (name!=null){
                    ptTip.setSenderName(name);
                }
                template = iPtTipSmsTemplateService.selectOne(templateEntityWrapper.eq("action_type", Setting.TempActionType.receipt.getTempActionTypeCode()));
            }
            //经销商取消这笔订单，订单处于已取消状态 经销商发消息给用户
            if (orderStatus!=null&&orderStatus==Setting.OrderStatus.orderCancel.getOrderTypeStatus()){
                ptTip.setReceiverId(ptOrder.getMemberId());
                ptTip.setSenderId(ptOrder.getMerchantId());
                ptTip.setSenderName(merchant.getUserName());
                if (name!=null){
                    ptTip.setReceiverName(name);
                }
                template = iPtTipSmsTemplateService.selectOne(templateEntityWrapper.eq("action_type", Setting.TempActionType.cancel.getTempActionTypeCode()));
            }

            if (template!=null){
                KnDictionary dictionary = null;
                Long logiId = ptOrder.getLogiId();
                if (logiId!=null&&logiId!=0){
                    dictionary =dictionaryService.ReadDic(logiId);
                }
                String temp = template.getContent();

                List<PtOrderItems> ptOrderItemsList = iPtOrderItemsService.selectList(new EntityWrapper<PtOrderItems>().eq("order_id", ptOrder.getId()));

                if (StringUtils.isNotEmpty(name)){
                    temp = temp.replace(Setting.TempPlaceholder.member.getTempPlaceholderCode(),name);
                }else{
                    temp = temp.replace(Setting.TempPlaceholder.member.getTempPlaceholderCode(),"买家");
                }
                temp = temp.replace(Setting.TempPlaceholder.goods.getTempPlaceholderCode(),ptOrderItemsList.get(0).getName());
                if (dictionary!=null){
                    temp = temp.replace(Setting.TempPlaceholder.expressCompany.getTempPlaceholderCode(),dictionary.getDicValue());
                }else{
                    temp = temp.replace(Setting.TempPlaceholder.expressCompany.getTempPlaceholderCode(),"");
                }
                String logiNum = ptOrder.getLogiNum();
                if(logiNum!=null){
                    temp = temp.replace(Setting.TempPlaceholder.expressNum.getTempPlaceholderCode(),logiNum);
                }else{
                    temp = temp.replace(Setting.TempPlaceholder.expressNum.getTempPlaceholderCode(),"");
                }
                ptTip.setContent(temp);
                ptTip.setItemImage(ptOrderItemsList.get(0).getImageUrl());
            }
            ptTip.setOrderId(orderId);
            ptTip.setSendTime(new Date().getTime());
            ptTip.setOrderNum(ptOrder.getOrderNum());
            ptTip.setLogiNum(ptOrder.getLogiNum());
            return ptTipMapper.insert(ptTip)>0;

        }
        return false;
    }
}
