package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtTip;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtTipMapper extends BaseMapper<PtTip> {

    /**
     * 将所有消息设置成旧状态
     * @param mallId
     * @param memberId
     */
    void updateIsNew(@Param("mallId") Long mallId,@Param("memberId") Long memberId);
}