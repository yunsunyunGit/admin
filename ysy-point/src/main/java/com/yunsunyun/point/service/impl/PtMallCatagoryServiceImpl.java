package com.yunsunyun.point.service.impl;

import com.yunsunyun.point.po.PtMallCatagory;
import com.yunsunyun.point.mapper.PtMallCatagoryMapper;
import com.yunsunyun.point.service.IPtMallCatagoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-04-11
 */
@Service
public class PtMallCatagoryServiceImpl extends ServiceImpl<PtMallCatagoryMapper, PtMallCatagory> implements IPtMallCatagoryService {

    @Autowired
    private PtMallCatagoryMapper ptMallCatagoryMapper;

    @Override
    public String getCatagoryMallNames(Long catagoryId) {
        return ptMallCatagoryMapper.getCatagoryMallNames(catagoryId);
    }
}
