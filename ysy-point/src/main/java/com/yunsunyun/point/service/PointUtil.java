package com.yunsunyun.point.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunsunyun.config.util.ConfigUtil;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.mxm.util.HttpClientUtil;
import com.yunsunyun.xsimple.util.client.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    public static JSONObject updateUserIntegral(Map<String,Object> map) throws  Exception{
        String appUrl = ConfigUtil.get(Setting.ConfigKeys.app_login_url.name());
        String resString =HttpUtil.sendHttpUrlRequestByJson(appUrl + Setting.AppApiUrl.updateUserIntegral.getUrl(),JSONObject.toJSONString(map),"post");
        return analyseJSONString(resString,"");
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
