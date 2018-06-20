package com.yunsunyun.point.vo;

public class PointRecordVo {

    private Long orderId;
    private String content;
    private String imageUrl;
    private Long createTime;
    private Double goodsPoint;
    private Integer status;

    public PointRecordVo() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getGoodsPoint() {
        return goodsPoint;
    }

    public void setGoodsPoint(Double goodsPoint) {
        this.goodsPoint = goodsPoint;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
