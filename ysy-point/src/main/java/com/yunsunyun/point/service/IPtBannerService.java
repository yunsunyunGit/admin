package com.yunsunyun.point.service;

import com.yunsunyun.point.po.PtBanner;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-04-08
 */
public interface IPtBannerService extends IService<PtBanner> {

    /**
     * 添加一条内容实例并返回主键
     * @param banner
     * @return
     */
    Long insertCatagoryReturnId(PtBanner banner);
}
