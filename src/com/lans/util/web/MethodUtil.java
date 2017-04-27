package com.lans.util.web;

import java.lang.reflect.Method;

public class MethodUtil {
	public static Method getMethod(Class<?> cls, String methodName) {
		Method[] methods = cls.getMethods();
		for (int i = 0 ; i < methods.length ; i ++) {
			if (methodName.equals(methods[i].getName())) {
				return methods[i];
			}
		}
		return null;
	}
}
