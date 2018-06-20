package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商城-经销商 关联表
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@TableName("pt_mall_merchant")
public class PtMallMerchant extends Model<PtMallMerchant> {

    private static final long serialVersionUID = 1L;

    /**
     * 经销商Id
     */
    @TableId("merchant_id")
	private Long merchantId;
    /**
     * 商城Id
     */
	@TableField("mall_id")
	private Long mallId;


	public Long getMerchantId() {
		return merchantId;
	}

	public PtMallMerchant setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
		return this;
	}

	public Long getMallId() {
		return mallId;
	}

	public PtMallMerchant setMallId(Long mallId) {
		this.mallId = mallId;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.merchantId;
	}

	@Override
	public String toString() {
		return "PtMallMerchant{" +
			", merchantId=" + merchantId +
			", mallId=" + mallId +
			"}";
	}
}
