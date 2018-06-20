package com.yunsunyun.rest;

import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.service.PtTipsService;
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
@RequestMapping("/v1/tips")
public class RestTipsDto {

    private static Logger logger = LoggerFactory.getLogger(RestTipsDto.class);

    @Autowired
    private PtTipsService tipsService;

    /**
     * 分页查询当前登录用户在当前商城所接受的消息列表
     *
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseMap list(Integer pageSize,Integer pageNo,HttpSession session) {
        ResponseMap map = new ResponseMap();
        try {
            map = tipsService.listByMallIdAndMemberId(pageSize,pageNo,map,session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("查询消息列表失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 查看消息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseMap readTip(@PathVariable("id") Long id){
        ResponseMap map = new ResponseMap();
        try {
            map = tipsService.readTipById(map,id);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("查看消息失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 查看当前登录会员在当前商城中的新消息数量
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/isNewCount",method = RequestMethod.GET)
    public ResponseMap getIsNewCount(HttpSession session){
        ResponseMap map = new ResponseMap();
        try {
            map = tipsService.getIsNewCountByMemberId(map,session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取新消息数量失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 会员逻辑删除消息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseMap deleteTip(@PathVariable("id") Long id){
        ResponseMap map = new ResponseMap();
        try {
            map = tipsService.memberDeleteTipById(map,id);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("会员逻辑删除消息失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }
}
