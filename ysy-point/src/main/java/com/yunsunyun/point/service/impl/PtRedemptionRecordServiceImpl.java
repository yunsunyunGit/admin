package com.yunsunyun.point.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.mapper.PtRedemptionRecordMapper;
import com.yunsunyun.point.po.PtRedemptionRecord;
import com.yunsunyun.point.service.IPtRedemptionRecordService;
import com.yunsunyun.point.vo.PtRedemptionRecordVo;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-04-12
 */
@Service
public class PtRedemptionRecordServiceImpl extends ServiceImpl<PtRedemptionRecordMapper, PtRedemptionRecord> implements IPtRedemptionRecordService {

    @Autowired
    private PtRedemptionRecordMapper ptRedemptionRecordMapper;

    @Override
    public Page<PtRedemptionRecordVo> getRecordlist(Page<PtRedemptionRecordVo> page, Map<String, Object> searchParams) throws  Exception {
        Map<String,Object> params = getSearchParams(searchParams);
        return page.setRecords(ptRedemptionRecordMapper.getRecordlist(page,params));
    }


    @Override
    public List<PtRedemptionRecordVo> getRecordlist(Map<String, Object> searchParams) throws  Exception {
        Map<String,Object> params = getSearchParams(searchParams);
        return ptRedemptionRecordMapper.getRecordlist(params);
    }


    private Map<String,Object>  getSearchParams(Map<String, Object> searchParams) throws Exception{
        Map<String,Object> params = new HashMap<>();
        if (searchParams != null && searchParams.size() != 0) {
            if (searchParams.containsKey("LIKE_mallName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_mallName").toString().trim())) {
                params.put("mallName",searchParams.get("LIKE_mallName").toString().trim());
            }
            if (searchParams.containsKey("LIKE_memberName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_memberName").toString().trim())) {
                params.put("memberName",searchParams.get("LIKE_memberName").toString().trim());
            }
            if (searchParams.containsKey("LIKE_memberPhone") && !Strings.isNullOrEmpty(searchParams.get("LIKE_memberPhone").toString().trim())) {
                params.put("memberPhone",searchParams.get("LIKE_memberPhone").toString().trim());
            }
            if (searchParams.containsKey("LIKE_merchantName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_merchantName").toString().trim())) {
                params.put("merchantName",searchParams.get("LIKE_merchantName").toString().trim());
            }
            if (searchParams.containsKey("LIKE_goodsName") && !Strings.isNullOrEmpty(searchParams.get("LIKE_goodsName").toString().trim())) {
                params.put("goodsName",searchParams.get("LIKE_goodsName").toString().trim());
            }

            if (searchParams.containsKey("LIKE_goodsNum") && !Strings.isNullOrEmpty(searchParams.get("LIKE_goodsNum").toString().trim())) {
                params.put("goodsNum",searchParams.get("LIKE_goodsNum").toString().trim());
            }
            if (searchParams.containsKey("LIKE_goodsPoint") && !Strings.isNullOrEmpty(searchParams.get("LIKE_goodsPoint").toString().trim())) {
                params.put("goodsPoint",searchParams.get("LIKE_goodsPoint").toString().trim());
            }

            if (searchParams.containsKey("LIKE_goodsPoint") && !Strings.isNullOrEmpty(searchParams.get("LIKE_goodsPoint").toString().trim())) {
                params.put("goodsPoint",searchParams.get("LIKE_goodsPoint").toString().trim());
            }

            if (searchParams.containsKey("EQ_merchant") && StringUtils.isNotEmpty(searchParams.get("EQ_merchant").toString().trim())) {
                params.put("merchantId",  Long.parseLong(searchParams.get("EQ_merchant").toString().trim()));
            }

            if (searchParams.containsKey("LIKE_startTime") && !Strings.isNullOrEmpty(searchParams.get("LIKE_startTime").toString().trim()) && searchParams.containsKey("LIKE_endTime") && !Strings.isNullOrEmpty(searchParams.get("LIKE_endTime").toString().trim())  ) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                params.put("timeStart",sdf.parse(searchParams.get("LIKE_startTime").toString().trim()).getTime());
                params.put("timeEnd",sdf.parse(searchParams.get("LIKE_endTime").toString().trim()).getTime());
            }
        }
        return  params;
    }




}
