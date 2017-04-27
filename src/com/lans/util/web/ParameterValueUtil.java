package com.lans.util.web;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.lans.util.StringUtil;

import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class ParameterValueUtil {
	private ParameterValueUtil() {}
	
	public static Object getBasicParameterValue(String paramName, String paramType) throws ParseException {
		String val = ServletContextApplicationUtil.getRequest().getParameter(paramName);
		if ("int".equals(paramType) || "java.lang.Integer".equals(paramType)) {
			if (val == null || "".equals(val)) {
				return 0 ;
			}
			return Integer.parseInt(val);
		}
		if ("double".equals(paramType) || "java.lang.Double".equals(paramType)) {
			if (val == null || "".equals(val)) {
				return 0.0 ;
			}
			return Double.parseDouble(val);
		}
		if ("long".equals(paramType) || "java.lang.Long".equals(paramType)) {
			if (val == null || "".equals(val)) {
				return 0 ;
			}
			return Long.parseLong(val);
		}
		if ("java.util.Date".equals(paramType)) {
			if (val == null || "".equals(val)) {
				return null;
			}
			String pattern = "yyyy-MM-dd" ;
			if (!val.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
				pattern = "yyyy-MM-dd hh:mm:ss" ;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(val);
		}
		return val ;
	}
	public  static Object[] getMethodParameter(Class<?> actionClass, Method actionMethod) throws Exception {
		Class<?>[] params = actionMethod.getParameterTypes();   //取得方法所有参数的类型
		
		ClassPool classPool = ClassPool.getDefault();
		ClassPath classPath = new ClassClassPath(actionClass);
		classPool.insertClassPath(classPath);
		
		CtClass ctClass = classPool.get(actionClass.getName());
		CtMethod ctMethod = ctClass.getDeclaredMethod(actionMethod.getName());
		
		MethodInfo methodInfo = ctMethod.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		Object[] dataObj = new Object[params.length] ;
		int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1 ;
		for (int i = 0 ; i < params.length ; i ++) {
			String paramName = attribute.variableName(i + pos) ;
			String paramType = params[i].getName();
			dataObj[i] = getBasicParameterValue(paramName, paramType);
		}
		return dataObj ;
	}
	public static Object getObjectParameterValue(Class<?> voCls) throws Exception {
		Object voObj = voCls.newInstance();
		Field[] fields = voCls.getDeclaredFields();
		for (int i = 0 ; i < fields.length ; i ++) {
			Method met = voCls.getMethod("set" + StringUtil.init(fields[i].getName()), fields[i].getType()) ;
			met.invoke(voObj, getBasicParameterValue(fields[i].getName(), fields[i].getType().getName()));
		}
		return voObj ;
	}
}
