package com.lans.util.action;

import com.lans.util.message.MessageUtil;

public class ActionMessageUtil {
	private static final String PAGE_BASENAME = "com.lans.util.config.page";
	private static final String MESSAGE_BASENAME = "com.lans.util.config.messages";
	private static MessageUtil pageMU = new MessageUtil(PAGE_BASENAME);
	private static MessageUtil messageMU = new MessageUtil(MESSAGE_BASENAME);

	public static String getUrl(String key) {
		return pageMU.getValue(key);
	}

	public static String getMsg(String key) {
		return messageMU.getValue(key);
	}
}
