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
@TableName("pt_tip")
public class PtTip extends Model<PtTip> {

    private static final long serialVersionUID = 1L;

    /**
     * 提醒消息主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商城Id
     */
	@TableField("mall_id")
	private Long mallId;
	/**
	 * 订单Id
	 */
	@TableField("order_id")
	private Long orderId;
    /**
     * 消息内容
     */
	private String content;
    /**
     * 发送者id
     */
	@TableField("sender_id")
	private Long senderId;
    /**
     * 发送者昵称
     */
	@TableField("sender_name")
	private String senderName;
    /**
     * 接收者id
     */
	@TableField("receiver_id")
	private Long receiverId;
    /**
     * 接收者昵称
     */
	@TableField("receiver_name")
	private String receiverName;
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
     * 消息读取状态 0未读，1已读
     */
	@TableField("read_status")
	private Integer readStatus;

	/**
	 * 是否读取该条消息 0:未读 1:已读
	 */
	@TableField("is_new")
	private Integer isNew;

	/**
	 * 商品item的第一张图片
	 */
	@TableField("item_image")
	private String itemImage;

	/**
	 * 订单号
	 */
	@TableField("order_num")
	private String orderNum;

	/**
	 * 物流单号
	 */
	@TableField("logi_num")
	private String logiNum;

	/**
	 * 是否已删除（0:未删除;1:已删除;
	 */
	@TableField("is_deleted")
	private Integer isDeleted;

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getLogiNum() {
		return logiNum;
	}

	public void setLogiNum(String logiNum) {
		this.logiNum = logiNum;
	}

	public Long getId() {
		return id;
	}

	public PtTip setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getMallId() {
		return mallId;
	}

	public PtTip setMallId(Long mallId) {
		this.mallId = mallId;
		return this;
	}

	public String getContent() {
		return content;
	}

	public PtTip setContent(String content) {
		this.content = content;
		return this;
	}

	public Long getSenderId() {
		return senderId;
	}

	public PtTip setSenderId(Long senderId) {
		this.senderId = senderId;
		return this;
	}

	public String getSenderName() {
		return senderName;
	}

	public PtTip setSenderName(String senderName) {
		this.senderName = senderName;
		return this;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public PtTip setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
		return this;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public PtTip setReceiverName(String receiverName) {
		this.receiverName = receiverName;
		return this;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public PtTip setSendTime(Long sendTime) {
		this.sendTime = sendTime;
		return this;
	}

	public Long getReceiveTime() {
		return receiveTime;
	}

	public PtTip setReceiveTime(Long receiveTime) {
		this.receiveTime = receiveTime;
		return this;
	}

	public Integer getReadStatus() {
		return readStatus;
	}

	public PtTip setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtTip{" +
			", id=" + id +
			", mallId=" + mallId +
			", content=" + content +
			", senderId=" + senderId +
			", senderName=" + senderName +
			", receiverId=" + receiverId +
			", receiverName=" + receiverName +
			", sendTime=" + sendTime +
			", receiveTime=" + receiveTime +
			", readStatus=" + readStatus +
			"}";
	}
}
