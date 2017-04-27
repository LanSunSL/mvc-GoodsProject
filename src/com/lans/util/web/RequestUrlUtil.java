package com.lans.util.web;

import javax.servlet.http.HttpServletRequest;

public class RequestUrlUtil {
	private RequestUrlUtil(){}
	public static String[] requestUri(HttpServletRequest request) {
		String uri = request.getRequestURI().replaceFirst(request.getContextPath(), "");
		String[] temp = uri.split("!");
		String[] result = new String[] {temp[0], temp[1].substring(0, temp[1].indexOf("."))};
		return result ;
	}
	
	public static String getActionName(HttpServletRequest request) {
		String uri = request.getRequestURI();
		return uri.substring(uri.lastIndexOf("/") + 1 , uri.lastIndexOf("."));
	}
}
