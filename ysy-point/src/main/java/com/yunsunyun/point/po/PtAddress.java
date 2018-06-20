package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 收货地址表
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@TableName("pt_address")
public class PtAddress extends Model<PtAddress> {

    private static final long serialVersionUID = 1L;

    /**
     * 地址Id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 会员id,从app端接口获取到的Id
     */
	@TableField("member_id")
	private Long memberId;
    /**
     * 收货人姓名
     */
	@TableField("receiver_name")
	private String receiverName;
    /**
     * 收货人电话
     */
	@TableField("receiver_phone")
	private String receiverPhone;
    /**
     * 省份名
     */
	private String province;
    /**
     * 市区名
     */
	private String city;
    /**
     * 区名
     */
	private String region;
    /**
     * 详细地址
     */
	@TableField("detail_address")
	private String detailAddress;
    /**
     * 是否是默认地址  0不是，1是
     */
	@TableField("is_default")
	private Integer isDefault;
    /**
     * 是否被删除 0未删除，1已删除
     */
	@TableField("is_deleted")
	private Integer isDeleted;


	public Long getId() {
		return id;
	}

	public PtAddress setId(Long id) {
		this.id = id;
		return this;
	}

	public Long getMemberId() {
		return memberId;
	}

	public PtAddress setMemberId(Long memberId) {
		this.memberId = memberId;
		return this;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public PtAddress setReceiverName(String receiverName) {
		this.receiverName = receiverName;
		return this;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public PtAddress setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
		return this;
	}

	public String getProvince() {
		return province;
	}

	public PtAddress setProvince(String province) {
		this.province = province;
		return this;
	}

	public String getCity() {
		return city;
	}

	public PtAddress setCity(String city) {
		this.city = city;
		return this;
	}

	public String getRegion() {
		return region;
	}

	public PtAddress setRegion(String region) {
		this.region = region;
		return this;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public PtAddress setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
		return this;
	}

	public Integer getIsDefult() {
		return isDefault;
	}

	public PtAddress setIsDefult(Integer isDefault) {
		this.isDefault = isDefault;
		return this;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public PtAddress setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PtAddress{" +
			", id=" + id +
			", memberId=" + memberId +
			", receiverName=" + receiverName +
			", receiverPhone=" + receiverPhone +
			", province=" + province +
			", city=" + city +
			", region=" + region +
			", detailAddress=" + detailAddress +
			", isDefult=" + isDefault +
			", isDeleted=" + isDeleted +
			"}";
	}
}
