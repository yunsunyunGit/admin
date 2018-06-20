package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-04-11
 */
@TableName("pt_catagory_goods")
public class PtCatagoryGoods extends Model<PtCatagoryGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * 参照分类表id
     */
    @TableId("catagory_id")
	private Long catagoryId;
    /**
     * 参照商品表id
     */
	@TableField("goods_id")
	private Long goodsId;


	public Long getCatagoryId() {
		return catagoryId;
	}

	public PtCatagoryGoods setCatagoryId(Long catagoryId) {
		this.catagoryId = catagoryId;
		return this;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public PtCatagoryGoods setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.catagoryId;
	}

	@Override
	public String toString() {
		return "PtCatagoryGoods{" +
			", catagoryId=" + catagoryId +
			", goodsId=" + goodsId +
			"}";
	}
}
