package com.lans.util.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletContextApplicationUtil {
	private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return requestThreadLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestThreadLocal.set(request);
	}

	public static HttpServletResponse getResponse() {
		return responseThreadLocal.get();
	}

	public static void setResponse(HttpServletResponse response) {
		responseThreadLocal.set(response);
	}

	public static HttpSession getSession() {
		return requestThreadLocal.get().getSession();
	}

	public static ServletContext getServletContext() {
		return requestThreadLocal.get().getServletContext();
	}
	
	public static void clear() {
		requestThreadLocal.remove();
		responseThreadLocal.remove();
	}
}
