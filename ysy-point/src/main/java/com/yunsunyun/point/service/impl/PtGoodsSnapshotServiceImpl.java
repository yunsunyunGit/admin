package com.yunsunyun.point.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtGoods;
import com.yunsunyun.point.po.PtGoodsSnapshot;
import com.yunsunyun.point.mapper.PtGoodsSnapshotMapper;
import com.yunsunyun.point.service.IPtGoodsSnapshotService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@Service
public class PtGoodsSnapshotServiceImpl extends ServiceImpl<PtGoodsSnapshotMapper, PtGoodsSnapshot> implements IPtGoodsSnapshotService {
    @Autowired
    private PtGoodsSnapshotMapper ptGoodsSnapshotMapper;

    @Override
    public Page<PtGoodsSnapshot> getGoodsSnapshotPage(Page<PtGoodsSnapshot> page, Map<String, Object> searchParams, String asc) {

        Map<String,Object> parmes = new HashMap<>();
        if (searchParams.containsKey("LIKE_sn") && StringUtils.isNotEmpty(searchParams.get("LIKE_sn").toString().trim())) {
            parmes.put("sn",searchParams.get("LIKE_sn").toString().trim());
        }
        if (searchParams.containsKey("LIKE_name") && StringUtils.isNotEmpty(searchParams.get("LIKE_name").toString().trim())) {
            parmes.put("name", searchParams.get("LIKE_name").toString().trim());
        }
        if (searchParams.containsKey("LIKE_price") && StringUtils.isNotEmpty(searchParams.get("LIKE_price").toString().trim())) {
            String price = searchParams.get("LIKE_price").toString().trim();
            parmes.put("reference_price_start", price.substring(0, price.indexOf('-')));
            parmes.put("reference_price_end", price.substring(price.indexOf('-') + 1));
        }
        if (searchParams.containsKey("LIKE_point") && StringUtils.isNotEmpty(searchParams.get("LIKE_point").toString().trim())) {
            String price = searchParams.get("LIKE_point").toString().trim();
            parmes.put("point_start", price.substring(0, price.indexOf('-')));
            parmes.put("point_end", price.substring(price.indexOf('-') + 1));
        }
        if (searchParams.containsKey("EQ_merchant") && StringUtils.isNotEmpty(searchParams.get("EQ_merchant").toString().trim())) {
            parmes.put("merchant_id",  Long.parseLong(searchParams.get("EQ_merchant").toString().trim()));
        }
        if (searchParams.containsKey("EQ_market") && StringUtils.isNotEmpty(searchParams.get("EQ_market").toString().trim())) {
            parmes.put("market_state",  Integer.parseInt(searchParams.get("EQ_market").toString().trim()));
        }
        if (searchParams.containsKey("EQ_auditState") && StringUtils.isNotEmpty(searchParams.get("EQ_auditState").toString().trim())) {
            parmes.put("audit_state",  Integer.parseInt(searchParams.get("EQ_auditState").toString().trim()));
        }
        if ("desc".equals(asc)){
            parmes.put("point","DESC");
        }else {
            parmes.put("point","ASC");
        }
        List<PtGoodsSnapshot> ptGoodsSnapshotList =  ptGoodsSnapshotMapper.selectGoodsSnapshotPage(page,parmes);
        page.setRecords(ptGoodsSnapshotList);
        return page;
    }

