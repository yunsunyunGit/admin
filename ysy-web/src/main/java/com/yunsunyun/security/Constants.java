package com.yunsunyun.security;

public interface Constants {

	/**
	 * 配置文件标签 isCheckHeader
	 */
	public static String IS_CHECK_HEADER = "isCheckHeader";

	/**
	 * 配置文件标签 isCheckParameter
	 */
	public static String IS_CHECK_PARAMETER = "isCheckParameter";

	/**
	 * 配置文件标签 isLog
	 */
	public static String IS_LOG = "isLog";

	/**
	 * 配置文件标签 isChain
	 */
	public static String IS_CHAIN = "isChain";

	/**
	 * 配置文件标签 replace
	 */
	public static String REPLACE = "replace";

	/**
	 * 配置文件标签 regex
	 */
	public static String REGEX_PREFIX = "regexlist.regex.";

	/**
	 * 替换非法字符的字符串
	 */
	public static String REPLACEMENT = "";

	/**
	 * ERROR_PAGE:过滤后错误页面
	 */
	public static String ERROR_PAGE = "errorPage";
	
	/**
	 * CSRF_WHITE_LIST:CSRF验证的白名单
	 */
	public static String CSRF_WHITE_LIST = "csrf.regex.whitelist";

}