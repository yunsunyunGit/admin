package com.yunsunyun.point.service.impl;

import com.yunsunyun.dictionary.dao.KnDictionaryDao;
import com.yunsunyun.point.po.PtBanner;
import com.yunsunyun.point.mapper.PtBannerMapper;
import com.yunsunyun.point.service.IPtBannerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-04-08
 */
@Transactional
@Service
public class PtBannerServiceImpl extends ServiceImpl<PtBannerMapper, PtBanner> implements IPtBannerService {

    @Autowired
    private PtBannerMapper bannerMapper;
    @Autowired
    private KnDictionaryDao dictionaryDao;

    @Override
    public Long insertCatagoryReturnId(PtBanner banner) {
        return bannerMapper.insertCatagoryReturnId(banner);
    }

}
