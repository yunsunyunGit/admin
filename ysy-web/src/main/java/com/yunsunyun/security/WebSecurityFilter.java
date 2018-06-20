package com.yunsunyun.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

public class WebSecurityFilter implements Filter, Constants {

	private static Logger logger = LoggerFactory.getLogger(WebSecurityFilter.class);
	
    /**
     * Default character encoding to use when <code>request.getCharacterEncoding</code>
     * returns <code>null</code>, according to the Servlet spec.
     *
     * @see javax.servlet.ServletRequest#getCharacterEncoding
     */
    private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

	private WebSecurityConfig xssSecurityConfig = new WebSecurityConfig();

	/**
	 * 初始化操作
	 */
	public void init(FilterConfig config) throws ServletException {
		logger.info("XSSSecurityManager init(FilterConfig config) begin");
		// 初始化过滤配置文件
		String configFilePath = config.getServletContext().getRealPath("/") + config.getInitParameter("securityconfig");
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(configFilePath));
			String isCheckHeader = prop.getProperty(IS_CHECK_HEADER);
			String isCheckParameter = prop.getProperty(IS_CHECK_PARAMETER);
			String isLog = prop.getProperty(IS_LOG);
			String isChain = prop.getProperty(IS_CHAIN);
			String replace = prop.getProperty(REPLACE);
			String errorPage = prop.getProperty(ERROR_PAGE);
			String csrfWhiteList = prop.getProperty(CSRF_WHITE_LIST);
			String regex = "";
			StringBuffer tempString = new StringBuffer("^");
			for (Object key : prop.keySet()) {
				if (key != null && key.toString().startsWith(REGEX_PREFIX)) {
					String v = prop.getProperty(key.toString());
					tempString.append(v);
					tempString.append("|");
				}
			}
			if (tempString.charAt(tempString.length() - 1) == '|') {
				regex = tempString.substring(0, tempString.length() - 1) + "$";
				logger.info("安全匹配规则" + regex);
			} else {
				regex = tempString.toString();
				logger.error("安全过滤配置文件加载失败:正则表达式异常 " + tempString.toString());
			}

			xssSecurityConfig.setCheckHeader(new Boolean(isCheckHeader));
			xssSecurityConfig.setCheckParameter(new Boolean(isCheckParameter));
			xssSecurityConfig.setLog(new Boolean(isLog));
			xssSecurityConfig.setChain(new Boolean(isChain));
			xssSecurityConfig.setReplace(new Boolean(replace));
			xssSecurityConfig.setRegex(regex);
			xssSecurityConfig.setErrorPage(errorPage);
			xssSecurityConfig.setCsrfWhiteList(csrfWhiteList);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 安全审核 读取配置信息
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 判断是否使用HTTP
		checkRequestResponse(request, response);
		// 转型
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		// 从 HTTP 头中取得 Referer 值
		String referer = httpRequest.getHeader("Referer");
		// 判断 Referer 是否以 bank.example 开头
		String basePath = request.getScheme() + "://" + request.getServerName();
		int serverPort = request.getServerPort();
		if (serverPort != 80) {
			basePath += ":" + request.getServerPort();
		}
		
		if (validateCSRF(referer, basePath)) {
			throw new ServletException("请求非法");
		}
		
		// http信息封装类
		WebHttpRequestWrapper xssRequest = new WebHttpRequestWrapper(httpRequest);
		xssRequest.setXssSecurityConfig(xssSecurityConfig);

		// 对request信息进行封装并进行校验工作，若校验失败（含非法字符），根据配置信息进行日志记录和请求中断处理
		if (xssRequest.validateParameter()) {
			if (xssSecurityConfig.isChain()) {
				String msg = "非法操作，不允许继续操作";
				String contentType = httpRequest.getParameter("Content-Type");// 自定义请求会加上这个参数
				if (contentType != null && contentType.toLowerCase().contains("json")) {
            		String result = String.format("{\"errorCode\": %d, \"msg\":\"%s\"}", 200, msg);
            		jsonOut(httpResponse, result);
				} else {
					String errorPage = xssSecurityConfig.getErrorPage();
					if (StringUtils.isBlank(errorPage)) {
						throw new ServletException(msg);
					} else {
						httpRequest.getRequestDispatcher(errorPage).forward(httpRequest, httpResponse);
					}
				}
				return;
			}
		}
		chain.doFilter(xssRequest, response);
	}

	/**
	 * 验证SRF攻击模式 
	 * @param referer
	 * @param basePath
	 * @return
	 */
	private boolean validateCSRF(String referer, String basePath) {
		boolean result = referer != null && !referer.trim().startsWith(basePath);
		String whiteList = xssSecurityConfig.getCsrfWhiteList();
		if (!result && whiteList != null && whiteList.trim().length() > 0) {
			Pattern csrfPattern = Pattern.compile(whiteList);
			return !csrfPattern.matcher(referer.toLowerCase()).matches();
		}
		return result;
	}

	/**
	 * 销毁操作
	 */
	public void destroy() {
	}

	/**
	 * 判断Request ,Response 类型
	 * 
	 * @param request
	 *            ServletRequest
	 * @param response
	 *            ServletResponse
	 * @throws ServletException
	 */
	private void checkRequestResponse(ServletRequest request, ServletResponse response) throws ServletException {
		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("Can only process HttpServletRequest");

		}
		if (!(response instanceof HttpServletResponse)) {
			throw new ServletException("Can only process HttpServletResponse");
		}
	}
    
    /**
	 * 以JSON格式输出
	 * @param response
	 */
	protected void jsonOut(HttpServletResponse response, String body) {
		//将实体对象转换为JSON Object转换
		response.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(body);
			logger.debug("返回是\n");
			logger.debug(body);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
