package com.yunsunyun.point.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunsunyun.config.util.ConfigUtil;
import com.yunsunyun.point.mapper.PtGoodsSnapshotGalleryMapper;
import com.yunsunyun.point.po.PtGoodsGallery;
import com.yunsunyun.point.mapper.PtGoodsGalleryMapper;
import com.yunsunyun.point.po.PtGoodsSnapshot;
import com.yunsunyun.point.po.PtGoodsSnapshotGallery;
import com.yunsunyun.point.service.IPtGoodsGalleryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.service.IPtGoodsSnapshotService;
import com.yunsunyun.point.vo.PtGoodsGalleryVo;
import com.yunsunyun.xsimple.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@Service
public class PtGoodsGalleryServiceImpl extends ServiceImpl<PtGoodsGalleryMapper, PtGoodsGallery> implements IPtGoodsGalleryService {

    @Autowired
    private PtGoodsGalleryMapper goodsGalleryMapper;

    @Autowired
    private PtGoodsSnapshotGalleryMapper goodsSnapshotGalleryMapper;

    @Autowired
    private IPtGoodsSnapshotService iPtGoodsSnapshotService;



    /**
     * 新增商品详情图
     * @param goodsId
     * @param file
     * @return
     */
    @Override
    public Map<String, Object> saveDetailGallery(Long goodsId, MultipartFile file) {
        return saveGalleryOne(goodsId,file,Setting.GoodsGallery.DETAIL.getGalleryType());
    }

    /**
     * 新增商品轮播图
     * @param goodsId
     * @param file
     * @return
     */
    @Override
    public Map<String, Object> saveCarouselGallery(Long goodsId, MultipartFile file) {
        return saveGalleryOne(goodsId,file,Setting.GoodsGallery.CAROUSEL.getGalleryType());
    }

    private Map<String, Object> saveGalleryOne(Long goodsId, MultipartFile file,Integer type){
        Map<String,Object> respMap = new HashMap<>();
        if(goodsId == null){
            respMap.put("code",500);
            respMap.put("msg","请先填写商品详情!");
            return respMap;
        }

        PtGoodsGallery gallery = new PtGoodsGallery(goodsId, type);
        Integer result = goodsGalleryMapper.insertGalleryReturnId(gallery);

        PtGoodsSnapshot ptGoodsSnapshot = iPtGoodsSnapshotService.selectOne(new EntityWrapper<PtGoodsSnapshot>().eq("goods_id", goodsId));
        PtGoodsSnapshotGallery goodsSnapshotGallery = new PtGoodsSnapshotGallery();
        goodsSnapshotGallery.setSnapshotId(ptGoodsSnapshot.getId());
        goodsSnapshotGallery.setType(type);
        goodsSnapshotGalleryMapper.insert(goodsSnapshotGallery);

        if(file != null){
            String fileName = file.getOriginalFilename();
            int i = fileName.lastIndexOf(".");
            String imageUrl = Setting.GALLERYGOODSIMAGE + "/"  + gallery.getId() + (i >= 0 ? fileName.substring(i) : ".jpg");
            gallery.setImageUrl(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name())+ imageUrl);
            goodsSnapshotGallery.setImageUrl(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name())+ imageUrl);
            String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name()) + imageUrl;
            File flag = updateImg(path,file);
            result = goodsGalleryMapper.updateById(gallery);
            goodsSnapshotGalleryMapper.updateById(goodsSnapshotGallery);
        }

        if (result <= 0 ){
            respMap.put("code",500);
            respMap.put("msg","上传失败!");
        }else {
            respMap.put("code", 200);
            respMap.put("msg", "上传成功!");
            respMap.put("result",gallery.getImageUrl());
            respMap.put("id",gallery.getId());

        }
        return respMap;
    }

    @Override
    public Map<String, Object> saveGalleryImage(MultipartFile file) {
        Map<String,Object> resMap = new HashMap<>();
        if(file != null){
            String fileName = file.getOriginalFilename();
            int i = fileName.lastIndexOf(".");
            String imgName = UUID.randomUUID().toString();
            String imageUrl = Setting.GALLERYGOODSIMAGESHOT +  "/" + imgName + (i >= 0 ? fileName.substring(i) : ".jpg");
            String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name()) + imageUrl;
            File flag = updateImg(path,file);
            if (flag!=null){
                resMap.put("stat",true);
                resMap.put("imgName",imgName);
                resMap.put("reallyPath",path);
                resMap.put("imagePath",ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name())+imageUrl);
                resMap.put("msg","图片添加成功！");
            }else{
                resMap.put("stat",false);
                resMap.put("msg","图片添加错误！");
            }
            return  resMap;
        }
        resMap.put("stat",false);
        resMap.put("msg","图片添加错误！");
        return resMap;
    }


    @Override
    public Map<String, Object> deleteGalleryImage(String filePath) {
        Map<String,Object> resMap = new HashMap<>();
        File file = new File(filePath);
        if (!file.exists()) {
            resMap.put("stat",false);
            resMap.put("msg","删除的文件不存在");
            return  resMap;
        } else {
            if (file.isFile()&&file.exists() ){
                if (file.delete()) {
                    resMap.put("stat",true);
                    resMap.put("msg","删除成功！");
                } else {
                    resMap.put("stat",true);
                    resMap.put("msg","删除失败！");
                }
            }
            return  resMap;
        }
    }

    private File updateImg(String path, MultipartFile file) {
        try {
            File localFile = new File(path);
            if (!localFile.getParentFile().exists()) {
                localFile.getParentFile().mkdirs();
            }
            file.transferTo(localFile);
            return localFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PtGoodsGalleryVo> goodsGalleryToGoodsGalleryVo(List<PtGoodsGallery> goodsGalleryList) {
        List<PtGoodsGalleryVo> ptGoodsGalleryVoList = new ArrayList<>();
        for (PtGoodsGallery ptGoodsGallery : goodsGalleryList) {
            PtGoodsGalleryVo ptGoodsGalleryVo = new PtGoodsGalleryVo();
            ptGoodsGalleryVo.setId(ptGoodsGallery.getId());
            ptGoodsGalleryVo.setType(ptGoodsGallery.getType());
            ptGoodsGalleryVo.setGoodsId(ptGoodsGallery.getGoodsId());
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

    @Override
    public List<PtGoodsGalleryVo> goodsGallerySnapshotToGoodsGalleryVo(List<PtGoodsSnapshotGallery> goodsGalleryList) {
        List<PtGoodsGalleryVo> ptGoodsGalleryVoList = new ArrayList<>();
        for (PtGoodsSnapshotGallery ptGoodsSnapshotGallery : goodsGalleryList) {
            PtGoodsGalleryVo ptGoodsGalleryVo = new PtGoodsGalleryVo();
            ptGoodsGalleryVo.setId(ptGoodsSnapshotGallery.getId());
            ptGoodsGalleryVo.setType(ptGoodsSnapshotGallery.getType());
            ptGoodsGalleryVo.setGoodsId(ptGoodsSnapshotGallery.getSnapshotId());
            ptGoodsGalleryVo.setImageUrl(ptGoodsSnapshotGallery.getImageUrl());
            String str = ptGoodsSnapshotGallery.getImageUrl();
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
