package com.yunsunyun.point.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtMall;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunsunyun.point.vo.PtMallVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtMallMapper extends BaseMapper<PtMall> {
     List<PtMallVo> getPageMallVo(Page<PtMallVo> page, Map<String,Object> map) ;
}