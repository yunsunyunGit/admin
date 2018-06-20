package com.yunsunyun.point.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtOrder;
import com.baomidou.mybatisplus.service.IService;
import com.yunsunyun.point.vo.PtOrderVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface IPtOrderService extends IService<PtOrder> {
	 Page<PtOrderVo> getOrderVoList(Page<PtOrderVo> page, Map<String,Object> params) throws Exception ;
	 List<PtOrderVo> getOrderVoList(Map<String,Object> params) throws Exception ;
	 void orderSchedule();
}
