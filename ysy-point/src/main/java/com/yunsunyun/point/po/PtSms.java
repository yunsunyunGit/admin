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
@TableName("pt_sms")
public class PtSms extends Model<PtSms> {

    private static final long serialVersionUID = 1L;

    /**
     * 短信主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商城id
     */
	@TableField("mall_id")
	private Long mallId;
    /**
     * 短信内容
     */
	private String content;
    /**
     * 发送者姓名
     */
	@TableField("sender_name")
	private String senderName;
    /**
     * 发送者手机
     */
	@TableField("sender_phone")
	private String senderPhone;
    /**
     * 接收者姓名
     */
	@TableField("receiver_name")
	private String receiverName;
    /**
     * 接收者手机
     */
	@TableField("receiver_phone")
	private String receiverPhone;
    /**
     * 发送时间
     */
	@TableField("send_time")
	private Long sendTime;
    /**
     * 接收时间
     */
	@TableField("receive_time")
	private Long receiveTime;
    /**
     * 发送状态（0:未发送;1:已发送）
     */
	@TableField("sms_status")
	private Integer smsStatus;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Long createTime;


	public Long getId() {
		return id;
	}

	public PtSms setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getMallId() {
		return mallId;
	}

	public PtSms setMallId(Long mallId) {
		this.mallId = mallId;
		return this;
	}

	public String getContent() {
		return content;
	}

	public PtSms setContent(String content) {
		this.content = content;
		return this;
	}

	public String getSenderName() {
		return senderName;
	}

	public PtSms setSenderName(String senderName) {
		this.senderName = senderName;
		return this;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public PtSms setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
		return this;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public PtSms setReceiverName(String receiverName) {
		this.receiverName = receiverName;
		return this;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public PtSms setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
		return this;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public PtSms setSendTime(Long sendTime) {
		this.sendTime = sendTime;
		return this;
	}

	public Long getReceiveTime() {
		return receiveTime;
	}

	public PtSms setReceiveTime(Long receiveTime) {
		this.receiveTime = receiveTime;
		return this;
	}

	public Integer getSmsStatus() {
		return smsStatus;
	}

	public PtSms setSmsStatus(Integer smsStatus) {
		this.smsStatus = smsStatus;
		return this;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public PtSms setCreateTime(Long createTime) {
		this.createTime = createTime;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtSms{" +
			", id=" + id +
			", mallId=" + mallId +
			", content=" + content +
			", senderName=" + senderName +
			", senderPhone=" + senderPhone +
			", receiverName=" + receiverName +
			", receiverPhone=" + receiverPhone +
			", sendTime=" + sendTime +
			", receiveTime=" + receiveTime +
			", smsStatus=" + smsStatus +
			", createTime=" + createTime +
			"}";
	}
}
