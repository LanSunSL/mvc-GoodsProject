package com.lans.util.factory;

import com.lans.util.message.MessageUtil;
import com.lans.util.proxy.ServiceProxy;

public class Factory {
	private final static MessageUtil DAO_MESSAGE = new MessageUtil("com.lans.util.config.dao") ;
	private final static MessageUtil SERVICE_MESSAGE = new MessageUtil("com.lans.util.config.service") ;
	@SuppressWarnings("unchecked")
	public static <T> T getDAOInstance(String daoName) {
		String className = DAO_MESSAGE.getValue(daoName)  ;
		T t = null ;
		try {
			t = (T) Class.forName(className).newInstance() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t ;
	} 
	@SuppressWarnings("unchecked")
	public static <T> T getServiceInstance(String serviceName) {
		String className = SERVICE_MESSAGE.getValue(serviceName) ;
		T t = null ;
		try {
			t = (T) new ServiceProxy().bind(Class.forName(className).newInstance()) ;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return t ; 
	}
}
