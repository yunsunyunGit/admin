package com.yunsunyun.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.dictionary.dao.KnDictionaryDao;
import com.yunsunyun.dictionary.entity.KnDictionary;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.mapper.*;
import com.yunsunyun.point.po.*;
import com.yunsunyun.point.service.IPtTipService;
import com.yunsunyun.point.vo.GoodsGalleryVo;
import com.yunsunyun.point.vo.MemberPointVo;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2018-03-30
 */
@Service
public class PtGoodsService extends ServiceImpl<PtGoodsMapper, PtGoods> {

    @Autowired
    private PtGoodsMapper goodsMapper;
    @Autowired
    private PtMallGoodsMapper mallGoodsMapper;
    @Autowired
    private PtCatagoryGoodsMapper catagoryGoodsMapper;
    @Autowired
    private KnDictionaryDao dictionaryDao;
    @Autowired
    private PtOrderMapper orderMapper;
    @Autowired
    private PtOrderItemsMapper itemsMapper;
    @Autowired
    private IPtTipService tipService;
    @Autowired
    private PtRedemptionRecordMapper recordMapper;
    /**
     * 前端页面分页查询商品列表
     *
     * @param page       mybatis-plus分页对象
     * @param goodsType  商品首页推荐类别（字典表首页推荐分类子项的dic_key）
     * @param catagoryId 商品所属分类
     * @param keyword    搜索关键字（目前只作为商品名）
     * @return
     */
    public Page<PtGoods> selectListWithParam(Page<PtGoods> page, String goodsType, Integer catagoryId, String keyword,HttpSession session) throws Exception {
        Long mallId = (Long) session.getAttribute("mallId");
        Wrapper<PtGoods> wrapper = new EntityWrapper<>();
        wrapper.where("audit_state = 1");
        wrapper.and("is_deleted = 0");
        //添加商品搜索关键字过滤
        if (StringUtils.isNotEmpty(keyword)) {
            wrapper.like("name", keyword);
        }

        //添加首页推荐分类过滤
        Long goodsTypeId = -1l;
        if (StringUtils.isNotEmpty(goodsType)) {
            goodsTypeId = dictionaryDao.findByDicKey(goodsType).getId();
        }
        if (goodsTypeId > 0) {
            wrapper.and("goods_type = {0}", goodsTypeId);
        }

        //添加商品分类过滤
        Long[] types = null;
        if (catagoryId != null && catagoryId > 0) {
            List<PtCatagoryGoods> list = catagoryGoodsMapper.selectList(new EntityWrapper<PtCatagoryGoods>().where("catagory_id = {0}", catagoryId));
            if (list != null && list.size() > 0) {
                types = new Long[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    types[i] = list.get(i).getGoodsId();
                }
                wrapper.in("id", types);
            }
        }

        //添加商品已上架商城过滤
        Long[] malls = null;
        if (mallId != null && mallId > 0) {
            List<PtMallGoods> list = mallGoodsMapper.selectList(new EntityWrapper<PtMallGoods>().where("mall_id = {0}", mallId));
            if (list != null && list.size() > 0) {
                malls = new Long[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    malls[i] = list.get(i).getGoodsId();
                }
                wrapper.in("id", malls);
            }
        }
        wrapper.orderBy("point",true);
        page = selectPage(page, wrapper);
        return page;
    }

    /**
     * 查询指定dicKey的字典表中数据
     *
     * @param dicKey
     * @return
     */
    public KnDictionary selectDictByKey(String dicKey) throws Exception {
        if (StringUtils.isEmpty(dicKey)) {
            return null;
        }
        return dictionaryDao.findByDicKey(dicKey);
    }

