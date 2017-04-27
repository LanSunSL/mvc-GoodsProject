package com.lans.util.web;

import java.lang.reflect.Method;

import com.lans.util.message.MessageUtil;

public class ActionBeanUtil {
	private static final String ACTION_BASENAME = "com.lans.conf.action";
	private static MessageUtil actionMU = new MessageUtil(ACTION_BASENAME);

	public static ModelAndView actionHandle(String[] urlResult) throws Exception {
		String className = actionMU.getValue(urlResult[0]);
		Class<?> actionClass = Class.forName(className);
		Object actionObj = actionClass.newInstance();   //GoodsAction实例化
		Method actionMet = MethodUtil.getMethod(actionClass, urlResult[1]); 
		Object retObj = null ;
		if (actionMet.getParameterTypes().length == 0) {
			retObj = actionMet.invoke(actionObj);
		} else {
			Object[] methodParams = ParameterValueUtil.getMethodParameter(actionClass, actionMet);
			retObj = actionMet.invoke(actionObj, methodParams) ;
		}
		if (retObj instanceof ModelAndView) {
			return (ModelAndView) retObj;
		}
		return null;
	}
	public static String actionHandler(String[] urlResult) throws Exception {
		String className = actionMU.getValue(urlResult[0]);
		Class<?> actionClass = Class.forName(className);
		Object actionObj = actionClass.newInstance();   //GoodsAction实例化
		Method actionMet = MethodUtil.getMethod(actionClass, urlResult[1]);  
		Object retObj = null ;
		if (actionMet.getParameterTypes().length == 0) {
			retObj = actionMet.invoke(actionObj);
		} else {
			Object[] methodParams = ParameterValueUtil.getMethodParameter(actionClass, actionMet);
			retObj = actionMet.invoke(actionObj, methodParams) ;
		}
		if (retObj instanceof ModelAndView) {
			return ((ModelAndView) retObj).getUrl();
		}
		if (retObj instanceof String) {
			return retObj.toString() ;
		}
		return null;
	}
}
