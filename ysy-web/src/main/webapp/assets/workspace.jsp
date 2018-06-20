<%@ page contentType="application/x-javascript; charset=UTF-8" language="java"%>
<%
	// 1. 获得请求上下文信息
	String contextPath = request.getContextPath();
	String json = "{\"contextPath\":\"" + contextPath + "\"}";
%><%="workspace=" + json + ";" %>