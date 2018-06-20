package com.yunsunyun.point.vo;

import com.yunsunyun.point.po.PtOrder;

public class PtOrderVo extends PtOrder {

    /**
     * 经销商名称
     */
    private String userName;

    /**
     * 收货人姓名
     */
    private String receiverName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }


}
