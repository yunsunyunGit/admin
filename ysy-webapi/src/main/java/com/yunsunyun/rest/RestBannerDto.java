package com.yunsunyun.rest;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunsunyun.config.util.ConfigUtil;
import com.yunsunyun.point.po.PtBanner;
import com.yunsunyun.point.vo.ResponseMap;
import com.yunsunyun.xsimple.Setting;
import com.yunsunyun.service.PtBannerService;
import com.yunsunyun.shiro.PointShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/banners")
public class RestBannerDto {

    private static Logger logger = LoggerFactory.getLogger(RestBannerDto.class);
    @Autowired
    private PtBannerService bannerService;

    /**
     * 获取内容列表，本接口用于获取首页banner列表，首页icon列表。
     *
     * @param bannerType 字典表banner_type子项的dic_key
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseMap bannerList(String bannerType) {
        //响应实体类
        ResponseMap map = new ResponseMap();
        try {
            Page<PtBanner> page = new Page<>();
            if (StringUtils.isEmpty(bannerType)) {
                bannerType = "carousel_image";
            }
            //获取配置中心配置的首页显示数量
            Integer count = 0;
            if ("carousel_image".equals(bannerType)) {
                //banner轮播图数量
                count = Integer.parseInt(ConfigUtil.getConfig(Setting.ConfigKeys.BANNERSHOWCOUNT.name()).getValue());
            } else {
                count = 4;
            }
            page.setCurrent(1);
            page.setSize(count);
            page = bannerService.selectListByType(bannerType, page);
            map.setCode(200);
            map.setMsg("操作成功!");
            map.setTotalSize(count);
            map.setData(page.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            PointShiroUser user = (PointShiroUser) SecurityUtils.getSubject().getPrincipal();
            logger.error("操作人id：memberId——" + user.getId());
            logger.error("获取banner列表失败：errorMsg——" + e.getMessage());
            map.setCode(500);
            map.setMsg("操作失败!");
            map.setData(null);
        }
        return map;
    }
}
