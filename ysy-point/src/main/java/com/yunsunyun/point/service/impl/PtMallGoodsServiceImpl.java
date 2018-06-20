package com.yunsunyun.point.service.impl;

import com.yunsunyun.point.po.PtMallGoods;
import com.yunsunyun.point.mapper.PtMallGoodsMapper;
import com.yunsunyun.point.service.IPtMallGoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商城-商品 关联表 服务实现类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@Service
public class PtMallGoodsServiceImpl extends ServiceImpl<PtMallGoodsMapper, PtMallGoods> implements IPtMallGoodsService {
    @Autowired
    private PtMallGoodsMapper ptMallGoodsMapper;

    @Override
    public List<Long> selectMallIdByGoodsId(Long goodsId) {
        return  ptMallGoodsMapper.selectMallIdByGoodsId(goodsId);
    }



}
