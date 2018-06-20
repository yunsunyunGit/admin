package com.yunsunyun.point.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtMall;
import com.yunsunyun.point.mapper.PtMallMapper;
import com.yunsunyun.point.service.IPtMallService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunsunyun.point.vo.PtMallVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
public class PtMallServiceImpl extends ServiceImpl<PtMallMapper, PtMall> implements IPtMallService {
    @Autowired
    private PtMallMapper ptMallMapper;

    @Override
    public Page<PtMallVo> getMallPage(Page<PtMallVo> page, String mallName) {
        Map<String,Object> map = new HashMap();
        map.put("mallName",mallName);
        return page.setRecords(ptMallMapper.getPageMallVo(page,map));
    }
}
