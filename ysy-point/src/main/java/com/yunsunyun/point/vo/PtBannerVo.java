package com.yunsunyun.point.vo;

import java.io.Serializable;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2018-04-08
 */
public class PtBannerVo {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 跳转地址
     */
    private String url;
    /**
     * banner名
     */
    private String name;
    /**
     * banner类型(参照字典表中内容类型)
     */
    private String typeValue;
    /**
     * 是否启用(0:不启用;1启用)
     */
    private Integer state;
    /**
     * banner内容
     */
    private String content;
    /**
     * 创建时间
     */
    private String createTime;

    public PtBannerVo() {
    }

    public PtBannerVo(Long id, String url, String name, String typeValue,Integer state, String content, String createTime) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.typeValue = typeValue;
        this.state = state;
        this.content = content;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public PtBannerVo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PtBannerVo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public PtBannerVo setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return typeValue;
    }

    public PtBannerVo setType(String type) {
        this.typeValue = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PtBannerVo setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public PtBannerVo setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PtBanner{" +
                ", id=" + id +
                ", url=" + url +
                ", name=" + name +
                ", typeValue=" + typeValue +
                ", content=" + content +
                ", createTime=" + createTime +
                "}";
    }
}
