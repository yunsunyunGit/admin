package com.yunsunyun.point.service;

import com.yunsunyun.point.po.PtGoodsGallery;
import com.baomidou.mybatisplus.service.IService;
import com.yunsunyun.point.po.PtGoodsSnapshotGallery;
import com.yunsunyun.point.vo.PtGoodsGalleryVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface IPtGoodsGalleryService extends IService<PtGoodsGallery> {

    /**
     * 新增商品详情图
     * @param goodsId
     * @param file
     * @return
     */
    Map<String,Object> saveDetailGallery(Long goodsId, MultipartFile file);

    /**
     * 新增商品轮播图
     * @param goodsId
     * @param file
     * @return
     */
    Map<String,Object> saveCarouselGallery(Long goodsId, MultipartFile file);


     Map<String,Object> saveGalleryImage(MultipartFile file);


    Map<String,Object> deleteGalleryImage(String filePath);

    List<PtGoodsGalleryVo> goodsGalleryToGoodsGalleryVo(List<PtGoodsGallery> goodsGalleryList);

    List<PtGoodsGalleryVo> goodsGallerySnapshotToGoodsGalleryVo(List<PtGoodsSnapshotGallery> goodsGalleryList);
}

