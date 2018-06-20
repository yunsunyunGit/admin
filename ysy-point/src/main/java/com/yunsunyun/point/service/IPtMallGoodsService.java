package com.yunsunyun.point.service;

import com.yunsunyun.point.po.PtMallGoods;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商城-商品 关联表 服务类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface IPtMallGoodsService extends IService<PtMallGoods> {
	List<Long> selectMallIdByGoodsId(Long goodsId);
}
