package com.yunsunyun.point.vo;

import java.util.List;

public class PtOrdersVo {
    private Long id;
    private Integer orderStatus;
    private Integer orderTotal;
    private Double orderGoodsPoint;
    private PtMerchantVo merchant;
    private List<PtOrderItemVo> items;

    public PtOrdersVo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal() {
        Integer orderTotal = 0;
        if (this.items != null && this.items.size() > 0) {
            for (PtOrderItemVo itemVo : items) {
                orderTotal += itemVo.getNum();
            }
            this.orderTotal = orderTotal;
        } else {
            this.orderTotal = 0;
        }

    }

    public Double getOrderGoodsPoint() {
        return orderGoodsPoint;
    }

    public void setOrderGoodsPoint(Double orderGoodsPoint) {
        this.orderGoodsPoint = orderGoodsPoint;
    }

    public PtMerchantVo getMerchant() {
        return merchant;
    }

    public void setMerchant(PtMerchantVo merchant) {
        this.merchant = merchant;
    }

    public List<PtOrderItemVo> getItems() {
        return items;
    }

    public void setItems(List<PtOrderItemVo> items) {
        this.items = items;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

}
