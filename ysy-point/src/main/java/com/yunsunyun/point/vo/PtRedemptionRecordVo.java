package com.yunsunyun.point.vo;

import com.yunsunyun.point.po.PtRedemptionRecord;

public class PtRedemptionRecordVo extends PtRedemptionRecord  {
    private String mallName;
    private String merchantName;
    private String orderNum;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

}
