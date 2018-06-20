package com.yunsunyun.point.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtGoods;
import com.yunsunyun.point.po.PtGoodsSnapshot;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 * @since 2018-03-30
 */
public interface IPtGoodsSnapshotService extends IService<PtGoodsSnapshot> {

    Page<PtGoodsSnapshot> getGoodsSnapshotPage(Page<PtGoodsSnapshot> page, Map<String, Object> searchParams, String asc);

    List<PtGoodsSnapshot> getGoodsSnapshotPage(Map<String, Object> searchParams);

    PtGoods ptGoodsSnapshotToPtGoods(PtGoodsSnapshot ptGoodsSnapshot);
}
