package com.lans.util.message;

import java.util.ResourceBundle;

public class MessageUtil {
	private ResourceBundle resource = null ;
	public MessageUtil(String baseName) {
		this.resource = ResourceBundle.getBundle(baseName) ;
	}
	public String getValue(String key) {
		return this.resource.getString(key) ; 
	}
}
