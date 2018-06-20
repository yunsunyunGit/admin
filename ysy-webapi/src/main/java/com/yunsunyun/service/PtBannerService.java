package com.yunsunyun.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.dictionary.dao.KnDictionaryDao;
import com.yunsunyun.dictionary.entity.KnDictionary;
import com.yunsunyun.point.po.PtBanner;
import com.yunsunyun.point.mapper.PtBannerMapper;
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
public class PtBannerService extends ServiceImpl<PtBannerMapper, PtBanner> {

    @Autowired
    private PtBannerMapper bannerMapper;
    @Autowired
    private KnDictionaryDao dictionaryDao;
    /**
     * 根据传入的字典表中banner_type子项的dic_key来查询banner列表
     * @param bannerTtpe
     * @param page
     * @return
     */
    public Page<PtBanner> selectListByType(String bannerTtpe, Page<PtBanner> page) throws Exception{
        KnDictionary dict = dictionaryDao.findByDicKey(bannerTtpe);
        page = selectPage(page,new EntityWrapper<PtBanner>().where("type={0}",dict.getId()).and("state=1").orderBy("sort",true));
        return page;
    }
}
