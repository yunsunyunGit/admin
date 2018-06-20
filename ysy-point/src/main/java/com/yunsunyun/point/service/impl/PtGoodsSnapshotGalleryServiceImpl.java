package com.yunsunyun.point.service.impl;

import com.yunsunyun.config.util.ConfigUtil;
import com.yunsunyun.point.po.PtGoodsSnapshotGallery;
import com.yunsunyun.point.mapper.PtGoodsSnapshotGalleryMapper;
import com.yunsunyun.point.service.IPtGoodsSnapshotGalleryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.vo.PtGoodsGalleryVo;
import com.yunsunyun.xsimple.Setting;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@Service
public class PtGoodsSnapshotGalleryServiceImpl extends ServiceImpl<PtGoodsSnapshotGalleryMapper, PtGoodsSnapshotGallery> implements IPtGoodsSnapshotGalleryService {
    @Override
    public List<PtGoodsGalleryVo> goodsGalleryToGoodsGalleryVo(List<PtGoodsSnapshotGallery> goodsGalleryList) {

        List<PtGoodsGalleryVo> ptGoodsGalleryVoList = new ArrayList<>();
        for (PtGoodsSnapshotGallery ptGoodsGallery : goodsGalleryList) {
            PtGoodsGalleryVo ptGoodsGalleryVo = new PtGoodsGalleryVo();
            ptGoodsGalleryVo.setId(ptGoodsGallery.getId());
            ptGoodsGalleryVo.setType(ptGoodsGallery.getType());
            ptGoodsGalleryVo.setImageUrl(ptGoodsGallery.getImageUrl());
            String str = ptGoodsGallery.getImageUrl();
            String pointImageAddress = ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name());
            String pointAddress =   ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name());
            if (pointImageAddress!=null&&pointAddress!=null){
                String rallyPath= str.replace(pointImageAddress,pointAddress);
                ptGoodsGalleryVo.setReallyPath(rallyPath);
            }
            int indexStart = str.lastIndexOf("/");
            int indexEnd = str.lastIndexOf(".");
            ptGoodsGalleryVo.setImageName(str.substring(indexStart+1,indexEnd));
            ptGoodsGalleryVoList.add(ptGoodsGalleryVo);
        }
        return ptGoodsGalleryVoList;

    }




}
