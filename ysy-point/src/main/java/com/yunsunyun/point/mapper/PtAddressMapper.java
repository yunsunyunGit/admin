package com.yunsunyun.point.mapper;

import com.yunsunyun.point.po.PtAddress;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 收货地址表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-03-30
 */
public interface PtAddressMapper extends BaseMapper<PtAddress> {

    /**
     * 取消会员默认收货地址
     *
     * @param id
     */
    void cannelDefault(@Param("id") Long id);

    Integer updateEntityById(PtAddress address);

    /**
     * 根据id取消默认收货地址
     *
     * @param id
     */
    void cannelDefaultById(@Param("id") Long id);

    /**
     * 设置默认会员地址
     *
     * @param address
     * @return
     */
    Integer setDefault(PtAddress address);

    /**
     * 软删除收货地址
     *
     * @param id
     * @return
     */
    Integer softDelete(@Param("id") Long id);
}