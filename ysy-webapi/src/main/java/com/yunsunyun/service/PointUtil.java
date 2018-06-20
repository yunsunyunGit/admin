package com.yunsunyun.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunsunyun.config.util.ConfigUtil;
import com.yunsunyun.point.vo.MemberPointVo;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.mxm.util.HttpClientUtil;
import com.yunsunyun.xsimple.util.client.HttpUtil;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PointUtil {

    private static Logger logger = LoggerFactory.getLogger(PointUtil.class);

    /**
     * 小碗健康APP端登录接口请求
     *
     * @param map
     * @return
     */
    public static JSONObject appLogin(Map<String,String> map){
        String appUrl = ConfigUtil.get(Setting.ConfigKeys.app_login_url.name());
        String resString = sendPost(appUrl + Setting.AppApiUrl.login.getUrl(),buildParam(map));
        return analyseJSONString(resString,"data");
    }

    /**
     * 小碗健康APP端获取用户信息请求
     *
     * @param map
     * @return
     */
    public static JSONObject getUserInfo(Map<String,String> map){
        String appUrl = ConfigUtil.get(Setting.ConfigKeys.app_login_url.name());
        String resString = sendPost(appUrl + Setting.AppApiUrl.getUserInfo.getUrl(),buildParam(map));
        return analyseJSONString(resString,"data");
    }

    /**
     * 小碗健康APP端获取用户积分请求
     *
     * @param map
     * @return
     */
    public static JSONArray getUserIntegral(Map<String,String> map){
        String appUrl = ConfigUtil.get(Setting.ConfigKeys.app_login_url.name());
        String resString = sendPost(appUrl + Setting.AppApiUrl.getUserIntegral.getUrl(),buildParam(map));
        return analyseJSONArray(resString,"data");
    }

    /**
     * 小碗健康APP端修改用户积分请求
     *
     * @param map
     * @return
     */
    public static JSONObject updateUserIntegral(Map<String,Object> map) throws Exception {
        String appUrl = ConfigUtil.get(Setting.ConfigKeys.app_login_url.name());
        String resString = HttpUtil.sendHttpUrlRequestByJson(appUrl + Setting.AppApiUrl.updateUserIntegral.getUrl(),JSONObject.toJSONString(map),"post");
        return analyseJSONString(resString,"");
    }

    /**
     * 设置修改会员积分的积分数组
     *
     * @param orderGoodsPoint
     * @param integralList
     * @return
     */
    public static List<MemberPointVo> setIntegral(Double orderGoodsPoint, List<MemberPointVo> integralList) {
        List<MemberPointVo> updateIntegralList = new ArrayList<>();
        Double residue = integralList.get(0).getIntegral() - orderGoodsPoint;
        if (residue < 0) {
            Double temp = residue;
            residue = integralList.get(1).getIntegral() + residue;
            if (residue < 0) {
                Double temp1 = residue;
                residue = integralList.get(2).getIntegral() + residue;
                if (residue < 0) {
                    updateIntegralList.add(new MemberPointVo("transferPoint", -integralList.get(0).getIntegral()));
                    updateIntegralList.add(new MemberPointVo("rebatePoint", -integralList.get(1).getIntegral()));
                    updateIntegralList.add(new MemberPointVo("ordinaryPoint", -integralList.get(2).getIntegral()));
                    updateIntegralList.add(new MemberPointVo("vipPoint", residue));
                } else {
                    updateIntegralList.add(new MemberPointVo("transferPoint", -integralList.get(0).getIntegral()));
                    updateIntegralList.add(new MemberPointVo("rebatePoint", -integralList.get(1).getIntegral()));
                    updateIntegralList.add(new MemberPointVo("ordinaryPoint", temp1));
                }
            } else {
                updateIntegralList.add(new MemberPointVo("transferPoint", -integralList.get(0).getIntegral()));
                updateIntegralList.add(new MemberPointVo("rebatePoint", temp));
            }
        } else {
            updateIntegralList.add(new MemberPointVo("transferPoint", -orderGoodsPoint));
        }

        List<MemberPointVo> modifyIntegralList = new ArrayList<>();

        for (MemberPointVo point : updateIntegralList){
            if (0 != Math.abs(point.getIntegral())){
                modifyIntegralList.add(point);
            }
        }
        return modifyIntegralList;
    }

    /**
     * 更新session中会员积分信息
     *
     * @param session
     */
    public static void updateSession(HttpSession session) {
        PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
        Map<String, String> param = new HashMap<>();
        param.put("userId", user.getId() + "");
        JSONArray userIntegral = PointUtil.getUserIntegral(param);
        List<MemberPointVo> list = new ArrayList<>();
        Double totalIntegral = 0d;
        for (int i = 0; i < userIntegral.size(); i++) {
            MemberPointVo temp = new MemberPointVo(userIntegral.getJSONObject(i).getString("integralName"), userIntegral.getJSONObject(i).getDouble("integral"));
            totalIntegral += temp.getIntegral();
            list.add(temp);
        }
        session.setAttribute("integralList", list);
        session.setAttribute("totalIntegral", totalIntegral);
    }


    /******************  公用方法  **************************/

    /**
     * post请求构造参数
     *
     * @param map
     * @return
     */
    private static ArrayList<NameValuePair> buildParam(Map<String,String> map){
        ArrayList<NameValuePair> paramMap = new ArrayList<>();
        if(map!= null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                paramMap.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return paramMap;
    }

    /**
     * json字符串解析方法
     *
     * @param resString
     * @param key
     * @return
     */
    private static JSONObject analyseJSONString(String resString,String key){
        JSONObject result = null;
        if (resString != null) {
            JSONObject obj = JSONObject.parseObject(resString);
            if (obj.getInteger("code") == 200) {
                if(StringUtils.isNotEmpty(key)) {
                    result = obj.getJSONObject(key);
                }else {
                    result = obj;
                }
            }
            logger.info("小碗健康App接口返回："+resString);
        }
        return result;
    }

    private static JSONArray analyseJSONArray(String resString, String key){
        JSONArray result = null;
        if (resString != null) {
            JSONObject obj = JSONObject.parseObject(resString);
            if (obj.getInteger("code") == 200) {
                if(StringUtils.isNotEmpty(key)) {
                    result = obj.getJSONArray(key);
                }else {
                    result = null;
                }
            }
            logger.info("小碗健康App接口返回："+resString);
        }
        return result;
    }

    /**
     * 发送请求
     *
     * @param url
     * @param param
     * @return
     */
    private static String sendPost(String url, ArrayList<NameValuePair> param){
        String resString = HttpClientUtil.Post(url, param);
        return resString;
    }
}
