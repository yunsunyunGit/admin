package com.yunsunyun.point.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtOrder;
import com.yunsunyun.point.mapper.PtOrderMapper;
import com.yunsunyun.point.service.IPtOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.vo.PtOrderVo;
import com.yunsunyun.xsimple.Setting;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
public class PtOrderServiceImpl extends ServiceImpl<PtOrderMapper, PtOrder> implements IPtOrderService {

    @Autowired
    private  PtOrderMapper ptOrderMapper;

    private static Logger logger = LoggerFactory.getLogger(PtOrderServiceImpl.class);

    @Override
    public void orderSchedule() {
        logger.info("---------------------->>>>>执行定时任务！！！！！！！");
        List<PtOrder> ptOrders = ptOrderMapper.selectList(new EntityWrapper<PtOrder>().eq("order_status",Setting.OrderStatus.orderSend.getOrderTypeStatus()));
        if (ptOrders!=null&&ptOrders.size()>0){
            long time = new Date().getTime();//东八区加8个小时
            for (PtOrder ptOrder : ptOrders) {
                Long  overShipTime =  time - ptOrder.getShipTime();
                if (1209600000<overShipTime){//超过15天
                    PtOrder updateOrder = new PtOrder();
                    updateOrder.setId(ptOrder.getId());
                    updateOrder.setOrderStatus(Setting.OrderStatus.orderComplete.getOrderTypeStatus());
                    updateOrder.setCompleteTime(new Date().getTime());
                    ptOrderMapper.updateById(updateOrder);
                }
            }
        }

    }

    @Override
    public Page<PtOrderVo> getOrderVoList(Page<PtOrderVo> page, Map<String, Object> searchParams) throws Exception {

        Map<String,Object> params = new HashMap<>();
        if (searchParams != null && searchParams.size() != 0) {
            if (searchParams.containsKey("EQ_mallId") && !Strings.isNullOrEmpty(searchParams.get("EQ_mallId").toString().trim())) {
                params.put("mallId",searchParams.get("EQ_mallId").toString().trim());
            }
            if (searchParams.containsKey("EQ_orderNum") && !Strings.isNullOrEmpty(searchParams.get("EQ_orderNum").toString().trim())) {
                params.put("orderNum",searchParams.get("EQ_orderNum").toString().trim());
            }
            if (searchParams.containsKey("EQ_merchantId") && !Strings.isNullOrEmpty(searchParams.get("EQ_merchantId").toString().trim())) {
                params.put("merchantId",searchParams.get("EQ_merchantId").toString().trim());
            }
            if (searchParams.containsKey("LIKE_orderStartTime") && !Strings.isNullOrEmpty(searchParams.get("LIKE_orderStartTime").toString().trim()) && searchParams.containsKey("LIKE_orderEndTime") && !Strings.isNullOrEmpty(searchParams.get("LIKE_orderEndTime").toString().trim())  ) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                params.put("timeStart",sdf.parse(searchParams.get("LIKE_orderStartTime").toString().trim()).getTime());
                params.put("timeEnd",sdf.parse(searchParams.get("LIKE_orderEndTime").toString().trim()).getTime());
            }
            if (searchParams.containsKey("EQ_orderGoodsPoint") && !Strings.isNullOrEmpty(searchParams.get("EQ_orderGoodsPoint").toString().trim()) && searchParams.get("EQ_orderGoodsPoint").toString().trim().contains("-")  ) {
                String[] startEndPoint =  searchParams.get("EQ_orderGoodsPoint").toString().trim().split("-");
                params.put("pointStart",startEndPoint[0]);
                params.put("pointEnd",startEndPoint[1]);
            }
            if (searchParams.containsKey("LIKE_receiverName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_receiverName").toString().trim())) {
                params.put("receiverName",searchParams.get("LIKE_receiverName").toString().trim());
            }
            if (searchParams.containsKey("EQ_orderStatus") && !Strings.isNullOrEmpty(searchParams.get("EQ_orderStatus").toString().trim())) {
                params.put("orderStatus",searchParams.get("EQ_orderStatus").toString().trim());
            }

            if (searchParams.containsKey("merchantId") && !Strings.isNullOrEmpty(searchParams.get("merchantId").toString().trim())){
                params.put("merchantId",searchParams.get("merchantId").toString().trim());
            }

        }

        return page.setRecords(ptOrderMapper.getPtOrderVoList(page,params));
    }


    @Override
    public List<PtOrderVo> getOrderVoList(Map<String, Object> searchParams) throws Exception {

        Map<String,Object> params = new HashMap<>();
        if (searchParams != null && searchParams.size() != 0) {
            if (searchParams.containsKey("EQ_mallId") && !Strings.isNullOrEmpty(searchParams.get("EQ_mallId").toString().trim())) {
                params.put("mallId",searchParams.get("EQ_mallId").toString().trim());
            }
            if (searchParams.containsKey("EQ_orderNum") && !Strings.isNullOrEmpty(searchParams.get("EQ_orderNum").toString().trim())) {
                params.put("orderNum",searchParams.get("EQ_orderNum").toString().trim());
            }
            if (searchParams.containsKey("EQ_merchantId") && !Strings.isNullOrEmpty(searchParams.get("EQ_merchantId").toString().trim())) {
                params.put("merchantId",searchParams.get("EQ_merchantId").toString().trim());
            }
            if (searchParams.containsKey("LIKE_orderStartTime") && !Strings.isNullOrEmpty(searchParams.get("LIKE_orderStartTime").toString().trim()) && searchParams.containsKey("LIKE_orderEndTime") && !Strings.isNullOrEmpty(searchParams.get("LIKE_orderEndTime").toString().trim())  ) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                params.put("timeStart",sdf.parse(searchParams.get("LIKE_orderStartTime").toString().trim()).getTime());
                params.put("timeEnd",sdf.parse(searchParams.get("LIKE_orderEndTime").toString().trim()).getTime());
            }

            if (searchParams.containsKey("EQ_orderStartTime") && !Strings.isNullOrEmpty(searchParams.get("EQ_orderStartTime").toString().trim()) && searchParams.containsKey("EQ_orderEndTime") && !Strings.isNullOrEmpty(searchParams.get("EQ_orderEndTime").toString().trim())  ) {
                params.put("timeStart",searchParams.get("EQ_orderStartTime").toString().trim());
                params.put("timeEnd",searchParams.get("EQ_orderEndTime").toString().trim());
            }

            if (searchParams.containsKey("EQ_orderGoodsPoint") && !Strings.isNullOrEmpty(searchParams.get("EQ_orderGoodsPoint").toString().trim()) && searchParams.get("EQ_orderGoodsPoint").toString().trim().contains("-")  ) {
                String[] startEndPoint =  searchParams.get("EQ_orderGoodsPoint").toString().trim().split("-");
                params.put("pointStart",startEndPoint[0]);
                params.put("pointEnd",startEndPoint[1]);
            }
            if (searchParams.containsKey("LIKE_receiverName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_receiverName").toString().trim())) {
                params.put("receiverName",searchParams.get("LIKE_receiverName").toString().trim());
            }
            if (searchParams.containsKey("EQ_orderStatus") && !Strings.isNullOrEmpty(searchParams.get("EQ_orderStatus").toString().trim())) {
                params.put("orderStatus",searchParams.get("EQ_orderStatus").toString().trim());
            }

            if (searchParams.containsKey("merchantId") && !Strings.isNullOrEmpty(searchParams.get("merchantId").toString().trim())){
                params.put("merchantId",searchParams.get("merchantId").toString().trim());
            }

        }

        return ptOrderMapper.getPtOrderVoList(params);
    }


}
