package com.yunsunyun.point.service;

import com.yunsunyun.point.po.PtGoodsSnapshotGallery;
import com.baomidou.mybatisplus.service.IService;
import com.yunsunyun.point.vo.PtGoodsGalleryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface IPtGoodsSnapshotGalleryService extends IService<PtGoodsSnapshotGallery> {

    List<PtGoodsGalleryVo> goodsGalleryToGoodsGalleryVo(List<PtGoodsSnapshotGallery> goodsGalleryList);
}
