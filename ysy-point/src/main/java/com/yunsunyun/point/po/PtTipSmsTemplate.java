package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-04-08
 */
@TableName("pt_tip_sms_template")
public class PtTipSmsTemplate extends Model<PtTipSmsTemplate> {

    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 模板名
     */
	private String name;
    /**
     * 模板内容
     */
	private String content;
    /**
     * 是否为默认模板，0否，1是
     */
	@TableField("is_default")
	private Integer isDefault;
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
    /**
     * 发送动作类型：1下单时，2发货时，3收货时，4取消时
     */
	@TableField("action_type")
	private Integer actionType;


	public Long getId() {
		return id;
	}

	public PtTipSmsTemplate setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public PtTipSmsTemplate setName(String name) {
		this.name = name;
		return this;
	}

	public String getContent() {
		return content;
	}

	public PtTipSmsTemplate setContent(String content) {
		this.content = content;
		return this;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public PtTipSmsTemplate setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
		return this;
	}

	public Long getCreateId() {
		return createId;
	}

	public PtTipSmsTemplate setCreateId(Long createId) {
		this.createId = createId;
		return this;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public PtTipSmsTemplate setCreateTime(Long createTime) {
		this.createTime = createTime;
		return this;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public PtTipSmsTemplate setUpdateId(Long updateId) {
		this.updateId = updateId;
		return this;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public PtTipSmsTemplate setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	public Integer getActionType() {
		return actionType;
	}

	public PtTipSmsTemplate setActionType(Integer actionType) {
		this.actionType = actionType;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtTipSmsTemplate{" +
			", id=" + id +
			", name=" + name +
			", content=" + content +
			", isDefault=" + isDefault +
			", createId=" + createId +
			", createTime=" + createTime +
			", updateId=" + updateId +
			", updateTime=" + updateTime +
			", actionType=" + actionType +
			"}";
	}
}
