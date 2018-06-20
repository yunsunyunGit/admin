package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtBanner;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-04-08
 */
public interface PtBannerMapper extends BaseMapper<PtBanner> {

    Long insertCatagoryReturnId(@Param(value = "banner") PtBanner banner);
}