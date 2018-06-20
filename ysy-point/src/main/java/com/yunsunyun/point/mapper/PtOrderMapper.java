package com.yunsunyun.point.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.point.po.PtOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yunsunyun.point.vo.PtOrderInfoVo;
import com.yunsunyun.point.vo.PtOrderVo;
import com.yunsunyun.point.vo.PtOrdersVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtOrderMapper extends BaseMapper<PtOrder> {
    /**
     * 后台获取订单列表
     * @param page
     * @param params
     * @return
     */
    List<PtOrderVo> getPtOrderVoList(Page<PtOrderVo> page, Map<String, Object> params);

    List<PtOrderVo> getPtOrderVoList(Map<String, Object> params);

    /**
     * 前台页面获取当前登录会员在当前商城的订单列表
     *
     * @param mallId
     * @param memberId
     * @param orderStatus
     * @param pageSize
     * @param pageSize
     * @return
     */
    List<PtOrdersVo> selectPageByStatus(@Param("mallId") Long mallId, @Param("memberId") Long memberId, @Param("orderStatus") Integer orderStatus, @Param("pageOffset") Integer pageOffset, @Param("pageSize") Integer pageSize);

    /**
     * 修改订单的新旧状态
     *
     * @param mallId
     * @param memberId
     * @param orderStatus
     */
    void updateIsNew(@Param("mallId") Long mallId,@Param("memberId") Long memberId, @Param("orderStatus") Integer orderStatus);

    /**
     * 根据订单id获取订单详情
     *
     * @param id
     * @return
     */
    PtOrderInfoVo getPtOrderVoInfoById(@Param("id") Long id);

    /**
     * 获取当前登录会员在当前商城中各状态订单的未读数量
     *
     * @param memberId
     * @param mallId
     * @return
     */
    HashMap<String,Object> getAllOrderIsNewCount(@Param("memberId") Long memberId,@Param("mallId") Long mallId);

    /**
     * 添加一条订单数据并返回id
     *
     * @param order
     */
    Integer insertAndReturnId(PtOrder order);
}