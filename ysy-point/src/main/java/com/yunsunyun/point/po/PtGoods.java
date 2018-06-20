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
@TableName("pt_goods")
public class PtGoods extends Model<PtGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 经销商的Id,对应kn_user表的Id
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
     * 审核状态0:新建;1:待审核;2:已审核;3:审核未通过
     */
	@TableField("audit_state")
	private Integer auditState;
    /**
     * 商品是否上架 0下架、1上架
     */
	@TableField("market_state")
	private Integer marketState;
    /**
     * 参照字典表中的首页分类id
     */
	@TableField("goods_type")
	private Long goodsType;
    /**
     * 是否有过修改并且未审核(0:未修改，1有修改且未审核)
     */
	@TableField("is_modified")
	private Integer isModified;
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
     * 创建人Id
     */
	@TableField("create_id")
	private Long createId;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Long createTime;
    /**
     * 更新人Id
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

	public PtGoods setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public PtGoods setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
		return this;
	}

	public String getName() {
		return name;
	}

	public PtGoods setName(String name) {
		this.name = name;
		return this;
	}

	public String getSn() {
		return sn;
	}

	public PtGoods setSn(String sn) {
		this.sn = sn;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public PtGoods setDescription(String description) {
		this.description = description;
		return this;
	}

	public Double getPoint() {
		return point;
	}

	public PtGoods setPoint(Double point) {
		this.point = point;
		return this;
	}

	public String getIntroduce() {
		return introduce;
	}

	public PtGoods setIntroduce(String introduce) {
		this.introduce = introduce;
		return this;
	}

	public Integer getStock() {
		return stock;
	}

	public PtGoods setStock(Integer stock) {
		this.stock = stock;
		return this;
	}

	public Integer getSeq() {
		return seq;
	}

	public PtGoods setSeq(Integer seq) {
		this.seq = seq;
		return this;
	}

	public Integer getGrade() {
		return grade;
	}

	public PtGoods setGrade(Integer grade) {
		this.grade = grade;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public PtGoods setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public PtGoods setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	public Double getReferencePrice() {
		return referencePrice;
	}

	public PtGoods setReferencePrice(Double referencePrice) {
		this.referencePrice = referencePrice;
		return this;
	}

	public String getAuditDisableReason() {
		return auditDisableReason;
	}

	public PtGoods setAuditDisableReason(String auditDisableReason) {
		this.auditDisableReason = auditDisableReason;
		return this;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public PtGoods setAuditState(Integer auditState) {
		this.auditState = auditState;
		return this;
	}

	public Integer getMarketState() {
		return marketState;
	}

	public PtGoods setMarketState(Integer marketState) {
		this.marketState = marketState;
		return this;
	}

	public Long getGoodsType() {
		return goodsType;
	}

	public PtGoods setGoodsType(Long goodsType) {
		this.goodsType = goodsType;
		return this;
	}

	public Integer getIsModified() {
		return isModified;
	}

	public PtGoods setIsModified(Integer isModified) {
		this.isModified = isModified;
		return this;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public PtGoods setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public PtGoods setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
		return this;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public PtGoods setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
		return this;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public PtGoods setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
		return this;
	}

	public Long getCreateId() {
		return createId;
	}

	public PtGoods setCreateId(Long createId) {
		this.createId = createId;
		return this;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public PtGoods setCreateTime(Long createTime) {
		this.createTime = createTime;
		return this;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public PtGoods setUpdateId(Long updateId) {
		this.updateId = updateId;
		return this;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public PtGoods setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtGoods{" +
			", id=" + id +
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
			", isModified=" + isModified +
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
