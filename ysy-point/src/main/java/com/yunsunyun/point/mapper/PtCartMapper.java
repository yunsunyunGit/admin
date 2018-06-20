package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtCart;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunsunyun.point.vo.PtCartItemVo;
import com.yunsunyun.point.vo.PtCartVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 购物车表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtCartMapper extends BaseMapper<PtCart> {

    /**
     * 获取当前登录用户在当前商城中的购物车列表
     *
     * @param mallId
     * @param memberId
     * @param pageSize
     * @param offset
     * @return
     */
    List<PtCartVo> ajaxCartListByMallIdMemberId(@Param("mallId") Long mallId, @Param("memberId") Long memberId, @Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    /**
     * 获取购物车分页总数
     *
     * @param mallId
     * @param memberId
     * @return
     */
    Integer getCartCountByMallIdMemberId(@Param("mallId") Long mallId,@Param("memberId") Long memberId);

    /**
     * 根据购物车id获取商家对象列表
     *
     * @param cartIds
     * @return
     */
    List<PtCartVo> ajaxCartListByCartIds(@Param("cartIds") Long[] cartIds);

    /**
     * 根据购物车id获取商品对象列表
     * @param cartIds
     * @param merchantId
     * @return
     */
    List<PtCartItemVo> ajaxCartItemsListByCartIds(@Param("cartIds") Long[] cartIds, @Param("merchantId") Long merchantId);

    /**
     * 修改购物车数量
     *
     * @param cart
     * @return
     */
    Integer updateGoodsNumById(PtCart cart);

    /**
     * 根据购物车id获取商品信息
     *
     * @param mallId
     * @param memberId
     * @param ids
     * @return
     */
    List<PtCartVo> ajaxCartListByIds(@Param("mallId") Long mallId,@Param("memberId") Long memberId,@Param("ids") Long[] ids);

    List<PtCartItemVo> secondGetCartItems(@Param("id") Long id,@Param("mallId") Long mallId,@Param("memberId") Long memberId,@Param("ids") Long[] ids);
}