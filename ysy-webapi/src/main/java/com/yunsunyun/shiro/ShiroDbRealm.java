package com.yunsunyun.shiro;

import com.alibaba.fastjson.JSONObject;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.xsimple.dao.log.KnLogDao;
import com.yunsunyun.xsimple.entity.log.KnOperationLog;
import com.yunsunyun.xsimple.entity.system.KnRole;
import com.yunsunyun.xsimple.entity.system.KnUser;
import com.yunsunyun.xsimple.service.system.ResourceService;
import com.yunsunyun.xsimple.util.dete.DateUtil;
import com.yunsunyun.service.PointUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShiroDbRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
    protected ResourceService resourceService;
    private KnLogDao logDao;

    @Autowired
    public void setLogDao(KnLogDao logDao) {
        this.logDao = logDao;
    }

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        Map<String,String> param = new HashMap<>();
        param.put("userId",userToken.getUsername());
        param.put("validateCode",String.valueOf(userToken.getPassword()));
        JSONObject result = PointUtil.appLogin(param);
        if (result != null) {
            Long id = Long.parseLong(userToken.getUsername());
            String phoneNum = result.getString("phoneNum");
            String name = result.getString("name");
            SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo(new PointShiroUser(id, phoneNum, name), userToken.getPassword(), getName());
            return auth;
        } else {
            throw new UnknownAccountException();
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        for(KnRole role : resourceService.CacheRoles(1l)){
            // 基于Role的权限信息
            info.addRole(role.getCode());
            // 基于Permission的权限信息
            info.addStringPermissions(role.getPermissionList());
            info.addStringPermissions(role.getButtonPermissionList());
        }
        return info;
    }
//
//    /**
//     * 设定Password校验的Hash算法与迭代次数.
//     */
//    @PostConstruct
//    public void initCredentialsMatcher() {
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ResourceService.HASH_ALGORITHM);
//        matcher.setHashIterations(ResourceService.HASH_INTERATIONS);
//        setCredentialsMatcher(matcher);
//    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 记录登录日志
     *
     * @param @param user
     * @return void
     * @throws
     * @Title: saveLog
     * @author Jason
     */
    public void saveLog(KnUser user) {

        KnOperationLog log = new KnOperationLog();
        log.setOperationType(Setting.LogOperationType.login.name());//删除设备
        log.setOptionTime(System.currentTimeMillis());
        log.setOptionId(user.getId());
        log.setOptionName(user.getName());
        StringBuffer content = new StringBuffer();
        String ip = SecurityUtils.getSubject().getSession().getHost();
        content.append("来自IP为[").append(ip).append("]的用户");
        content.append("[").append(user.getName()).append("]");
        content.append("在[").append(DateUtil.getInstance().getDateStr(new Date(), "yyyy-MM-dd HH:mm")).append("]做了[");
        content.append(Setting.LogOperationType.login.getOption_type()).append("]").append("行为");
        log.setContent(content.toString());
        logDao.save(log);
    }
}