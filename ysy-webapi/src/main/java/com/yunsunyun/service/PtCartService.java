package com.yunsunyun.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.mapper.*;
import com.yunsunyun.point.po.*;
import com.yunsunyun.point.service.IPtTipService;
import com.yunsunyun.point.vo.MemberPointVo;
import com.yunsunyun.point.vo.PtCartItemVo;
import com.yunsunyun.point.vo.PtCartVo;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PtCartService extends ServiceImpl<PtCartMapper, PtCart> {

    @Autowired
    private PtCartMapper cartMapper;
    @Autowired
    private PtGoodsMapper goodsMapper;
    @Autowired
    private PtOrderMapper orderMapper;
    @Autowired
    private PtOrderItemsMapper itemsMapper;
    @Autowired
    private IPtTipService tipService;
    @Autowired
    private PtRedemptionRecordMapper recordMapper;
    @Autowired
    private PtMallGoodsMapper mallGoodsMapper;

    /**
     * 获取当前登录会员在当前商城购物车中的商品总量
     *
     * @param map
     * @param session
     * @return
     */
    public ResponseMap getCartTotalByUserId(ResponseMap map, HttpSession session) throws Exception {
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getId();
        Long mallId = (Long) session.getAttribute("mallId");
        int total = 0;
        List<PtCart> list = cartMapper.selectList(new EntityWrapper<PtCart>().where("member_id = {0}", userId).and("mall_id = {0}", mallId));
        if (list != null && list.size() > 0) {
            for (PtCart cart : list) {
                total += cart.getGoodsNum();
            }
        }
        map.setCode(200);
        map.setMsg("操作成功!");
        Map<String, Object> totalMap = new HashMap<>();
        totalMap.put("total", total);
        map.setData(totalMap);
        return map;
    }

    /**
     * 根据传入的商品id往购物车中添加一条数据
     *
     * @param goodsId
     * @param map
     * @param session
     * @return
     */
    @Transactional
    public ResponseMap addToCartByGoodsId(Long goodsId, ResponseMap map, HttpSession session) throws Exception {
        Integer result = 0;
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long mallId = (Long) session.getAttribute("mallId");
        PtCart flag = selectOne(new EntityWrapper<PtCart>().where("goods_id = {0}", goodsId).and("mall_id = {0}",mallId).and("member_id = {0}",user.getId()));
        if (flag != null) {
            flag.setGoodsNum(flag.getGoodsNum() + 1);
            result = cartMapper.updateById(flag);
        } else {
            PtGoods goods = goodsMapper.selectById(goodsId);
            PtCart cart = new PtCart();
            cart.setGoodsId(goods.getId());
            cart.setGoodsNum(1);
            cart.setMerchantId(goods.getMerchantId());
            cart.setMemberId(user.getId());
            cart.setMallId(mallId);
            result = cartMapper.insert(cart);
        }
        if (result <= 0) {
            map.setCode(500);
            map.setMsg("操作失败!");
        } else {
            map.setCode(200);
            map.setMsg("操作成功!");
        }
        return map;
    }

    /**
     * 修改购物车商品数量
     *
     * @param id
     * @param map
     * @param goodsNum
     * @return
     */
    @Transactional
    public ResponseMap updateNumById(Long id, ResponseMap map, Integer goodsNum) throws Exception {
        PtCart cart = new PtCart();
        cart.setId(id);
        cart.setGoodsNum(goodsNum);
        Integer result = cartMapper.updateGoodsNumById(cart);
        if (result <= 0) {
            map.setCode(500);
            map.setMsg("操作失败!");
        } else {
            map.setCode(200);
            map.setMsg("操作成功!");
        }
        return map;
    }

    /**
     * 获取当前登录用户在当前商城中的购物车列表
     *
     * @param pageSize
     * @param pageNo
     * @param session
     * @param map
     * @return
     */
    public ResponseMap ajaxCartList(Integer pageSize, Integer pageNo, HttpSession session, ResponseMap map) throws Exception {
        Long mallId = (Long) session.getAttribute("mallId");
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long memberId = user.getId();
        if (pageSize == 0 || pageSize == null) {
            pageSize = 10;
        }
        if (pageNo == 0 || pageNo == null) {
            pageNo = 1;
        }
        Integer offset = (pageNo - 1) * pageSize;
        List<PtCartVo> list = cartMapper.ajaxCartListByMallIdMemberId(mallId, memberId, pageSize, offset);
        Integer totalSize = cartMapper.getCartCountByMallIdMemberId(mallId, memberId);
        if (list == null || list.size() <= 0) {
            map.setCode(404);
            map.setMsg("数据为空");
        } else {
            map.setCode(200);
            map.setMsg("操作成功!");
            map.setTotalSize(totalSize);
            map.setData(list);
        }
        return map;
    }

    /**
     * 购物车结算
     *
     * @param map
     * @param session
     * @param addressId
     * @param cartIds
     * @return
     */
    public ResponseMap settleCarts(ResponseMap map, HttpSession session, Long addressId, Long[] cartIds) throws Exception {
        Long mallId = (Long) session.getAttribute("mallId");
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();

        //计算订单积分总数
        Double orderGoodsPoint = 0d;

        /**     判断商品库存、上架状态  start   **/
        List<PtCart> carts = cartMapper.selectList(new EntityWrapper<PtCart>().in("id", cartIds));
        for (PtCart ptCart : carts) {
            PtGoods tempGoods = goodsMapper.selectById(ptCart.getGoodsId());

            //判断商品是否已删除
            if (tempGoods.getIsDeleted() == 1) {
                map.setCode(404);
                map.setMsg("您的商品信息有变更，请重新购买");
                return map;
            }

            //商品是否已下架
            PtMallGoods temp = mallGoodsMapper.selectOne(new PtMallGoods(mallId, tempGoods.getId()));
            if (temp == null) {
                map.setCode(404);
                map.setMsg(tempGoods.getName() + "商品已下架,请不要购买!");
                return map;
            }

            //判断商品库存是否充足
            if (ptCart.getGoodsNum() > tempGoods.getStock()) {
                map.setCode(404);
                map.setMsg(tempGoods.getName() + "商品库存不足!");
                return map;
            }

            orderGoodsPoint += tempGoods.getPoint() * ptCart.getGoodsNum();
        }
        /**     判断商品库存、上架状态  end   **/

        //判断是否选择了送货地址
        if (addressId == null || addressId <= 0) {
            map.setCode(500);
            map.setMsg("未选择收货地址");
            return map;
        }

        //判断会员积分是否足够
        Double totalIntegral = (Double) session.getAttribute("totalIntegral");
        if (totalIntegral < orderGoodsPoint) {
            map.setCode(403);
            map.setMsg("会员积分不足!");
            return map;
        }

        //创建订单编号
        String orderNum = System.currentTimeMillis() + "" + (int) (Math.random() * 10);

        //获取会员当前四种积分数组
        List<MemberPointVo> integralList = (List<MemberPointVo>) session.getAttribute("integralList");

        //创建修改会员积分数组参数
        List<MemberPointVo> updateIntegralList = PointUtil.setIntegral(orderGoodsPoint, integralList);

        Map<String, Object> param = new HashMap<>();
        param.put("userId", user.getId() + "");
        param.put("operId", orderNum);
        param.put("pointList", updateIntegralList);
        JSONObject result = PointUtil.updateUserIntegral(param);
        if (result == null) {
            map.setCode(500);
            map.setMsg("积分扣除失败!");
            return map;
        }
        if (result.getInteger("code") != 200) {
            map.setCode(500);
            map.setMsg("积分扣除失败!");
            return map;
        }

        try {
            //开始创建订单
            createOrder(updateIntegralList, addressId, cartIds, orderNum, mallId, user);
            PointUtil.updateSession(session);
            HashMap<String,Object> dataMap = new HashMap<>();
            dataMap.put("orderGoodsPoint",orderGoodsPoint);
            map.setData(dataMap);
            map.setCode(200);
            map.setMsg("操作成功!");
        } catch (Exception e) {
            map.setCode(500);
            map.setMsg("操作失败!");
            for (int i = 0; i < updateIntegralList.size(); i++) {
                updateIntegralList.get(i).setIntegral(-updateIntegralList.get(i).getIntegral());
            }
            Map<String, Object> param1 = new HashMap<>();
            param1.put("userId", user.getId());
            param1.put("operId", orderNum);
            param1.put("pointList", updateIntegralList);
            PointUtil.updateUserIntegral(param);
            PointUtil.updateSession(session);
        }
        return map;
    }

    /**
     * 创建订单
     *
     * @param updateIntegralList
     * @param addressId
     * @param cartIds
     * @param orderNum
     * @param mallId
     * @param user
     */
    @Transactional
    public void createOrder(List<MemberPointVo> updateIntegralList,
                            Long addressId,
                            Long[] cartIds, String orderNum,
                            Long mallId, PointShiroUser user) {
        List<PtCartVo> list = cartMapper.ajaxCartListByCartIds(cartIds);
        for (int i = 0; i < list.size(); i++) {
            List<PtCartItemVo> items = cartMapper.ajaxCartItemsListByCartIds(cartIds, list.get(i).getId());
            list.get(i).setItems(items);
        }
        for (PtCartVo cartVo : list) {
            PtOrder order = new PtOrder();
            order.setMallId(mallId);
            order.setMemberId(user.getId());
            order.setAddressId(addressId);
            order.setMerchantId(cartVo.getId());
            order.setOrderNum(orderNum);
            order.setOrderStatus(0);
            order.setCreateId(user.getId());
            order.setCreateTime(System.currentTimeMillis());
            if (updateIntegralList.get(0) != null) {
                order.setTransferPoint(updateIntegralList.get(0).getIntegral());
            }
            if (updateIntegralList.size() == 2) {
                order.setRebatePoint(updateIntegralList.get(1).getIntegral());
            }
            if (updateIntegralList.size() == 3) {
                order.setOrdinaryPoint(updateIntegralList.get(2).getIntegral());
            }
            if (updateIntegralList.size() == 4) {
                order.setVipPoint(updateIntegralList.get(3).getIntegral());
            }
            orderMapper.insertAndReturnId(order);
            Double orderPoint = 0d;  //拆单时将总积分分开存储
            for (PtCartItemVo itemVo : cartVo.getItems()) {
                PtOrderItems item = new PtOrderItems();
                item.setOrderId(order.getId());
                item.setGoodsId(itemVo.getGoodsId());
                item.setNum(itemVo.getGoodsNum());
                item.setImageUrl(itemVo.getImageUrl());
                item.setName(itemVo.getGoodsName());
                item.setPoint(itemVo.getPoint());
                item.setReferencePrice(itemVo.getReferencePrice());
                item.setSn(itemVo.getSn());
                PtRedemptionRecord record = new PtRedemptionRecord();
                record.setMallId(order.getMallId());
                record.setMemberId(order.getMemberId());
                record.setMemberName(user.getName());
                record.setMemberPhone(user.getPhoneNum());
                record.setMerchantId(order.getMerchantId());
                record.setGoodsName(itemVo.getGoodsName());
                record.setGoodsNum(itemVo.getGoodsNum());
                record.setGoodsPoint(-itemVo.getPoint() * itemVo.getGoodsNum());
                record.setCreateTime(System.currentTimeMillis());
                record.setOrderId(order.getId());
                record.setImageUrl(item.getImageUrl());
                record.setStatus(0);
                PtGoods updateGood = goodsMapper.selectById(itemVo.getGoodsId());
                updateGood.setStock(updateGood.getStock() - itemVo.getGoodsNum());
                goodsMapper.updateById(updateGood);
                itemsMapper.insert(item);
                recordMapper.insert(record);
                orderPoint += item.getPoint() * item.getNum();
            }
            order.setOrderGoodsPoint(orderPoint);
            orderMapper.updateById(order);
            tipService.sendTip(order.getId());
        }
        cartMapper.delete(new EntityWrapper<PtCart>().in("id",cartIds));
    }

    /**
     * 根据购物车id获取商品信息
     *
     * @param map
     * @param session
     * @param ids
     * @return
     */
    public ResponseMap getCartInfoByIds(ResponseMap map, HttpSession session, Long[] ids) throws Exception {
        Long mallId = (Long) session.getAttribute("mallId");
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Long memberId = user.getId();
        List<PtCartVo> list = cartMapper.ajaxCartListByIds(mallId, memberId, ids);
        for (PtCartVo cartVo : list){
            List<PtCartItemVo> itemVos = cartMapper.secondGetCartItems(cartVo.getId(),mallId, memberId, ids);
            cartVo.setItems(itemVos);
        }
        map.setCode(200);
        map.setMsg("操作成功!");
        map.setData(list);
        return map;
    }
}
