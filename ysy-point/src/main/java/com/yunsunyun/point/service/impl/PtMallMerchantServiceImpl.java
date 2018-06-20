package com.yunsunyun.point.service.impl;

import com.yunsunyun.point.po.PtMallMerchant;
import com.yunsunyun.point.mapper.PtMallMerchantMapper;
import com.yunsunyun.point.service.IPtMallMerchantService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商城-经销商 关联表 服务实现类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
@Service
public class PtMallMerchantServiceImpl extends ServiceImpl<PtMallMerchantMapper, PtMallMerchant> implements IPtMallMerchantService {

    @Autowired
    private PtMallMerchantMapper ptMallMerchantMapper;

    @Override
    public List<Long> getMerchantIdsByMallId(Long id) {
        return ptMallMerchantMapper.getMerchantIdsByMallId(id);
    }

    @Override
    public List<Long> getMallIdsByMerchantId(Long id) {
        return ptMallMerchantMapper.getMallIdsByMerchantId(id);
    }
}
