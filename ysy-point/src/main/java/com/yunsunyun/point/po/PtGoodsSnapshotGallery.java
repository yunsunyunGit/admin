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
@TableName("pt_goods_snapshot_gallery")
public class PtGoodsSnapshotGallery extends Model<PtGoodsSnapshotGallery> {

    private static final long serialVersionUID = 1L;

    /**
     * 照片编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商品快照id
     */
	@TableField("snapshot_id")
	private Long snapshotId;
    /**
     * 商品快照原始图
     */
	@TableField("image_url")
	private String imageUrl;
    /**
     * 排序
     */
	private Integer type;


	public Long getId() {
		return id;
	}

	public PtGoodsSnapshotGallery setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getSnapshotId() {
		return snapshotId;
	}

	public PtGoodsSnapshotGallery setSnapshotId(Long snapshotId) {
		this.snapshotId = snapshotId;
		return this;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public PtGoodsSnapshotGallery setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	public Integer getType() {
		return type;
	}

	public PtGoodsSnapshotGallery setType(Integer type) {
		this.type = type;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtGoodsSnapshotGallery{" +
			", id=" + id +
			", snapshotId=" + snapshotId +
			", imageUrl=" + imageUrl +
			", type=" + type +
			"}";
	}
}
