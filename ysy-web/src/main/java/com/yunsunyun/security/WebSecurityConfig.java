package com.yunsunyun.security;

public class WebSecurityConfig {

	/**
	 * 是否开启header校验
	 */
	private boolean isCheckHeader;

	/**
	 * 是否开启parameter校验
	 */
	private boolean isCheckParameter;

	/**
	 * 是否记录日志
	 */
	private boolean isLog;

	/**
	 * 是否中断操作
	 */
	private boolean isChain;

	/**
	 * 是否开启替换
	 */
	private boolean replace;
	
	/**
	 * 校验正则表达式
	 */
	private String regex;

	/**
	 * 错误页面
	 */
	private String errorPage;
	
	/**
	 * 错误页面
	 */
	private String csrfWhiteList;

	public boolean isCheckHeader() {
		return isCheckHeader;
	}

	public void setCheckHeader(boolean isCheckHeader) {
		this.isCheckHeader = isCheckHeader;
	}

	public boolean isCheckParameter() {
		return isCheckParameter;
	}

	public void setCheckParameter(boolean isCheckParameter) {
		this.isCheckParameter = isCheckParameter;
	}

	public boolean isLog() {
		return isLog;
	}

	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}

	public boolean isChain() {
		return isChain;
	}

	public void setChain(boolean isChain) {
		this.isChain = isChain;
	}

	public boolean isReplace() {
		return replace;
	}

	public void setReplace(boolean replace) {
		this.replace = replace;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getCsrfWhiteList() {
		return csrfWhiteList;
	}

	public void setCsrfWhiteList(String csrfWhiteList) {
		this.csrfWhiteList = csrfWhiteList;
	}

}