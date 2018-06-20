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
 * 
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@TableName("pt_goods_gallery")
public class PtGoodsGallery extends Model<PtGoodsGallery> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品相册id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商品id(参照商品主键id)
     */
	@TableField("goods_id")
	private Long goodsId;
    /**
     * 图片路径
     */
	@TableField("image_url")
	private String imageUrl;
    /**
     * 排序
     */
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public PtGoodsGallery(Long goodsId, Integer type) {
		this.goodsId = goodsId;
		this.type = type;
	}

	public PtGoodsGallery() {
	}

	public PtGoodsGallery(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getId() {
		return id;
	}

	public PtGoodsGallery setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public PtGoodsGallery setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
		return this;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public PtGoodsGallery setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtGoodsGallery{" +
			", id=" + id +
			", goodsId=" + goodsId +
			", imageUrl=" + imageUrl +
			", seq=" + type +
			"}";
	}
}
