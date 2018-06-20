package com.yunsunyun.point.vo;

import java.io.Serializable;

import com.yunsunyun.point.po.PtGoodsCatagory;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2018-03-30
 */
public class PtGoodsCatagoryVo extends PtGoodsCatagory{

    /**
     * 商城名
     */
    private Integer mallNum;
    private String mallNameList;

    public String getMallNameList() {
        return mallNameList;
    }

    public void setMallNameList(String mallNameList) {
        this.mallNameList = mallNameList;
    }

    public Integer getMallNum() {
        return mallNum;
    }

    public void setMallNum(Integer mallNum) {
        this.mallNum = mallNum;
    }
}
