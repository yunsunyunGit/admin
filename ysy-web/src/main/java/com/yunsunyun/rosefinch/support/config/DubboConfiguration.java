package com.yunsunyun.rosefinch.support.config;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ProtocolConfig;
/**
 * dubbo配置类
 * 解决dubbo优雅停机的问题
 * 详情请参考：http://dubbo.io/User+Guide-zh.htm#UserGuide-zh-%E4%BC%98%E9%9B%85%E5%81%9C%E6%9C%BA
 * @author Wayen
 *
 */
@Configuration
public class DubboConfiguration {
	
	@PreDestroy
	public void shutdownDestroy() {
		ProtocolConfig.destroyAll();
	}
}
