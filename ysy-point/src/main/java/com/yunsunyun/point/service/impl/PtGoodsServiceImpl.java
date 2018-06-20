package com.yunsunyun.point.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.config.util.ConfigUtil;
import com.yunsunyun.dictionary.dao.KnDictionaryDao;
import com.yunsunyun.point.mapper.PtCatagoryGoodsMapper;
import com.yunsunyun.point.mapper.PtCatagorySnapshotGoodsMapper;
import com.yunsunyun.point.mapper.PtGoodsMapper;
import com.yunsunyun.point.mapper.PtMallGoodsMapper;
import com.yunsunyun.point.po.*;
import com.yunsunyun.point.service.IPtGoodsGalleryService;
import com.yunsunyun.point.service.IPtGoodsService;
import com.yunsunyun.point.service.IPtGoodsSnapshotGalleryService;
import com.yunsunyun.point.service.IPtGoodsSnapshotService;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2018-03-30
 */
@Service
public class PtGoodsServiceImpl extends ServiceImpl<PtGoodsMapper, PtGoods> implements IPtGoodsService {

    @Autowired
    private PtGoodsMapper goodsMapper;
    @Autowired
    private PtMallGoodsMapper mallGoodsMapper;
    @Autowired
    private PtCatagoryGoodsMapper catagoryGoodsMapper;
    @Autowired
    private PtCatagorySnapshotGoodsMapper ptCatagorySnapshotGoodsMapper;
    @Autowired
    private IPtGoodsGalleryService iPtGoodsGalleryService;
    @Autowired
    private IPtGoodsSnapshotGalleryService iPtGoodsSnapshotGalleryService;

    @Autowired
    private IPtGoodsSnapshotService iPtGoodsSnapshotService;

    @Autowired
    private KnDictionaryDao dictionaryDao;

    /**
     * 查询商品数据
     *
     * @param dt
     * @param searchParams
     * @param mallId
     * @param findType     查询商品审核类型， null,-1 默认查询所有，0查询未审核，1 查询已审核 2，审核不通过，3查询 未审核和审核不通过
     * @return
     */
    @Override
    public Page<PtGoods> getGoodsPage(Page page, Map<String, Object> searchParams, Long mallId, Integer findType, boolean point, boolean asc) {
        Long[] goodsId = null;
        if (mallId != 0) {
            List<PtMallGoods> list = mallGoodsMapper.selectList(new EntityWrapper<PtMallGoods>().where("mall_id = {0}", mallId));
            goodsId = new Long[list.size()];
            if (goodsId.length == 0) {
                goodsId = new Long[]{-1L};
            }
            for (int i = 0; i < list.size(); i++) {
                goodsId[i] = list.get(i).getGoodsId();
            }
        }
        Wrapper<PtGoods> wrapper = new EntityWrapper<>();
        wrapper.eq("is_deleted", 0);
        if (point){
            wrapper.orderBy("point",asc);
        }
            wrapper.orderBy("create_time",false);

        if (findType == Setting.AuditState.auditWaiting.getAuditStateCode() || findType == Setting.AuditState.audited.getAuditStateCode() || findType == Setting.AuditState.AuditFailed.getAuditStateCode()) {
            wrapper.eq("audit_state", findType);
        } else if (findType == 3) {
            wrapper.and("audit_state = {0} or audit_state = {1}", Setting.AuditState.auditWaiting.getAuditStateCode(), Setting.AuditState.AuditFailed.getAuditStateCode());
        }
        wrapper.in("id", goodsId);
        if (searchParams.containsKey("LIKE_sn") && StringUtils.isNotEmpty(searchParams.get("LIKE_sn").toString().trim())) {
            wrapper.like("sn", searchParams.get("LIKE_sn").toString().trim());
        }
        if (searchParams.containsKey("LIKE_name") && StringUtils.isNotEmpty(searchParams.get("LIKE_name").toString().trim())) {
            wrapper.like("name", searchParams.get("LIKE_name").toString().trim());
        }
        if (searchParams.containsKey("LIKE_price") && StringUtils.isNotEmpty(searchParams.get("LIKE_price").toString().trim())) {
            String price = searchParams.get("LIKE_price").toString().trim();
            wrapper.between("reference_price", price.substring(0, price.indexOf('-')), price.substring(price.indexOf('-') + 1));
        }
        if (searchParams.containsKey("LIKE_point") && StringUtils.isNotEmpty(searchParams.get("LIKE_point").toString().trim())) {
            String price = searchParams.get("LIKE_point").toString().trim();
            wrapper.between("point", price.substring(0, price.indexOf('-')), price.substring(price.indexOf('-') + 1));
        }
        if (searchParams.containsKey("EQ_merchant") && StringUtils.isNotEmpty(searchParams.get("EQ_merchant").toString().trim())) {
            wrapper.eq("merchant_id", Long.parseLong(searchParams.get("EQ_merchant").toString().trim()));
        }
        if (searchParams.containsKey("EQ_market") && StringUtils.isNotEmpty(searchParams.get("EQ_market").toString().trim())) {
            wrapper.eq("market_state", Integer.parseInt(searchParams.get("EQ_market").toString().trim()));
        }
        if (searchParams.containsKey("EQ_auditState") && StringUtils.isNotEmpty(searchParams.get("EQ_auditState").toString().trim())) {
            wrapper.eq("audit_state", Integer.parseInt(searchParams.get("EQ_auditState").toString().trim()));
        }
        page = selectPage(page, wrapper);
        return page;
    }

