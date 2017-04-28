package com.lans.util.web;

import java.lang.reflect.Method;

/**
 * 方法处理类
 * 
 * @author Lan
 *
 */
public class MethodUtil {
	/**
	 * 取得指定类的指定方法名称的方法，并不确定方法的参数类型（若已知方法名，方法参数类型，则可直接调用反射中的getMethod方法）
	 * 
	 * @param cls
	 *            方法所属类
	 * @param methodName
	 *            方法名称
	 * @return
	 */
	public static Method getMethod(Class<?> cls, String methodName) {
		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methodName.equals(methods[i].getName())) {
				return methods[i];
			}
		}
		return null;
	}
}