    /**
     * 根据传入的商品id查询出商品详情以及该商品相册
     *
     * @param map
     * @param id
     * @param session
     * @return
     */
    public ResponseMap queryGoodsAndPics(ResponseMap map, Long id, HttpSession session) throws Exception {
        Double totalIntegral = (Double) session.getAttribute("totalIntegral");
        GoodsGalleryVo goodsInfo = goodsMapper.selectGoodsGalleryById(id);
        if (goodsInfo.getPoint() > totalIntegral) {
            map.setCode(403);
            map.setMsg("会员积分不足!");
        } else {
            map.setCode(200);
            map.setMsg("操作成功!");
            map.setData(goodsInfo);
        }
        return map;
    }

    /**
     * 当前登录会员兑换商品
     *
     * @param map
     * @param id
     * @param goodsNum
     * @param addressId
     * @param session
     * @return
     */
    public ResponseMap convertGoods(ResponseMap map, Long id, Integer goodsNum, Long addressId, HttpSession session) throws Exception {
        Long mallId = (Long) session.getAttribute("mallId");
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        PtGoods goods = goodsMapper.selectById(id);
        Double orderGoodsPoint = goodsNum * goods.getPoint();
        if (goods.getIsDeleted() == 1){
            map.setCode(404);
            map.setMsg("您的商品信息有变更，请重新购买");
            return map;
        }
        //商品是否已下架
        PtMallGoods temp = mallGoodsMapper.selectOne(new PtMallGoods(mallId, goods.getId()));
        if (temp == null) {
            map.setCode(404);
            map.setMsg(goods.getName() + "商品已下架,请不要购买!");
            return map;
        }

        //判断商品库存是否充足
        if (goodsNum > goods.getStock()) {
            map.setCode(404);
            map.setMsg(goods.getName() + "商品库存不足!");
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

        //开始扣除积分
        Map<String, Object> param = new HashMap<>();
        param.put("userId", user.getId()+"'");
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
            createOrder(mallId,user,addressId,orderNum,goodsNum,goods,updateIntegralList,orderGoodsPoint);
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
            PointUtil.updateUserIntegral(param1);
            PointUtil.updateSession(session);
        }
        return map;
    }

    /**
     * 创建订单
     *
     * @param mallId
     * @param user
     * @param addressId
     * @param orderNum
     * @param goodsNum
     * @param goods
     * @param updateIntegralList
     * @param orderGoodsPoint
     * @throws Exception
     */
    @Transactional
    public void createOrder(Long mallId,PointShiroUser user ,Long addressId,String orderNum,Integer goodsNum,PtGoods goods,List<MemberPointVo> updateIntegralList,Double orderGoodsPoint) throws Exception{
        PtOrder order = new PtOrder();
        order.setMallId(mallId);
        order.setMemberId(user.getId());
        order.setAddressId(addressId);
        order.setMerchantId(goods.getMerchantId());
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
        order.setOrderGoodsPoint(orderGoodsPoint);
        orderMapper.insertAndReturnId(order);
        PtOrderItems item = new PtOrderItems();
        item.setOrderId(order.getId());
        item.setGoodsId(goods.getId());
        item.setNum(goodsNum);
        item.setImageUrl(goods.getImageUrl());
        item.setName(goods.getName());
        item.setPoint(goods.getPoint());
        item.setReferencePrice(goods.getReferencePrice());
        item.setSn(goods.getSn());
        PtRedemptionRecord record = new PtRedemptionRecord();
        record.setMallId(order.getMallId());
        record.setMemberId(order.getMemberId());
        record.setMemberName(user.getName());
        record.setMemberPhone(user.getPhoneNum());
        record.setMerchantId(order.getMerchantId());
        record.setGoodsName(goods.getName());
        record.setGoodsNum(goodsNum);
        record.setGoodsPoint(-orderGoodsPoint);
        record.setCreateTime(System.currentTimeMillis());
        record.setOrderId(order.getId());
        record.setImageUrl(item.getImageUrl());
        record.setStatus(0);
        goods.setStock(goods.getStock() - goodsNum);
        goodsMapper.updateById(goods);
        itemsMapper.insert(item);
        recordMapper.insert(record);
        tipService.sendTip(order.getId());
    }
}
