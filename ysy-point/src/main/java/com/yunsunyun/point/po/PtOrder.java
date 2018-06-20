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
 * <p>
 * </p>
 *
 * @author
 * @since 2018-03-30
 */
@TableName("pt_order")
public class PtOrder extends Model<PtOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商城Id,关联pt_mall.id
     */
    @TableField("mall_id")
    private Long mallId;
    /**
     * 会员id,从app端接口获取到的Id
     */
    @TableField("member_id")
    private Long memberId;
    /**
     * 收货地址Id
     */
    @TableField("address_id")
    private Long addressId;
    /**
     * 商家（经销商）id
     */
    @TableField("merchant_id")
    private Long merchantId;
    /**
     * 物流公司id
     */
    @TableField("logi_id")
    private Long logiId;
    /**
     * 物流单号
     */
    @TableField("logi_num")
    private String logiNum;
    /**
     * 订单编号
     */
    @TableField("order_num")
    private String orderNum;
    /**
     * 订单状态(0:待发货;1:已发货;2:已完成;3:已取消)
     */
    @TableField("order_status")
    private Integer orderStatus;
    /**
     * 订单创建时间
     */
    @TableField("create_time")
    private Long createTime;
    /**
     * 创建人Id
     */
    @TableField("create_id")
    private Long createId;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Long updateTime;
    /**
     * 更新者Id
     */
    @TableField("update_id")
    private Long updateId;
    /**
     * 发货日期
     */
    @TableField("ship_time")
    private Long shipTime;
    /**
     * 商品订单商品总积分
     */
    @TableField("order_goods_point")
    private Double orderGoodsPoint;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 是否被删除(0:否，1:是)
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     * 完成时间
     */
    @TableField("complete_time")
    private Long completeTime;
    /**
     * 取消原因
     */
    @TableField("cancel_reason")
    private String cancelReason;
    /**
     * 取消时间
     */
    @TableField("cancel_time")
    private Long cancelTime;
    /**
     * 商品快照id
     */
    @TableField("snapshot_id")
    private Long snapshotId;

    /**
     * 是否读取该条消息 0:未读 1:已读(当订单状态改变时 须要设置成0:未读)
     */
    @TableField("is_new")
    private Integer isNew;

    /**
     * 获转让积分
     */
    @TableField("transfer_point")
    private Double transferPoint;

    /**
     * 返利积分
     */
    @TableField("rebate_point")
    private Double rebatePoint;

    /**
     * 普通积分
     */
    @TableField("ordinary_point")
    private Double ordinaryPoint;

    /**
     * vip积分
     */
    @TableField("vip_point")
    private Double vipPoint;

    public Double getTransferPoint() {
        return transferPoint;
    }

    public void setTransferPoint(Double transferPoint) {
        this.transferPoint = transferPoint;
    }

    public Double getRebatePoint() {
        return rebatePoint;
    }

    public void setRebatePoint(Double rebatePoint) {
        this.rebatePoint = rebatePoint;
    }

    public Double getOrdinaryPoint() {
        return ordinaryPoint;
    }

    public void setOrdinaryPoint(Double ordinaryPoint) {
        this.ordinaryPoint = ordinaryPoint;
    }

    public Double getVipPoint() {
        return vipPoint;
    }

    public void setVipPoint(Double vipPoint) {
        this.vipPoint = vipPoint;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Long getId() {
        return id;
    }

    public PtOrder setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getMallId() {
        return mallId;
    }

    public PtOrder setMallId(Long mallId) {
        this.mallId = mallId;
        return this;
    }

    public Long getMemberId() {
        return memberId;
    }

    public PtOrder setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public Long getAddressId() {
        return addressId;
    }

    public PtOrder setAddressId(Long addressId) {
        this.addressId = addressId;
        return this;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public PtOrder setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public Long getLogiId() {
        return logiId;
    }

    public PtOrder setLogiId(Long logiId) {
        this.logiId = logiId;
        return this;
    }

    public String getLogiNum() {
        return logiNum;
    }

    public PtOrder setLogiNum(String logiNum) {
        this.logiNum = logiNum;
        return this;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public PtOrder setOrderNum(String orderNum) {
        this.orderNum = orderNum;
        return this;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public PtOrder setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public PtOrder setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getCreateId() {
        return createId;
    }

    public PtOrder setCreateId(Long createId) {
        this.createId = createId;
        return this;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public PtOrder setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public PtOrder setUpdateId(Long updateId) {
        this.updateId = updateId;
        return this;
    }

    public Long getShipTime() {
        return shipTime;
    }

    public PtOrder setShipTime(Long shipTime) {
        this.shipTime = shipTime;
        return this;
    }

    public Double getOrderGoodsPoint() {
        return orderGoodsPoint;
    }

    public PtOrder setOrderGoodsPoint(Double orderGoodsPoint) {
        this.orderGoodsPoint = orderGoodsPoint;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PtOrder setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public PtOrder setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public Long getCompleteTime() {
        return completeTime;
    }

    public PtOrder setCompleteTime(Long completeTime) {
        this.completeTime = completeTime;
        return this;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public PtOrder setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
        return this;
    }

    public Long getCancelTime() {
        return cancelTime;
    }

    public PtOrder setCancelTime(Long cancelTime) {
        this.cancelTime = cancelTime;
        return this;
    }

    public Long getSnapshotId() {
        return snapshotId;
    }

    public PtOrder setSnapshotId(Long snapshotId) {
        this.snapshotId = snapshotId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PtOrder{" +
                "id=" + id +
                ", mallId=" + mallId +
                ", memberId=" + memberId +
                ", addressId=" + addressId +
                ", merchantId=" + merchantId +
                ", logiId=" + logiId +
                ", logiNum='" + logiNum + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", orderStatus=" + orderStatus +
                ", createTime=" + createTime +
                ", createId=" + createId +
                ", updateTime=" + updateTime +
                ", updateId=" + updateId +
                ", shipTime=" + shipTime +
                ", orderGoodsPoint=" + orderGoodsPoint +
                ", remark='" + remark + '\'' +
                ", isDeleted=" + isDeleted +
                ", completeTime=" + completeTime +
                ", cancelReason='" + cancelReason + '\'' +
                ", cancelTime=" + cancelTime +
                ", snapshotId=" + snapshotId +
                ", isNew=" + isNew +
                ", transferPoint=" + transferPoint +
                ", rebatePoint=" + rebatePoint +
                ", ordinaryPoint=" + ordinaryPoint +
                ", vipPoint=" + vipPoint +
                '}';
    }
}
