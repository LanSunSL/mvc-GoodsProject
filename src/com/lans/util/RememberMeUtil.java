package com.lans.util;

import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RememberMeUtil {
	private static final String REMEMBER_ME_TITLE = "menuuser";
	private HttpServletRequest request ;
	private HttpServletResponse response ;
	public RememberMeUtil(HttpServletRequest request, HttpServletResponse response) {
		this.request = request ;
		this.response = response ;
	}
	public void saveRemember(String mid, String password) {
		String str = mid + ":" + password ;
		String newStr = Base64.getEncoder().encodeToString(str.getBytes());
		Cookie c = new Cookie(REMEMBER_ME_TITLE, newStr);
		c.setMaxAge(864000);
		c.setPath(this.request.getContextPath());
		this.response.addCookie(c);
	}
	public String[] loadRemember() {
		Cookie [] cookies = this.request.getCookies();
		if (cookies != null) {
			for (int i = 0 ; i < cookies.length ; i ++) {
				if (REMEMBER_ME_TITLE.equalsIgnoreCase(cookies[i].getName())) {
					String str = cookies[i].getValue();
					String old = new String(Base64.getDecoder().decode(str.getBytes()));
					return old.split(":");
				}
			}
		}
		return null ;
	}
	public void clear(){
		Cookie c = new Cookie(REMEMBER_ME_TITLE,"") ;
		c.setMaxAge(0);
		c.setPath(this.request.getContextPath());
		this.response.addCookie(c);
	}
}
