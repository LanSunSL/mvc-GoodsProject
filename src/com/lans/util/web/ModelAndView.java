package com.lans.util.web;

import java.util.Iterator;
import java.util.Map;

public class ModelAndView {
	private String url;

	public ModelAndView() {
	}

	public ModelAndView(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void add(String key, Object value) {
		ServletContextApplicationUtil.getRequest().setAttribute(key, value);
	}
	
	public void add(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			ServletContextApplicationUtil.getRequest().setAttribute(entry.getKey(), entry.getValue());
		}
	}
}
