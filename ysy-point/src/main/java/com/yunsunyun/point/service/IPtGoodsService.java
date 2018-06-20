package com.yunsunyun.point.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtGoods;
import com.baomidou.mybatisplus.service.IService;
import com.yunsunyun.point.po.PtGoodsSnapshot;
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
public interface IPtGoodsService extends IService<PtGoods> {

    Page<PtGoods> getGoodsPage(Page page, Map<String, Object> searchParams, Long mallId, Integer findType, boolean point, boolean asc);
    List<PtGoods> getGoodsPage( Map<String, Object> searchParams, Long mallId, Integer findType);
    /**
     * 添加一条商品数据
     * @param goods
     * @param cataIds
     * @param goodsImage
     * @return
     */
    Boolean createGoods(PtGoods goods, Long[] cataIds, MultipartFile goodsImage, List<String> detailImage, List<String> carouselImage);

    /**
     * 修改一条商品数据
     * @param goods
     * @param cataIds
     * @param goodsImage
     * @param detailImage
     * @param carouselImage
     * @return
     */
    Integer updateGoods(PtGoods goods, Long[] cataIds, MultipartFile goodsImage, List<String> detailImage, List<String> carouselImage);


    PtGoodsSnapshot ptGoodsToPtGoodsSnapshot(PtGoods ptGoods);



}
