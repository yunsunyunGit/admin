package com.yunsunyun.rest;

import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.service.PtOrdersService;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/v1/orders")
public class RestOrdersDto {

    private static Logger logger = LoggerFactory.getLogger(RestOrdersDto.class);

    @Autowired
    private PtOrdersService ordersService;

    /**
     * 分页查询当前登录会员，所在商城的订单列表
     *
     * @param orderStatus （-1:全部;0:待发货;1:已发货;2:已完成;3:已取消;）
     * @param pageSize
     * @param pageNo
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseMap list(Integer orderStatus, Integer pageSize, Integer pageNo, HttpSession session) {
        ResponseMap map = new ResponseMap();
        try {
            map = ordersService.ajaxDataByStatus(map, orderStatus, pageSize, pageNo, session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取订单列表失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
        }
        return map;
    }

    /**
     * 会员前端页面确认收货
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseMap sign(@PathVariable(value = "id") Long id) {
        ResponseMap map = new ResponseMap();
        try {
            map = ordersService.signOrderById(map,id);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("确认收货失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
        }
        return map;
    }

    /**
     * 查看订单详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseMap orderInfo(@PathVariable(value = "id") Long id){
        ResponseMap map = new ResponseMap();
        try {
            map = ordersService.getOrderInfoById(map,id);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("查看订单详情失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
        }
        return map;
    }

    /**
     * 获取当前登录会员在当前商城中各状态订单的未读数量
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/isNewCount", method = RequestMethod.GET)
    public ResponseMap newOrderCount(HttpSession session){
        ResponseMap map = new ResponseMap();
        try {
            map = ordersService.getIsNewCount(map,session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取未读订单数量失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 当前登录会员逻辑删除订单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseMap delete(@PathVariable(value = "id") Long id){
        ResponseMap map = new ResponseMap();
        try {
            map = ordersService.memberDeleteOrder(map,id);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("逻辑删除订单失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }
}
