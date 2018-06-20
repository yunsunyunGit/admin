package com.yunsunyun.rest;

import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.service.PtCartService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/carts")
public class RestCartDto {

    private Logger logger = LoggerFactory.getLogger(RestCartDto.class);

    @Autowired
    private PtCartService cartService;

    /**
     * 获取当前登录用户在当前商城中的购物车列表
     *
     * @param pageSize
     * @param pageNo
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseMap getCartList(Integer pageSize,Integer pageNo,HttpSession session){
        ResponseMap map = new ResponseMap();
        try {
            map = cartService.ajaxCartList(pageSize,pageNo,session,map);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取购物车列表失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 获取购物车商品总数量
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/total", method = RequestMethod.GET)
    public ResponseMap getCartTotal(HttpSession session) {
        ResponseMap map = new ResponseMap();
        try {
            map = cartService.getCartTotalByUserId(map, session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取购物车商品总数失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 将商品添加进购物车
     *
     * @param goodsId
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMap add(Long goodsId, HttpSession session) {
        ResponseMap map = new ResponseMap();
        try {
            map = cartService.addToCartByGoodsId(goodsId, map, session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("加入购物车失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 根据购物车id修改商品数量
     * @param id
     * @param goodsNum
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseMap updateNum(@PathVariable(value = "id") Long id, Integer goodsNum) {
        ResponseMap map = new ResponseMap();
        try {
            if (goodsNum == null || goodsNum <= 0){
                map.setCode(500);
                map.setMsg("修改数量不能为空!");
                return map;
            }
            map = cartService.updateNumById(id, map, goodsNum);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("修改购物车数量失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 购物车提交订单
     *
     * @param session
     * @param addressId
     * @param cartIds
     * @return
     */
    @RequestMapping(value = "/settle",method = RequestMethod.POST)
    public ResponseMap settleCart(HttpSession session,Long addressId,Long ... cartIds){
        ResponseMap map = new ResponseMap();
        try {
            if(cartIds == null || cartIds.length <= 0){
                map.setCode(500);
                map.setMsg("未选择商品");
                return map;
            }
            map = cartService.settleCarts(map,session,addressId,cartIds);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("订单结算失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 删除购物车记录
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    public ResponseMap deletCart(Long ... ids){
        ResponseMap map = new ResponseMap();
        try {
            if (ids == null || ids.length <= 0){
                map.setCode(404);
                map.setMsg("所选择的记录不存在!");
                return map;
            }
            List<Long>  idsList = Arrays.asList(ids);
            boolean result = cartService.deleteBatchIds(idsList);
            if (result) {
                map.setCode(200);
                map.setMsg("操作成功!");
            } else {
                map.setCode(500);
                map.setMsg("操作失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("删除购物车失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }


    /**
     * 删除购物车记录
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseMap deletCart(@PathVariable("id")Long id){
        ResponseMap map = new ResponseMap();
        try {
            if (id == null || id <= 0){
                map.setCode(404);
                map.setMsg("所选择的记录不存在!");
                return map;
            }
            boolean result = cartService.deleteById(id);
            if (result) {
                map.setCode(200);
                map.setMsg("操作成功!");
            } else {
                map.setCode(500);
                map.setMsg("操作失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("删除购物车失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 根据购物车id获取商品信息
     *
     * @param session
     * @param ids
     * @return
     */
    @RequestMapping(value = "/array",method = RequestMethod.GET)
    public ResponseMap getCartInfoByIds(HttpSession session,Long ... ids){
        ResponseMap map = new ResponseMap();
        try {
            if (ids == null || ids.length <= 0){
                map.setCode(500);
                map.setMsg("查询不到数据!");
                return map;
            }
            map = cartService.getCartInfoByIds(map,session,ids);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("根据购物车id获取商品信息失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }
}
