package com.yunsunyun.point.vo;

import java.util.List;

public class PtCartVo {
    private Long id;
    private String userName;
    private Long mallId;
    private Long memberId;
    private String imageAddress;
    private List<PtCartItemVo> items;

    public PtCartVo() {
    }

    public Long getMallId() {
        return mallId;
    }

    public void setMallId(Long mallId) {
        this.mallId = mallId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public List<PtCartItemVo> getItems() {
        return items;
    }

    public void setItems(List<PtCartItemVo> items) {
        this.items = items;
    }

}
