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
 * @since 2018-04-09
 */
@TableName("pt_banner")
public class PtBanner extends Model<PtBanner> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 跳转地址
     */
	private String url;
    /**
     * banner名
     */
	private String name;
    /**
     * banner图片存储地址
     */
	@TableField("image_url")
	private String imageUrl;
    /**
     * banner类型(参照字典表中内容类型)
     */
	private Long type;
    /**
     * banner内容
     */
	private String content;
    /**
     * 是否启用(0:不启用;1启用)
     */
	private Integer state;
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

	@TableField("sort")
	private Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public PtBanner setId(Long id) {
		this.id = id;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public PtBanner setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getName() {
		return name;
	}

	public PtBanner setName(String name) {
		this.name = name;
		return this;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public PtBanner setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	public Long getType() {
		return type;
	}

	public PtBanner setType(Long type) {
		this.type = type;
		return this;
	}

	public String getContent() {
		return content;
	}

	public PtBanner setContent(String content) {
		this.content = content;
		return this;
	}

	public Integer getState() {
		return state;
	}

	public PtBanner setState(Integer state) {
		this.state = state;
		return this;
	}

	public Long getCreateId() {
		return createId;
	}

	public PtBanner setCreateId(Long createId) {
		this.createId = createId;
		return this;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public PtBanner setCreateTime(Long createTime) {
		this.createTime = createTime;
		return this;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public PtBanner setUpdateId(Long updateId) {
		this.updateId = updateId;
		return this;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public PtBanner setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtBanner{" +
			", id=" + id +
			", url=" + url +
			", name=" + name +
			", imageUrl=" + imageUrl +
			", type=" + type +
			", content=" + content +
			", state=" + state +
			", createId=" + createId +
			", createTime=" + createTime +
			", updateId=" + updateId +
			", updateTime=" + updateTime +
			"}";
	}
}
