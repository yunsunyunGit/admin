package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtGoods;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunsunyun.point.vo.GoodsGalleryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtGoodsMapper extends BaseMapper<PtGoods> {

    /**
     * 添加一条商品数据并返回ID
     * @param goods
     * @return
     */
    Long insertGoodsReturnId(PtGoods goods);

    /**
     * 根据id查询商品信息以及该商品相册
     * @param id
     * @return
     */
    GoodsGalleryVo selectGoodsGalleryById(Long id);

    /**
     * 删除商家时，删除该商家的所有商品
     *
     * @param id
     */
    void deleteByMerchantId(@Param("id") Long id);
}