package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-04-12
 */
@TableName("pt_redemption_record")
public class PtRedemptionRecord extends Model<PtRedemptionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 积分兑换记录Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商城Id
     */
	@TableField("mall_id")
	private Long mallId;
    /**
     * 会员Id
     */
	@TableField("member_id")
	private Long memberId;
    /**
     * 会员名
     */
	@TableField("member_name")
	private String memberName;
    /**
     * 会员手机号码
     */
	@TableField("member_phone")
	private String memberPhone;
    /**
     * 商家（经销商）Id
     */
	@TableField("merchant_id")
	private Long merchantId;
    /**
     * 商品名
     */
	@TableField("goods_name")
	private String goodsName;
    /**
     * 商品数量
     */
	@TableField("goods_num")
	private Integer goodsNum;
    /**
     * 商品单价积分
     */
	@TableField("goods_point")
	private Double goodsPoint;
    /**
     * 兑换时间
     */
	@TableField("create_time")
	private Long createTime;

	@TableField("order_id")
	private Long orderId;

	@TableField("image_url")
	private String imageUrl;

	@TableField("status")
	private Integer status;


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return id;
	}

	public PtRedemptionRecord setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getMallId() {
		return mallId;
	}

	public PtRedemptionRecord setMallId(Long mallId) {
		this.mallId = mallId;
		return this;
	}

	public Long getMemberId() {
		return memberId;
	}



	public PtRedemptionRecord setMemberId(Long memberId) {

		this.memberId = memberId;
		return this;
	}

	public String getMemberName() {
		return memberName;
	}

	public PtRedemptionRecord setMemberName(String memberName) {
		this.memberName = memberName;
		return this;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public PtRedemptionRecord setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
		return this;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public PtRedemptionRecord setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
		return this;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public PtRedemptionRecord setGoodsName(String goodsName) {
		this.goodsName = goodsName;
		return this;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public PtRedemptionRecord setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
		return this;
	}

	public Double getGoodsPoint() {
		return goodsPoint;
	}

	public PtRedemptionRecord setGoodsPoint(Double goodsPoint) {
		this.goodsPoint = goodsPoint;
		return this;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public PtRedemptionRecord setCreateTime(Long createTime) {
		this.createTime = createTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtRedemptionRecord{" +
			", id=" + id +
			", mallId=" + mallId +
			", memberId=" + memberId +
			", memberName=" + memberName +
			", memberPhone=" + memberPhone +
			", merchantId=" + merchantId +
			", goodsName=" + goodsName +
			", goodsNum=" + goodsNum +
			", goodsPoint=" + goodsPoint +
			", createTime=" + createTime +
			"}";
	}
}
