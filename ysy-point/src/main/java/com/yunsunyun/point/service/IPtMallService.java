package com.yunsunyun.point.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtMall;
import com.baomidou.mybatisplus.service.IService;
import com.yunsunyun.point.vo.PtMallVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface IPtMallService extends IService<PtMall> {
    Page<PtMallVo> getMallPage(Page<PtMallVo> page,String mallName);
}
