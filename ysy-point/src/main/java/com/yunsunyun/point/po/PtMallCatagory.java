package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-04-11
 */
@TableName("pt_mall_catagory")
public class PtMallCatagory extends Model<PtMallCatagory> {

    private static final long serialVersionUID = 1L;

	public PtMallCatagory() {
	}

	public PtMallCatagory(Long mallId, Long catagoryId) {
		this.mallId = mallId;
		this.catagoryId = catagoryId;
	}

	/**
     * 参考商城表id
     */
    @TableId("mall_id")
	private Long mallId;
    /**
     * 参考分类表id
     */
	@TableField("catagory_id")
	private Long catagoryId;


	public Long getMallId() {
		return mallId;
	}

	public PtMallCatagory setMallId(Long mallId) {
		this.mallId = mallId;
		return this;
	}

	public Long getCatagoryId() {
		return catagoryId;
	}

	public PtMallCatagory setCatagoryId(Long catagoryId) {
		this.catagoryId = catagoryId;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.mallId;
	}

	@Override
	public String toString() {
		return "PtMallCatagory{" +
			", mallId=" + mallId +
			", catagoryId=" + catagoryId +
			"}";
	}
}