    @Override
    public List<PtGoods> getGoodsPage( Map<String, Object> searchParams, Long mallId, Integer findType) {
        Long[] goodsId = null;
        if (mallId != 0) {
            List<PtMallGoods> list = mallGoodsMapper.selectList(new EntityWrapper<PtMallGoods>().where("mall_id = {0}", mallId));
            goodsId = new Long[list.size()];
            if (goodsId.length == 0) {
                goodsId = new Long[]{-1L};
            }
            for (int i = 0; i < list.size(); i++) {
                goodsId[i] = list.get(i).getGoodsId();
            }
        }
        Wrapper<PtGoods> wrapper = new EntityWrapper<>();
        wrapper.eq("is_deleted", 0);
        if (findType == Setting.AuditState.auditWaiting.getAuditStateCode() || findType == Setting.AuditState.audited.getAuditStateCode() || findType == Setting.AuditState.AuditFailed.getAuditStateCode()) {
            wrapper.eq("audit_state", findType);
        } else if (findType == 3) {
            wrapper.and("audit_state = {0} or audit_state = {1}", Setting.AuditState.auditWaiting.getAuditStateCode(), Setting.AuditState.AuditFailed.getAuditStateCode());
        }
        wrapper.in("id", goodsId);
        if (searchParams.containsKey("LIKE_sn") && StringUtils.isNotEmpty(searchParams.get("LIKE_sn").toString().trim())) {
            wrapper.like("sn", searchParams.get("LIKE_sn").toString().trim());
        }
        if (searchParams.containsKey("LIKE_name") && StringUtils.isNotEmpty(searchParams.get("LIKE_name").toString().trim())) {
            wrapper.like("name", searchParams.get("LIKE_name").toString().trim());
        }
        if (searchParams.containsKey("LIKE_price") && StringUtils.isNotEmpty(searchParams.get("LIKE_price").toString().trim())) {
            String price = searchParams.get("LIKE_price").toString().trim();
            wrapper.between("reference_rice", price.substring(0, price.indexOf('-')), price.substring(price.indexOf('-') + 1));
        }
        if (searchParams.containsKey("LIKE_point") && StringUtils.isNotEmpty(searchParams.get("LIKE_point").toString().trim())) {
            String price = searchParams.get("LIKE_point").toString().trim();
            wrapper.between("point", price.substring(0, price.indexOf('-')), price.substring(price.indexOf('-') + 1));
        }
        if (searchParams.containsKey("EQ_merchant") && StringUtils.isNotEmpty(searchParams.get("EQ_merchant").toString().trim())) {
            wrapper.eq("merchant_id", Long.parseLong(searchParams.get("EQ_merchant").toString().trim()));
        }
        if (searchParams.containsKey("EQ_market") && StringUtils.isNotEmpty(searchParams.get("EQ_market").toString().trim())) {
            wrapper.eq("market_state", Integer.parseInt(searchParams.get("EQ_market").toString().trim()));
        }
        if (searchParams.containsKey("EQ_auditState") && StringUtils.isNotEmpty(searchParams.get("EQ_auditState").toString().trim())) {
            wrapper.eq("audit_state", Integer.parseInt(searchParams.get("EQ_auditState").toString().trim()));
        }
        return selectList(wrapper);
    }