    @Override
    public List<PtGoodsSnapshot> getGoodsSnapshotPage(Map<String, Object> searchParams) {
        Map<String,Object> parmes = new HashMap<>();
        if (searchParams.containsKey("LIKE_sn") && StringUtils.isNotEmpty(searchParams.get("LIKE_sn").toString().trim())) {
            parmes.put("sn",searchParams.get("LIKE_sn").toString().trim());
        }
        if (searchParams.containsKey("LIKE_name") && StringUtils.isNotEmpty(searchParams.get("LIKE_name").toString().trim())) {
            parmes.put("name", searchParams.get("LIKE_name").toString().trim());
        }
        if (searchParams.containsKey("LIKE_price") && StringUtils.isNotEmpty(searchParams.get("LIKE_price").toString().trim())) {
            String price = searchParams.get("LIKE_price").toString().trim();
            parmes.put("reference_price_start", price.substring(0, price.indexOf('-')));
            parmes.put("reference_price_end", price.substring(price.indexOf('-') + 1));
        }
        if (searchParams.containsKey("LIKE_point") && StringUtils.isNotEmpty(searchParams.get("LIKE_point").toString().trim())) {
            String price = searchParams.get("LIKE_point").toString().trim();
            parmes.put("point_start", price.substring(0, price.indexOf('-')));
            parmes.put("point_end", price.substring(price.indexOf('-') + 1));
        }
        if (searchParams.containsKey("EQ_merchant") && StringUtils.isNotEmpty(searchParams.get("EQ_merchant").toString().trim())) {
            parmes.put("merchant_id",  Long.parseLong(searchParams.get("EQ_merchant").toString().trim()));
        }
        if (searchParams.containsKey("EQ_market") && StringUtils.isNotEmpty(searchParams.get("EQ_market").toString().trim())) {
            parmes.put("market_state",  Integer.parseInt(searchParams.get("EQ_market").toString().trim()));
        }
        if (searchParams.containsKey("EQ_auditState") && StringUtils.isNotEmpty(searchParams.get("EQ_auditState").toString().trim())) {
            parmes.put("audit_state",  Integer.parseInt(searchParams.get("EQ_auditState").toString().trim()));
        }
        List<PtGoodsSnapshot> ptGoodsSnapshotList =  ptGoodsSnapshotMapper.selectGoodsSnapshotPage(parmes);
        return ptGoodsSnapshotList;
    }


    public PtGoods ptGoodsSnapshotToPtGoods(PtGoodsSnapshot ptGoodsSnapshot) {
        PtGoods ptGoods = new PtGoods();
        ptGoods.setId(ptGoodsSnapshot.getGoodsId());
        ptGoods.setMerchantId(ptGoodsSnapshot.getMerchantId());
        ptGoods.setName(ptGoodsSnapshot.getName());
        ptGoods.setSn(ptGoodsSnapshot.getSn());
        ptGoods.setDescription(ptGoodsSnapshot.getDescription());
        ptGoods.setPoint(ptGoodsSnapshot.getPoint());
        ptGoods.setIntroduce(ptGoodsSnapshot.getIntroduce());
        ptGoods.setStock(ptGoodsSnapshot.getStock());
        ptGoods.setSeq(ptGoodsSnapshot.getSeq());
        ptGoods.setGrade(ptGoodsSnapshot.getGrade());
        ptGoods.setComment(ptGoodsSnapshot.getComment());
        ptGoods.setImageUrl(ptGoodsSnapshot.getImageUrl());
        ptGoods.setReferencePrice(ptGoodsSnapshot.getReferencePrice());
        ptGoods.setAuditDisableReason(ptGoodsSnapshot.getAuditDisableReason());
        ptGoods.setAuditState(ptGoodsSnapshot.getAuditState());
        ptGoods.setMarketState(ptGoodsSnapshot.getMarketState());
        ptGoods.setGoodsType(ptGoodsSnapshot.getGoodsType());
        ptGoods.setIsDeleted(ptGoodsSnapshot.getIsDeleted());
        ptGoods.setViewCount(ptGoodsSnapshot.getViewCount());
        ptGoods.setBuyCount(ptGoodsSnapshot.getBuyCount());
        ptGoods.setMetaKeywords(ptGoodsSnapshot.getMetaKeywords());
        ptGoods.setCreateId(ptGoodsSnapshot.getCreateId());
        ptGoods.setCreateId(ptGoodsSnapshot.getCreateId());
        ptGoods.setCreateTime(ptGoodsSnapshot.getCreateTime());
        ptGoods.setUpdateId(ptGoodsSnapshot.getUpdateId());
        ptGoods.setUpdateTime(ptGoodsSnapshot.getUpdateTime());
        return ptGoods;
    }

}
