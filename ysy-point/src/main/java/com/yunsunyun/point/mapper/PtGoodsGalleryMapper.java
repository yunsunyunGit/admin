package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtGoodsGallery;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtGoodsGalleryMapper extends BaseMapper<PtGoodsGallery> {

    /**
     * 新增商品相册信息并返回ID
     * @param ptGoodsGallery
     * @return
     */
    Integer insertGalleryReturnId(PtGoodsGallery ptGoodsGallery);
}