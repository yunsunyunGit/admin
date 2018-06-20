package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-04-12
 */
@TableName("pt_order_items")
public class PtOrderItems extends Model<PtOrderItems> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单itemId
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单Id
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 商品Id
     */
	@TableField("goods_id")
	private Long goodsId;
    /**
     * 当前订单的item的数量
     */
	private Integer num;
    /**
     * item的缩略图片
     */
	@TableField("image_url")
	private String imageUrl;
    /**
     * item的名称
     */
	private String name;
    /**
     * 订单的item购买积分
     */
	private Double point;
    /**
     * item的参考价格
     */
	@TableField("reference_price")
	private Double referencePrice;
    /**
     * 商品编号
     */
	private String sn;


	public Long getId() {
		return id;
	}

	public PtOrderItems setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getOrderId() {
		return orderId;
	}

	public PtOrderItems setOrderId(Long orderId) {
		this.orderId = orderId;
		return this;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public PtOrderItems setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	public Integer getNum() {
		return num;
	}

	public PtOrderItems setNum(Integer num) {
		this.num = num;
		return this;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public PtOrderItems setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	public String getName() {
		return name;
	}

	public PtOrderItems setName(String name) {
		this.name = name;
		return this;
	}

	public Double getPoint() {
		return point;
	}

	public PtOrderItems setPoint(Double point) {
		this.point = point;
		return this;
	}

	public Double getReferencePrice() {
		return referencePrice;
	}

	public PtOrderItems setReferencePrice(Double referencePrice) {
		this.referencePrice = referencePrice;
		return this;
	}

	public String getSn() {
		return sn;
	}

	public PtOrderItems setSn(String sn) {
		this.sn = sn;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtOrderItems{" +
			", id=" + id +
			", orderId=" + orderId +
			", goodsId=" + goodsId +
			", num=" + num +
			", imageUrl=" + imageUrl +
			", name=" + name +
			", point=" + point +
			", referencePrice=" + referencePrice +
			", sn=" + sn +
			"}";
	}
}
