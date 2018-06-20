package com.yunsunyun.point.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-04-19
 */
@TableName("pt_catagory_snapshot_goods")
public class PtCatagorySnapshotGoods extends Model<PtCatagorySnapshotGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * 参照分类表id
     */
    @TableId("catagory_id")
	private Long catagoryId;
    /**
     * 快照表id
     */
	@TableField("snapshot_id")
	private Long snapshotId;


	public Long getCatagoryId() {
		return catagoryId;
	}

	public PtCatagorySnapshotGoods setCatagoryId(Long catagoryId) {
		this.catagoryId = catagoryId;
		return this;
	}

	public Long getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(Long snapshotId) {
		this.snapshotId = snapshotId;
	}

	@Override
	protected Serializable pkVal() {
		return this.catagoryId;
	}

	@Override
	public String toString() {
		return "PtCatagorySnapshotGoods{" +
			", catagoryId=" + catagoryId +
			", snapshotId=" + snapshotId +
			"}";
	}
}
