package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtGoodsCatagory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtGoodsCatagoryMapper extends BaseMapper<PtGoodsCatagory> {

    Long insertCatagoryReturnId(@Param(value = "catagory") PtGoodsCatagory catagory);
}