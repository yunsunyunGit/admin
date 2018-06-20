package com.yunsunyun.point.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtRedemptionRecord;
import com.baomidou.mybatisplus.service.IService;
import com.yunsunyun.point.vo.PtRedemptionRecordVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-04-12
 */
public interface IPtRedemptionRecordService extends IService<PtRedemptionRecord> {
      Page<PtRedemptionRecordVo> getRecordlist(Page<PtRedemptionRecordVo> page,Map<String, Object> searchParams) throws  Exception;
      List<PtRedemptionRecordVo> getRecordlist(Map<String, Object> searchParams) throws  Exception;
}
