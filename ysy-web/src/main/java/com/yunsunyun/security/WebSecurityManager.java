package com.yunsunyun.security;

import java.util.regex.Pattern;

public class WebSecurityManager implements Constants {

	private WebSecurityManager() {
	}

	/**
	 * 对非法字符进行替换
	 * 
	 * @param text
	 * @return
	 */
	public static String securityReplace(String text, String regex) {
		if (isNullStr(text)) {
			return text;
		} else {
			return text.replaceAll(regex, REPLACEMENT);
		}
	}

	/**
	 * 匹配字符是否含特殊字符
	 * 
	 * @param text
	 * @return
	 */
	public static boolean matches(String text, String regex) {
		if (text == null) {
			return false;
		}
		Pattern xssPattern = Pattern.compile(regex);
		return xssPattern.matcher(text.toLowerCase()).matches();
	}

	/**
	 * 判断是否为空串，建议放到某个工具类中
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullStr(String value) {
		return value == null || value.trim().equals("");
	}
	
	public static void main(String[] args) {
		String text = "签到模块</textarea><script type='text/javascript'>alert(\"跨站脚本\");</script><textarea";
		text = "签到模块</textarea><script type='text/javascript'>alert(\"跨站脚本\");</script><textarea class=\"form-control\" style=\"resize:none;\" rows=\"3\" name=\"description\" value=\"签到模块\" id=\"description\">";
		text = "签到模块</textarea><script type='text/javascript'></script><textarea";
		String regex = ".*alert\\s*\\(.*\\).*|.*window\\.location\\s*=.*|.*style\\s*=.*xex.*pres{1,2}ion\\s*\\(.*\\).*|.*document\\.cookie.*|.*eval\\s*\\(.*\\).*|.*unescape\\s*\\(.*\\).*|.*execscript\\s*\\(.*\\).*|.*msgboxs\\s*\\(.*\\).*]|.*confirm\\s*\\(.*\\).*|.*prompt\\s*\\(.*\\).*|.*<script.*>.*</script>.*|.&[^\"]]*\"[.&[^\"]]*|.&[^']]*'[.&[^']]*|.&[^a]]|[|a|\n|\r\n|\r|| | ]]*<script>.*</script>[[.&[^a]]|[|a|\n|\r\n|\r|| | ]]*";
		boolean m = WebSecurityManager.matches(text, regex);
		System.out.println(m);
		System.out.println(regex);
	}
}