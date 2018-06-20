package com.yunsunyun.point.vo;

import com.yunsunyun.point.po.PtMall;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public class PtMallVo extends PtMall {

	private Integer merchantNum;

	public Integer getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(Integer merchantNum) {
		this.merchantNum = merchantNum;
	}
}