    @Override
    @Transactional
    public Boolean createGoods(PtGoods goods, Long[] cataIds, MultipartFile goodsImage,List<String> detailImage, List<String> carouselImage) {
        Map<String, Object> resMap = new HashMap<>();
        //插入商品表单数据
        ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
        goods.setMerchantId(loginUser.getId());
        goods.setCreateId(loginUser.getId());
        goods.setCreateTime(System.currentTimeMillis());
        goods.setAuditState(Setting.AuditState.auditWaiting.getAuditStateCode());
        goods.setMarketState(Setting.MarketStat.soldOut.getMaketStatCode());
        goods.setIsModified(0);
         goodsMapper.insertGoodsReturnId(goods);
        Integer result = 0;
        //插入商品列表图片
        if (!goodsImage.isEmpty()) {
            String fileName = goodsImage.getOriginalFilename();
            int i = fileName.lastIndexOf(".");
            if (fileName!=null&&fileName.length()>0) {
                String imageUrl = Setting.GOODSIMAGE + "/"  + goods.getId() + (i >= 0 ? fileName.substring(i) : ".jpg");
                goods.setImageUrl(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name()) + imageUrl);
                String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name()) + imageUrl;
                File file = updateImg(path, goodsImage);
                result = goodsMapper.updateById(goods);
            }
        }
        //创建商品与分类的关联信息  创建商品与分类的关联快照信息
        if (cataIds != null && cataIds.length > 0) {
            for (Long cataId : cataIds) {
                PtCatagoryGoods catagoryGoods = new PtCatagoryGoods();
                catagoryGoods.setGoodsId(goods.getId());
                catagoryGoods.setCatagoryId(cataId);
                PtCatagoryGoods flag = catagoryGoodsMapper.selectOne(catagoryGoods);
                if (flag == null) {
                    catagoryGoodsMapper.insert(catagoryGoods);
                }
            }
            //删除多余的该商品与分类的信息
            catagoryGoodsMapper.delete(new EntityWrapper<PtCatagoryGoods>().where("goods_id = {0}", goods.getId()).notIn("catagory_id", cataIds));
        }
        //插入商品详情图和轮播图
        iPtGoodsGalleryService.delete(new EntityWrapper<PtGoodsGallery>().eq("goods_id",goods.getId()));
        List<PtGoodsGallery> goodsGalleryList  = new ArrayList<>();
        if(detailImage!=null){
            for (String imagePath : detailImage) {
                PtGoodsGallery ptGoodsGallery = new PtGoodsGallery();
                ptGoodsGallery.setGoodsId(goods.getId());
                ptGoodsGallery.setType(Setting.GoodsGallery.DETAIL.getGalleryType());
                ptGoodsGallery.setImageUrl(imagePath);
                goodsGalleryList.add(ptGoodsGallery);
            }
        }
        if (carouselImage!=null){
            for (String imagePath : carouselImage) {
                PtGoodsGallery ptGoodsGallery = new PtGoodsGallery();
                ptGoodsGallery.setGoodsId(goods.getId());
                ptGoodsGallery.setType(Setting.GoodsGallery.CAROUSEL.getGalleryType());
                ptGoodsGallery.setImageUrl(imagePath);
                goodsGalleryList.add(ptGoodsGallery);
            }
        }
        if (goodsGalleryList!=null&&goodsGalleryList.size()>0){
            boolean flag = iPtGoodsGalleryService.insertBatch(goodsGalleryList);
        }
        return result>0;
    }

    @Override
    @Transactional
    public Integer updateGoods(PtGoods goods, Long[] cataIds, MultipartFile goodsImage, List<String> detailImage, List<String> carouselImage) {
        PtGoods ptGoods = selectById(goods.getId());
        boolean flagReturn = false;
        //下架状态 只需要修改 商品表、商品相册表、商品分类关联表
        if (ptGoods.getMarketState()==Setting.MarketStat.soldOut.getMaketStatCode()){
            //修改商品表
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            goods.setUpdateId(loginUser.getId());
            goods.setUpdateTime(System.currentTimeMillis());
            goods.setAuditState(Setting.AuditState.auditWaiting.getAuditStateCode());
            //修改商品图片字段，上传图片
            if (!goodsImage.isEmpty()) {
                String fileName = goodsImage.getOriginalFilename();
                int i = fileName.lastIndexOf(".");
                if (fileName!=null&&fileName.length()>0){
                    String imageUrl = Setting.GOODSIMAGE +  "/" + goods.getId() + (i >= 0 ? fileName.substring(i) : ".jpg");
                    goods.setImageUrl(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name())+imageUrl);
                    String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name()) + imageUrl;
                    File file = updateImg(path, goodsImage);
                }
            }
            flagReturn =  updateById(goods);
            //修改商品与分类的关联信息
            if (cataIds != null && cataIds.length > 0) {
                for (Long cataId : cataIds) {
                    PtCatagoryGoods catagoryGoods = new PtCatagoryGoods();
                    catagoryGoods.setGoodsId(goods.getId());
                    catagoryGoods.setCatagoryId(cataId);
                    PtCatagoryGoods flag = catagoryGoodsMapper.selectOne(catagoryGoods);
                    if (flag == null) {
                        catagoryGoodsMapper.insert(catagoryGoods);
                    }
                }
                //删除多余的该商品与分类的信息
                catagoryGoodsMapper.delete(new EntityWrapper<PtCatagoryGoods>().where("goods_id = {0}", goods.getId()).notIn("catagory_id", cataIds));
            }
            //插入商品详情图和轮播图
            iPtGoodsGalleryService.delete(new EntityWrapper<PtGoodsGallery>().eq("goods_id",goods.getId()));
            List<PtGoodsGallery> goodsGalleryList  = new ArrayList<>();
            if (detailImage!=null&&detailImage.size()>0){
                for (String imagePath : detailImage) {
                    PtGoodsGallery ptGoodsGallery = new PtGoodsGallery();
                    ptGoodsGallery.setGoodsId(goods.getId());
                    ptGoodsGallery.setType(Setting.GoodsGallery.DETAIL.getGalleryType());
                    ptGoodsGallery.setImageUrl(imagePath);
                    goodsGalleryList.add(ptGoodsGallery);
                }
            }
            if (carouselImage!=null&&carouselImage.size()>0){
                for (String imagePath : carouselImage) {
                    PtGoodsGallery ptGoodsGallery = new PtGoodsGallery();
                    ptGoodsGallery.setGoodsId(goods.getId());
                    ptGoodsGallery.setType(Setting.GoodsGallery.CAROUSEL.getGalleryType());
                    ptGoodsGallery.setImageUrl(imagePath);
                    goodsGalleryList.add(ptGoodsGallery);
                }
            }
            if (goodsGalleryList!=null&&goodsGalleryList.size()>0){
                boolean flag = iPtGoodsGalleryService.insertBatch(goodsGalleryList);
            }
            if (flagReturn){
                return  1;
            }
        }else if(ptGoods.getMarketState()==Setting.MarketStat.OnShelf.getMaketStatCode()){//上架状态 产生快照
            goodsMapper.update(new PtGoods().setIsModified(1),new EntityWrapper<PtGoods>().eq("id",goods.getId()));
            iPtGoodsSnapshotService.delete(new EntityWrapper<PtGoodsSnapshot>().eq("goods_id",goods.getId()));

            //判断是否审核未通过 当审核未通过的时候修改 删除商品表里审核未通过的那条数据
            if (ptGoods.getAuditState()==Setting.AuditState.AuditFailed.getAuditStateCode()){
                goodsMapper.delete(new EntityWrapper<PtGoods>().eq("id",goods.getId()));
            }

            PtGoodsSnapshot ptGoodsSnapshot = ptGoodsToPtGoodsSnapshot(ptGoods);
            //修改商品快照表
            ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();// 取得当前用户信息
            ptGoodsSnapshot.setName(goods.getName());
            ptGoodsSnapshot.setSn(goods.getSn());
            ptGoodsSnapshot.setStock(goods.getStock());
            ptGoodsSnapshot.setReferencePrice(goods.getReferencePrice());
            ptGoodsSnapshot.setPoint(goods.getPoint());
            ptGoodsSnapshot.setGoodsType(goods.getGoodsType());
            ptGoodsSnapshot.setUpdateId(loginUser.getId());
            ptGoodsSnapshot.setUpdateTime(System.currentTimeMillis());
            ptGoodsSnapshot.setAuditState(Setting.AuditState.auditWaiting.getAuditStateCode());
            //修改商品图片字段，上传图片
            if (goodsImage != null) {
                String fileName = goodsImage.getOriginalFilename();
                int i = fileName.lastIndexOf(".");
                if (fileName!=null&&fileName.length()>0) {
                    String imageUrl = Setting.GOODSIMAGESHOT + "/"  + UUID.randomUUID() + (i >= 0 ? fileName.substring(i) : ".jpg");
                    ptGoodsSnapshot.setImageUrl(ConfigUtil.get(Setting.ConfigKeys.POINTIMAGEADDRESS.name()) + imageUrl);
                    String path = ConfigUtil.get(Setting.ConfigKeys.POINTADDRESS.name()) + imageUrl;
                    File file = updateImg(path, goodsImage);
                }
            }
            flagReturn = iPtGoodsSnapshotService.insert(ptGoodsSnapshot);
            // 创建商品与分类的关联快照信息
            if (cataIds != null && cataIds.length > 0) {
                for (Long cataId : cataIds) {
                    PtCatagorySnapshotGoods catagoryGoods = new PtCatagorySnapshotGoods();
                    catagoryGoods.setSnapshotId(ptGoodsSnapshot.getId());
                    catagoryGoods.setCatagoryId(cataId);
                    PtCatagorySnapshotGoods flag = ptCatagorySnapshotGoodsMapper.selectOne(catagoryGoods);
                    if (flag == null) {
                        ptCatagorySnapshotGoodsMapper.insert(catagoryGoods);
                    }
                }
                //删除多余的该商品与分类的信息
                ptCatagorySnapshotGoodsMapper.delete(new EntityWrapper<PtCatagorySnapshotGoods>().where("snapshot_id = {0}", goods.getId()).notIn("catagory_id", cataIds));
            }
            //插入商品详情图和轮播图
            iPtGoodsSnapshotGalleryService.delete(new EntityWrapper<PtGoodsSnapshotGallery>().eq("snapshot_id",ptGoodsSnapshot.getId()));
            List<PtGoodsSnapshotGallery> goodsGalleryList  = new ArrayList<>();
            if (detailImage!=null&&detailImage.size()>0){
                for (String imagePath : detailImage) {
                    PtGoodsSnapshotGallery ptGoodsGallery = new PtGoodsSnapshotGallery();
                    ptGoodsGallery.setSnapshotId(ptGoodsSnapshot.getId());
                    ptGoodsGallery.setType(Setting.GoodsGallery.DETAIL.getGalleryType());
                    ptGoodsGallery.setImageUrl(imagePath);
                    goodsGalleryList.add(ptGoodsGallery);
                }
            }
            if (carouselImage!=null&&carouselImage.size()>0){
                for (String imagePath : carouselImage) {
                    PtGoodsSnapshotGallery ptGoodsGallery = new PtGoodsSnapshotGallery();
                    ptGoodsGallery.setSnapshotId(ptGoodsSnapshot.getId());
                    ptGoodsGallery.setType(Setting.GoodsGallery.CAROUSEL.getGalleryType());
                    ptGoodsGallery.setImageUrl(imagePath);
                    goodsGalleryList.add(ptGoodsGallery);
                }
            }
            boolean flag = iPtGoodsSnapshotGalleryService.insertBatch(goodsGalleryList);
            if (flagReturn){
                return  2;
            }
        }
        return 0;
    }

    public PtGoodsSnapshot ptGoodsToPtGoodsSnapshot(PtGoods ptGoods){
        PtGoodsSnapshot ptGoodsSnapshot = new PtGoodsSnapshot();
        ptGoodsSnapshot.setGoodsId(ptGoods.getId());
        ptGoodsSnapshot.setMerchantId(ptGoods.getMerchantId());
        ptGoodsSnapshot.setName(ptGoods.getName());
        ptGoodsSnapshot.setSn(ptGoods.getSn());
        ptGoodsSnapshot.setDescription(ptGoods.getDescription());
        ptGoodsSnapshot.setPoint(ptGoods.getPoint());
        ptGoodsSnapshot.setIntroduce(ptGoods.getIntroduce());
        ptGoodsSnapshot.setStock(ptGoods.getStock());
        ptGoodsSnapshot.setSeq(ptGoods.getSeq());
        ptGoodsSnapshot.setGrade(ptGoods.getGrade());
        ptGoodsSnapshot.setComment(ptGoods.getComment());
        ptGoodsSnapshot.setImageUrl(ptGoods.getImageUrl());
        ptGoodsSnapshot.setReferencePrice(ptGoods.getReferencePrice());
        ptGoodsSnapshot.setAuditDisableReason(ptGoods.getAuditDisableReason());
        ptGoodsSnapshot.setAuditState(ptGoods.getAuditState());
        ptGoodsSnapshot.setMarketState(ptGoods.getMarketState());
        ptGoodsSnapshot.setGoodsType(ptGoods.getGoodsType());
        ptGoodsSnapshot.setIsDeleted(ptGoods.getIsDeleted());
        ptGoodsSnapshot.setViewCount(ptGoods.getViewCount());
        ptGoodsSnapshot.setBuyCount(ptGoods.getBuyCount());
        ptGoodsSnapshot.setMetaKeywords(ptGoods.getMetaKeywords());
        ptGoodsSnapshot.setCreateId(ptGoods.getCreateId());
        ptGoodsSnapshot.setCreateTime(ptGoods.getCreateTime());
        ptGoodsSnapshot.setUpdateId(ptGoods.getUpdateId());
        ptGoodsSnapshot.setUpdateTime(ptGoods.getCreateTime());
        return  ptGoodsSnapshot;
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
}
