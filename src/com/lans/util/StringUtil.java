package com.lans.util;

public class StringUtil {
	public static String init(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
}
