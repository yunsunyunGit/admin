package com.yunsunyun.point.service;

import com.yunsunyun.point.po.PtMallCatagory;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-04-11
 */
public interface IPtMallCatagoryService extends IService<PtMallCatagory> {
	String getCatagoryMallNames(Long catagoryId);
}
