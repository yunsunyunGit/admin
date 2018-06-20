package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@TableName("pt_mall")
public class PtMall extends Model<PtMall> {

    private static final long serialVersionUID = 1L;

    /**
     * 商城ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商城名称
     */
	private String name;
    /**
     * 所属APP名称
     */
	@TableField("app_name")
	private String appName;
    /**
     * 商城描述
     */
	private String description;
    /**
     * 是否被删除 0未删除，1已删除
     */
	@TableField("is_deleted")
	private Integer isDeleted;
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

	public PtMall setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public PtMall setName(String name) {
		this.name = name;
		return this;
	}

	public String getAppName() {
		return appName;
	}

	public PtMall setAppName(String appName) {
		this.appName = appName;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public PtMall setDescription(String description) {
		this.description = description;
		return this;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public PtMall setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	public Long getCreateId() {
		return createId;
	}

	public PtMall setCreateId(Long createId) {
		this.createId = createId;
		return this;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public PtMall setCreateTime(Long createTime) {
		this.createTime = createTime;
		return this;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public PtMall setUpdateId(Long updateId) {
		this.updateId = updateId;
		return this;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public PtMall setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtMall{" +
			", id=" + id +
			", name=" + name +
			", appName=" + appName +
			", description=" + description +
			", isDeleted=" + isDeleted +
			", createId=" + createId +
			", createTime=" + createTime +
			", updateId=" + updateId +
			", updateTime=" + updateTime +
			"}";
	}
}
