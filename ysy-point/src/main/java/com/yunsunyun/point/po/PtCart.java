package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@TableName("pt_cart")
public class PtCart extends Model<PtCart> {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商品Id,对应kn_goods的goods_id
     */
	@TableField("goods_id")
	private Long goodsId;
    /**
     * 商品数量
     */
	@TableField("goods_num")
	private Integer goodsNum;
    /**
     * 商家（经销商）Id,kn_user表的Id，冗余字段
     */
	@TableField("merchant_id")
	private Long merchantId;
    /**
     * 会员id,从app端接口获取到的Id
     */
	@TableField("member_id")
	private Long memberId;
    /**
     * 商城id
     */
	@TableField("mall_id")
	private Long mallId;


	public Long getId() {
		return id;
	}

	public PtCart setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public PtCart setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public PtCart setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
		return this;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public PtCart setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
		return this;
	}

	public Long getMemberId() {
		return memberId;
	}

	public PtCart setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}

	public Long getMallId() {
		return mallId;
	}

	public PtCart setMallId(Long mallId) {
		this.mallId = mallId;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtCart{" +
			", id=" + id +
			", goodsId=" + goodsId +
			", goodsNum=" + goodsNum +
			", merchantId=" + merchantId +
			", memberId=" + memberId +
			", mallId=" + mallId +
			"}";
	}
}
