package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtMallGoods;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 商城-商品 关联表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtMallGoodsMapper extends BaseMapper<PtMallGoods> {
    List<Long> selectMallIdByGoodsId(Long goodsId);
}