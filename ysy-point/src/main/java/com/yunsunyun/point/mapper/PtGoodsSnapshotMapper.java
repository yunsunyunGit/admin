package com.yunsunyun.point.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtGoodsSnapshot;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtGoodsSnapshotMapper extends BaseMapper<PtGoodsSnapshot> {

    Long insertGoodsSnapshotReturnId(PtGoodsSnapshot ptGoodsSnapshot);

    List<PtGoodsSnapshot> selectGoodsSnapshotPage(Page<PtGoodsSnapshot> page, Map<String, Object> searchParams);
    List<PtGoodsSnapshot> selectGoodsSnapshotPage(Map<String, Object> searchParams);
}