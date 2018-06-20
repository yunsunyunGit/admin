package com.yunsunyun.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.dictionary.dao.KnDictionaryDao;
import com.yunsunyun.point.mapper.PtOrderMapper;
import com.yunsunyun.point.po.PtOrder;
import com.yunsunyun.point.service.IPtTipService;
import com.yunsunyun.point.vo.PtOrderInfoVo;
import com.yunsunyun.point.vo.PtOrdersVo;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Service
public class PtOrdersService extends ServiceImpl<PtOrderMapper, PtOrder> {

    @Autowired
    private PtOrderMapper orderMapper;
    @Autowired
    private KnDictionaryDao dictionaryDao;
    @Autowired
    private IPtTipService tipService;

    /**
     * 分页查询当前登录会员，所在商城的订单列表
     *
     * @param map
     * @param orderStatus （-1:全部;0:待发货;1:已发货;2:已完成;3:已取消;）
     * @param pageSize
     * @param pageNo
     * @param session
     * @return
     */
    @Transactional
    public ResponseMap ajaxDataByStatus(ResponseMap map, Integer orderStatus, Integer pageSize, Integer pageNo, HttpSession session) throws Exception{
        Long mallId = (Long) session.getAttribute("mallId");
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long memberId = user.getId();
        if (pageSize == 0 || pageSize == null){
            pageSize = 10;
        }
        if (pageNo == 0 || pageNo == null){
            pageNo = 1;
        }
        Integer pageOffset = (pageNo - 1) * pageSize;
        List<PtOrdersVo> list = orderMapper.selectPageByStatus(mallId, memberId, orderStatus, pageOffset, pageSize);
        if(list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setOrderTotal();
            }
        }
        Wrapper<PtOrder> wrapper = new EntityWrapper<>();
        wrapper.where("mall_id = {0}", mallId).and("member_id = {0}", memberId);
        if (orderStatus != -1) {
            wrapper.and("order_status = {0}", orderStatus);
        }
        Integer totalSize = orderMapper.selectCount(wrapper);
        orderMapper.updateIsNew(mallId,memberId,orderStatus);
        map.setTotalSize(totalSize);
        map.setCode(200);
        map.setMsg("操作成功!");
        map.setData(list);
        return map;
    }

    /**
     * 订单签收
     * @param map
     * @param id
     * @return
     */
    @Transactional
    public ResponseMap signOrderById(ResponseMap map, Long id) throws Exception{
        if (id == null || id <= 0){
            map.setCode(500);
            map.setMsg("操作失败,订单id无效!");
            return map;
        }
        PtOrder order = new PtOrder();
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        order.setId(id);
        order.setUpdateId(user.getId());
        order.setUpdateTime(System.currentTimeMillis());
        order.setCompleteTime(System.currentTimeMillis());
        order.setOrderStatus(Setting.OrderStatus.orderComplete.getOrderTypeStatus());
        order.setIsNew(0);
        Integer result = orderMapper.updateById(order);
        tipService.sendTip(id);
        if(result != 1){
            map.setCode(500);
            map.setMsg("操作失败!");
        }else{
            map.setCode(200);
            map.setMsg("操作成功!");
        }
        return map;
    }

    /**
     * 根据订单id查看订单详情
     * @param map
     * @param id
     * @return
     */
    public ResponseMap getOrderInfoById(ResponseMap map, Long id) throws Exception{
        if (id == null || id <= 0){
            map.setCode(500);
            map.setMsg("操作失败,订单id无效!");
            return map;
        }
        PtOrderInfoVo orderInfo = orderMapper.getPtOrderVoInfoById(id);
        if(orderInfo.getLogiId() != null && orderInfo.getLogiId().intValue() > 0) {
            orderInfo.setLogiName(dictionaryDao.findOne(orderInfo.getLogiId()).getDicValue());
        }
        if(orderInfo == null){
            map.setCode(500);
            map.setMsg("操作失败!");
        }else{
            map.setCode(200);
            map.setMsg("操作成功!");
            map.setData(orderInfo);
        }
        return map;
    }

    /**
     * 获取当前登录会员在当前商城中各状态订单的未读数量
     *
     *
     * @param map
     * @param session
     * @return
     */
    public ResponseMap getIsNewCount(ResponseMap map, HttpSession session) throws Exception{
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long memberId = user.getId();
        Long mallId = (Long) session.getAttribute("mallId");
        HashMap<String,Object> resultMap = orderMapper.getAllOrderIsNewCount(memberId,mallId);
        if(resultMap == null){
            map.setCode(500);
            map.setMsg("操作失败!");
        }else{
            map.setCode(200);
            map.setMsg("操作成功!");
            map.setData(resultMap);
        }
        return map;
    }

    /**
     * 当前登录会员根据订单id逻辑删除订单
     *
     * @param map
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional
    public ResponseMap memberDeleteOrder(ResponseMap map, Long id) throws Exception{
        PtOrder order = new PtOrder();
        order.setId(id);
        order.setIsDeleted(1);
        Integer result = orderMapper.updateById(order);
        if(result != 1){
            map.setCode(500);
            map.setMsg("操作失败!");
        }else{
            map.setCode(200);
            map.setMsg("操作成功!");
        }
        return map;
    }
}
