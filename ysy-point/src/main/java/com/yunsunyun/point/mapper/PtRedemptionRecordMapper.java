package com.yunsunyun.point.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtRedemptionRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunsunyun.point.vo.PointRecordVo;
import com.yunsunyun.point.vo.PtRedemptionRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2018-04-12
 */
public interface PtRedemptionRecordMapper extends BaseMapper<PtRedemptionRecord> {
    List<PtRedemptionRecordVo> getRecordlist(Page<PtRedemptionRecordVo> page, Map<String, Object> searchParams);

    List<PtRedemptionRecordVo> getRecordlist(Map<String, Object> searchParams);

    /**
     * 查看当前登录会员在当前商城的积分消费记录
     *
     * @param offset
     * @param pageSize
     * @param mallId
     * @param memberId
     * @param date     @return
     */
    List<PointRecordVo> ajaxRecordList(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize, @Param("mallId") Long mallId, @Param("memberId") Long memberId, @Param("date") String date);

    Integer getRecordListSize(@Param("mallId") Long mallId, @Param("memberId") Long id, @Param("date") String date);
}