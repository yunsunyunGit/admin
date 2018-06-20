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
@TableName("pt_goods_catagory")
public class PtGoodsCatagory extends Model<PtGoodsCatagory> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 分类名称
     */
	private String name;
    /**
     * 父类id
     */
	@TableField("parent_id")
	private Long parentId;
    /**
     * 分类id路径
     */
	private String path;
    /**
     * 分类排序
     */
	private Integer seq;
    /**
     * 是否在页面显示 0不显示，1显示
     */
	@TableField("is_show")
	private Integer isShow;
    /**
     * 分类图片
     */
	@TableField("image_url")
	private String imageUrl;
    /**
     * 是否被删除 0未删除，1已删除
     */
	@TableField("is_deleted")
	private Integer isDeleted;


	public Long getId() {
		return id;
	}

	public PtGoodsCatagory setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public PtGoodsCatagory setName(String name) {
		this.name = name;
		return this;
	}

	public Long getParentId() {
		return parentId;
	}

	public PtGoodsCatagory setParentId(Long parentId) {
		this.parentId = parentId;
		return this;
	}

	public String getPath() {
		return path;
	}

	public PtGoodsCatagory setPath(String path) {
		this.path = path;
		return this;
	}

	public Integer getSeq() {
		return seq;
	}

	public PtGoodsCatagory setSeq(Integer seq) {
		this.seq = seq;
		return this;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public PtGoodsCatagory setIsShow(Integer isShow) {
		this.isShow = isShow;
		return this;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public PtGoodsCatagory setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public PtGoodsCatagory setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtGoodsCatagory{" +
			", id=" + id +
			", name=" + name +
			", parentId=" + parentId +
			", path=" + path +
			", seq=" + seq +
			", isShow=" + isShow +
			", imageUrl=" + imageUrl +
			", isDeleted=" + isDeleted +
			"}";
	}
}
