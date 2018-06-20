package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtMallCatagory;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-04-11
 */
public interface PtMallCatagoryMapper extends BaseMapper<PtMallCatagory> {
    String getCatagoryMallNames(Long catagoryId);
}