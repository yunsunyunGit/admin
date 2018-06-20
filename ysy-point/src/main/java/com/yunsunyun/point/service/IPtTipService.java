package com.yunsunyun.point.service;

import com.yunsunyun.point.po.PtTip;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface IPtTipService extends IService<PtTip> {
	boolean sendTip(Long orderId);
}
