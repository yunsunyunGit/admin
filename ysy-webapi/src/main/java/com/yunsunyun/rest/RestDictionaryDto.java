package com.yunsunyun.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunsunyun.dictionary.entity.KnDictionary;
import com.yunsunyun.point.vo.MemberPointVo;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.service.PointUtil;
import com.yunsunyun.service.PtCatagoryService;
import com.yunsunyun.service.PtGoodsService;
import com.yunsunyun.service.PtRedemptionRecordService;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1")
public class RestDictionaryDto {

    private static Logger logger = LoggerFactory.getLogger(RestDictionaryDto.class);
    @Autowired
    private PtGoodsService goodsService;
    @Autowired
    private PtRedemptionRecordService recordService;
    @Autowired
    private PtCatagoryService catagoryService;
    /**
     * 获取首页推荐类型信息
     *
     * @param dicKey
     * @return
     */
    @RequestMapping(value = "/dictionary", method = RequestMethod.GET)
    public ResponseMap dictionary(String dicKey) {
        ResponseMap map = new ResponseMap();
        try {
            KnDictionary dictionary = goodsService.selectDictByKey(dicKey);
            if (dictionary != null) {
                map.setCode(200);
                map.setMsg("操作成功!");
                map.setData(dictionary);
            } else {
                map.setCode(500);
                map.setMsg("操作失败!");
                map.setData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("查询字典对象失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 登录认证接口
     *
     * @param userId
     * @param mallId
     * @param validateCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/appLogin", method = RequestMethod.GET)
    public ResponseMap appLogin(Long userId, Long mallId, String validateCode, HttpServletRequest request) {
        ResponseMap map = new ResponseMap();
        Subject user = SecurityUtils.getSubject();
        try {
            //参数飞空判断 start
            if (userId == null || userId <= 0) {
                map.setCode(500);
                map.setMsg("登录失败,请重试......");
                return map;
            }
            if (mallId == null || mallId <= 0) {
                map.setCode(500);
                map.setMsg("登录失败,请重试......");
                return map;
            }
            if (StringUtils.isEmpty(validateCode)) {
                map.setCode(500);
                map.setMsg("登录失败,请重试......");
                return map;
            }
            if (user.isAuthenticated()) {
                user.logout();
            }
            //参数非空判断 end
            if (!user.isAuthenticated()) {
                UsernamePasswordToken token = new UsernamePasswordToken(userId + "", validateCode, true);
                user.login(token);
            }
            Map<String, String> param = new HashMap<>();
            param.put("userId", userId + "");
            JSONArray userIntegral = PointUtil.getUserIntegral(param);

//            //判断请求接口是否有响应
            if (userIntegral != null) {
                List<MemberPointVo> list = new ArrayList<>();
                Double totalIntegral = 0d;
                for (int i = 0; i < userIntegral.size(); i++) {
                    MemberPointVo temp = new MemberPointVo(userIntegral.getJSONObject(i).getString("integralName"), userIntegral.getJSONObject(i).getDouble("integral"));
                    totalIntegral += temp.getIntegral();
                    list.add(temp);
                }
                HttpSession session = request.getSession();
                session.setAttribute("integralList", list);
                session.setAttribute("totalIntegral", totalIntegral);
                session.setAttribute("mallId", mallId);
                map.setCode(200);
                map.setData("{}");
                map.setMsg("操作成功！");
            } else {
                user.logout();
                map.setCode(500);
                map.setMsg("登录失败,请重试......");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            logger.error("操作人id：memberId——" + userId);
            logger.error("APP登录认证失败：errorMsg——" + e.getMessage());
            user.logout();
            map.setCode(500);
            map.setMsg("登录失败,请重试......");
        }
        return map;
    }

    /**
     * 获取当前登录会员信息
     *
     * @return
     */
    @RequestMapping(value = "/memberInfo", method = RequestMethod.GET)
    public ResponseMap getMemberInfo() {
        ResponseMap map = new ResponseMap();
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        try {
            Map<String, String> param = new HashMap<>();
            param.put("userId", user.getId() + "");
            JSONObject memberInfo = PointUtil.getUserInfo(param);
            if (memberInfo == null) {
                map.setCode(500);
                map.setMsg("操作失败!");
            } else {
                map.setCode(200);
                map.setMsg("操作成功!");
                map.setData(memberInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取会员信息失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 获取当前登录会员在当前商城中产生的积分消费记录
     *
     * @param date
     * @param session
     * @return
     */
    @RequestMapping(value = "/records",method = RequestMethod.GET)
    public ResponseMap getRecords(Integer pageSize,Integer pageNo,@RequestParam(required = false) String date, HttpSession session){
        ResponseMap map = new ResponseMap();
        try {
            map = recordService.ajaxRecordList(map,pageSize,pageNo,date,session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取会员信息失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }

    /**
     * 获取分类列表
     *
     * @param keyword
     * @param session
     * @return
     */
    @GetMapping(value = "/catagorys")
    public ResponseMap getCatagoryList(@RequestParam(required = false) String keyword,HttpSession session){
        ResponseMap map = new ResponseMap();
        try {
            map = catagoryService.ajaxDataList(map,keyword,session);
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取分裂列表失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }
}
