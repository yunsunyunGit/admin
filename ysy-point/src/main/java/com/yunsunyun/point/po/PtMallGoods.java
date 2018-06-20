package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商城-商品 关联表
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@TableName("pt_mall_goods")
public class PtMallGoods extends Model<PtMallGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * 商城Id
     */
    @TableId("mall_id")
	private Long mallId;
    /**
     * 商品Id
     */
	@TableField("goods_id")
	private Long goodsId;

	public PtMallGoods() {
	}

	public PtMallGoods(Long mallId, Long goodsId) {
		this.mallId = mallId;
		this.goodsId = goodsId;
	}

	public Long getMallId() {
		return mallId;
	}

	public PtMallGoods setMallId(Long mallId) {
		this.mallId = mallId;
		return this;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public PtMallGoods setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.mallId;
	}

	@Override
	public String toString() {
		return "PtMallGoods{" +
			", mallId=" + mallId +
			", goodsId=" + goodsId +
			"}";
	}
}
