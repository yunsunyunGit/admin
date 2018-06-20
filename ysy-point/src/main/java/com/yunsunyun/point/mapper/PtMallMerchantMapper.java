package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtMallMerchant;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 商城-经销商 关联表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */

public interface PtMallMerchantMapper extends BaseMapper<PtMallMerchant> {
    public List<Long> getMerchantIdsByMallId(Long id);

    public List<Long> getMallIdsByMerchantId(Long Id);
}