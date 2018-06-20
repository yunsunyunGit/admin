package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@TableName("pt_goods_snapshot")
public class PtGoodsSnapshot extends Model<PtGoodsSnapshot> {

    private static final long serialVersionUID = 1L;

    /**
     * 快照id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商品id
     */
	@TableField("goods_id")
	private Long goodsId;

    /**
     * 经销商的Id
     */
	@TableField("merchant_id")
	private Long merchantId;
    /**
     * 商品名称
     */
	private String name;
    /**
     * 商品编号
     */
	private String sn;
    /**
     * 商品描述
     */
	private String description;
    /**
     * 积分价格
     */
	private Double point;
    /**
     * 商品详情
     */
	private String introduce;
    /**
     * 商品库存量
     */
	private Integer stock;
    /**
     * 商品排序
     */
	private Integer seq;
    /**
     * 商品评分
     */
	private Integer grade;
    /**
     * 商品评论
     */
	private String comment;
    /**
     * 商品缩略图
     */
	@TableField("image_url")
	private String imageUrl;
    /**
     * 商品参考价
     */
	@TableField("reference_price")
	private Double referencePrice;
    /**
     * 审核未通过原因
     */
	@TableField("audit_disable_reason")
	private String auditDisableReason;
    /**
     * 0:新建;1:待审核;2:已审核;3:审核未通过
     */
	@TableField("audit_state")
	private Integer auditState;
    /**
     * 商品是否上架 0下架、1上架
     */
	@TableField("market_state")
	private Integer marketState;
    /**
     * 是否配置在首页显示(0:不显示;1:显示)
     */
	@TableField("goods_type")
	private Long goodsType;
    /**
     * 是否被删除 0未删除，1已删除
     */
	@TableField("is_deleted")
	private Integer isDeleted;
    /**
     * 商品浏览次数
     */
	@TableField("view_count")
	private Integer viewCount;
    /**
     * 商品购买次数
     */
	@TableField("buy_count")
	private Integer buyCount;
    /**
     * 页面关键字
     */
	@TableField("meta_keywords")
	private String metaKeywords;
    /**
     * 创建人
     */
	@TableField("create_id")
	private Long createId;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Long createTime;
    /**
     * 修改人
     */
	@TableField("update_id")
	private Long updateId;
    /**
     * 修改时间
     */
	@TableField("update_time")
	private Long updateTime;


	public Long getId() {
		return id;
	}

	public PtGoodsSnapshot setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public PtGoodsSnapshot setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public PtGoodsSnapshot setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
		return this;
	}

	public String getName() {
		return name;
	}

	public PtGoodsSnapshot setName(String name) {
		this.name = name;
		return this;
	}

	public String getSn() {
		return sn;
	}

	public PtGoodsSnapshot setSn(String sn) {
		this.sn = sn;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public PtGoodsSnapshot setDescription(String description) {
		this.description = description;
		return this;
	}

	public Double getPoint() {
		return point;
	}

	public PtGoodsSnapshot setPoint(Double point) {
		this.point = point;
		return this;
	}

	public String getIntroduce() {
		return introduce;
	}

	public PtGoodsSnapshot setIntroduce(String introduce) {
		this.introduce = introduce;
		return this;
	}

	public Integer getStock() {
		return stock;
	}

	public PtGoodsSnapshot setStock(Integer stock) {
		this.stock = stock;
		return this;
	}

	public Integer getSeq() {
		return seq;
	}

	public PtGoodsSnapshot setSeq(Integer seq) {
		this.seq = seq;
		return this;
	}

	public Integer getGrade() {
		return grade;
	}

	public PtGoodsSnapshot setGrade(Integer grade) {
		this.grade = grade;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public PtGoodsSnapshot setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public PtGoodsSnapshot setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	public Double getReferencePrice() {
		return referencePrice;
	}

	public PtGoodsSnapshot setReferencePrice(Double referencePrice) {
		this.referencePrice = referencePrice;
		return this;
	}

	public String getAuditDisableReason() {
		return auditDisableReason;
	}

	public PtGoodsSnapshot setAuditDisableReason(String auditDisableReason) {
		this.auditDisableReason = auditDisableReason;
		return this;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public PtGoodsSnapshot setAuditState(Integer auditState) {
		this.auditState = auditState;
		return this;
	}

	public Integer getMarketState() {
		return marketState;
	}

	public PtGoodsSnapshot setMarketState(Integer marketState) {
		this.marketState = marketState;
		return this;
	}

	public Long getGoodsType() {
		return goodsType;
	}

	public PtGoodsSnapshot setGoodsType(Long goodsType) {
		this.goodsType = goodsType;
		return this;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public PtGoodsSnapshot setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public PtGoodsSnapshot setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
		return this;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public PtGoodsSnapshot setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
		return this;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public PtGoodsSnapshot setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
		return this;
	}

	public Long getCreateId() {
		return createId;
	}

	public PtGoodsSnapshot setCreateId(Long createId) {
		this.createId = createId;
		return this;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public PtGoodsSnapshot setCreateTime(Long createTime) {
		this.createTime = createTime;
		return this;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public PtGoodsSnapshot setUpdateId(Long updateId) {
		this.updateId = updateId;
		return this;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public PtGoodsSnapshot setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtGoodsSnapshot{" +
			", id=" + id +
			", goodsId=" + goodsId +
			", merchantId=" + merchantId +
			", name=" + name +
			", sn=" + sn +
			", description=" + description +
			", point=" + point +
			", introduce=" + introduce +
			", stock=" + stock +
			", seq=" + seq +
			", grade=" + grade +
			", comment=" + comment +
			", imageUrl=" + imageUrl +
			", referencePrice=" + referencePrice +
			", auditDisableReason=" + auditDisableReason +
			", auditState=" + auditState +
			", marketState=" + marketState +
			", goodsType=" + goodsType +
			", isDeleted=" + isDeleted +
			", viewCount=" + viewCount +
			", buyCount=" + buyCount +
			", metaKeywords=" + metaKeywords +
			", createId=" + createId +
			", createTime=" + createTime +
			", updateId=" + updateId +
			", updateTime=" + updateTime +
			"}";
	}





}
