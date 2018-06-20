package com.yunsunyun.rest;

import com.yunsunyun.point.mapper.PtAddressMapper;
import com.yunsunyun.point.po.PtAddress;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.service.PtAddressService;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/v1/address")
public class RestAddressDto {

    private static Logger logger = LoggerFactory.getLogger(RestAddressDto.class);

    @Autowired
    private PtAddressService addressService;
    @Autowired
    private PtAddressMapper addressMapper;

    /**
     * 获取当前登录会员的收货地址列表
     *
     * @param pageSize
     * @param pageNo
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseMap ajaxData(Integer pageSize, Integer pageNo) {
        ResponseMap map = new ResponseMap();
        try {
            map = addressService.ajaxAddressList(pageSize, pageNo, map);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取收货地址列表失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 根据id查询收货地址详细信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseMap getAddress(@PathVariable("id") Long id) {
        ResponseMap map = new ResponseMap();
        try {
            map = addressService.getAddressById(map, id);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("查询收货地址信息失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 新增收货地址
     *
     * @param address
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMap create(PtAddress address) {
        ResponseMap map = new ResponseMap();
        try {
            map = addressService.createAddress(address, map);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("创建新收货地址失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 修改收货地址
     *
     * @param address
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseMap update(PtAddress address) {
        ResponseMap map = new ResponseMap();
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        try {
            address.setMemberId(user.getId());
            address.setIsDefult(0);
            address.setIsDeleted(0);
            map = addressService.updateAddressById(address, map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("修改收货地址失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 设置默认收货地址
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseMap setDefault(@PathVariable("id") Long id) {
        ResponseMap map = new ResponseMap();
        try {
            map = addressService.setDefaultAddress(id, map);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("设置默认收货地址失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 会员逻辑删除收货地址
     *
     * @param id
     * @return
     */
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseMap delete(@PathVariable("id") Long id) {
        ResponseMap map = new ResponseMap();
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        try {
            if (id == null || id <= 0) {
                map.setCode(500);
                map.setMsg("收货地址id不存在!");
                return map;
            }
            PtAddress address = addressService.selectById(id);
            if (address.getIsDefult() == 1) {
                map.setCode(500);
                map.setMsg("默认地址不允许删除!");
                return map;
            }
            Integer result = addressMapper.softDelete(id);
            if (result > 0) {
                map.setCode(200);
                map.setMsg("操作成功!");
            } else {
                map.setCode(500);
                map.setMsg("操作失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("删除收货地址失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 取消默认地址
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancelDefault/{id}", method = RequestMethod.PUT)
    public ResponseMap cancelDefault(@PathVariable("id") Long id) {
        ResponseMap map = new ResponseMap();
        try {
            if (id == null || id <= 0) {
                map.setCode(500);
                map.setMsg("取消默认地址失败,请刷新后重试!");
                return map;
            }
            addressMapper.cannelDefaultById(id);
            map.setCode(200);
            map.setMsg("操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("取消默认收货地址失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }
}
