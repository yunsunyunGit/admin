package com.yunsunyun.point.service;

import com.yunsunyun.point.po.PtMallMerchant;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商城-经销商 关联表 服务类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface IPtMallMerchantService extends IService<PtMallMerchant> {
	public List<Long> getMerchantIdsByMallId(Long Id);

    public List<Long> getMallIdsByMerchantId(Long Id);
 }
