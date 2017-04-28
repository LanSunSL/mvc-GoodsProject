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

/**
 * 参数处理类
 * 
 * @author Lan
 *
 */
public class ParameterValueUtil {
	private ParameterValueUtil() {
	}

	/**
	 * 取得指定的参数内容，封装（SmartUpload处理）和未封装处理
	 * 
	 * @param paramName
	 *            参数名称
	 * @return 参数内容
	 */
	public static String getParameter(String paramName) {
		String requestContentType = ServletContextApplicationUtil.getRequest().getContentType();
		if (requestContentType != null) {
			if (requestContentType.contains("multipart/form-data")) {
				return ServletContextApplicationUtil.getSmartUpload().getRequest().getParameter(paramName);
			}
		}
		return ServletContextApplicationUtil.getRequest().getParameter(paramName);
	}

	/**
	 * 取得指定的一组参数内容，封装（SmartUpload处理）和未封装处理
	 * 
	 * @param paramName
	 *            参数名称
	 * @return 以数组形式返还参数内容
	 */
	public static String[] getParameterValues(String paramName) {
		String requestContentType = ServletContextApplicationUtil.getRequest().getContentType();
		if (requestContentType != null) {
			if (requestContentType.contains("multipart/form-data")) {
				return ServletContextApplicationUtil.getSmartUpload().getRequest().getParameterValues(paramName);
			}
		}
		return ServletContextApplicationUtil.getRequest().getParameterValues(paramName);
	}

	public static Object[] getMethodParameter(Class<?> actionClass, Method actionMethod) throws Exception {
		Class<?>[] params = actionMethod.getParameterTypes(); // 取得方法所有参数的类型

		ClassPool classPool = ClassPool.getDefault();
		ClassPath classPath = new ClassClassPath(actionClass);
		classPool.insertClassPath(classPath);

		CtClass ctClass = classPool.get(actionClass.getName());
		CtMethod ctMethod = ctClass.getDeclaredMethod(actionMethod.getName());

		MethodInfo methodInfo = ctMethod.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attribute = (LocalVariableAttribute) codeAttribute
				.getAttribute(LocalVariableAttribute.tag);

		Object[] dataObj = new Object[params.length];
		int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
		for (int i = 0; i < params.length; i++) {
			String paramName = attribute.variableName(i + pos);
			String paramType = params[i].getName();
			if (isBasic(paramType)) {
				dataObj[i] = getBasicParameterValue(paramName, paramType);
			} else {
				paramType = params[i].getSimpleName();
				if (isArray(paramType)) {
					dataObj[i] = getArrayParameterValue(paramName, paramType);
				} else {
					dataObj[i] = getObjectParameterValue(params[i]);
				}
			}
		}
		return dataObj;
	}

	/**
	 * 取得基本类型的参数值，注意区分是否封装
	 * 
	 * @param paramName
	 *            参数名称
	 * @param paramType
	 *            参数类型
	 * @return 参数值
	 * @throws ParseException
	 */
	public static Object getBasicParameterValue(String paramName, String paramType) throws ParseException {
		String val =getParameter(paramName);
		if ("int".equals(paramType) || "java.lang.Integer".equals(paramType)) {
			if (val == null || "".equals(val)) {
				return 0;
			}
			return Integer.parseInt(val);
		}
		if ("double".equals(paramType) || "java.lang.Double".equals(paramType)) {
			if (val == null || "".equals(val)) {
				return 0.0;
			}
			return Double.parseDouble(val);
		}
		if ("long".equals(paramType) || "java.lang.Long".equals(paramType)) {
			if (val == null || "".equals(val)) {
				return 0;
			}
			return Long.parseLong(val);
		}
		if ("java.util.Date".equals(paramType)) {
			if (val == null || "".equals(val)) {
				return null;
			}
			String pattern = "yyyy-MM-dd";
			if (val.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
				pattern = "yyyy-MM-dd hh:mm:ss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(val);
		}
		return val;
	}

	public static Object getArrayParameterValue(String paramName, String paramType) {
		String val = getParameter(paramName);
		String[] result ;
		if (val.contains(",")) {
			result = val.split(",");
		} else {
			result = getParameterValues(paramName);
		}
		if ("int[]".equals(paramType) || "Integer[]".equals(paramType)) {
			int[] data = new int[result.length];
			for (int i = 0 ; i < data.length ; i ++) {
				data[i] = Integer.parseInt(result[i]);
			}
			return data;
		}
		return result ;
	}
	/**
	 * 取得vo类对象，由表单传递值，反射设置vo对象的属性
	 * @param voCls
	 * @return
	 * @throws Exception
	 */
	public static Object getObjectParameterValue(Class<?> voCls) throws Exception {
		Object voObj = voCls.newInstance();
		Field[] fields = voCls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Method met = voCls.getMethod("set" + StringUtil.initcap(fields[i].getName()), fields[i].getType());
			met.invoke(voObj, getBasicParameterValue(fields[i].getName(), fields[i].getType().getName()));
		}
		return voObj;
	}

	/**
	 * 判断是否是基本数据类型
	 * 
	 * @param paramType
	 * @return
	 */
	public static boolean isBasic(String paramType) {
		return ("int".equals(paramType) || "java.lang.Integer".equals(paramType) || "double".equals(paramType)
				|| "java.lang.Double".equals(paramType) || "long".equals(paramType)
				|| "java.lang.Long".equals(paramType) || "java.util.Date".equals(paramType)
				|| "java.lang.String".equals(paramType));
	}

	/**
	 * 判断参数是否是数组，一般都是int数组或String数组，如deleteBatch方法的参数
	 * 
	 * @param paramType
	 * @return
	 */
	public static boolean isArray(String paramType) {
		return "int[]".equals(paramType) || "java.lang.String".equals(paramType);
	}
}
