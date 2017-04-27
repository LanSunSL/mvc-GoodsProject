package com.lans.util.web;

import com.lans.util.message.MessageUtil;

public class ParameterValidatorUtil {
	private static final String VALIDATOR_BASENAME = "com.lans.util.conf.validator" ;
	private static MessageUtil validatorMU = new MessageUtil(VALIDATOR_BASENAME);
	private String validateRule ;
	public ParameterValidatorUtil(String actionName) {
		this.validateRule = validatorMU.getValue(actionName);
	}
	public boolean validate() {
		String[] temp = this.validateRule.split("\\|");
		for (int i = 0 ; i < temp.length ; i ++) {
			String[] result = temp[i].split(":");
			String paramName = result[0];
			String rule = result[1];
		}
		return false ;
	}
	
	public boolean validateString(String str) {
		return !(str == null || "".equals(str));
	}
	public boolean validateInt(String str) {
		if (this.validateString(str)) {
			return str.matches("\\d+");
		}
		return false ;
	}
	public boolean validateDouble(String str) {
		if (this.validateString(str)) {
			return str.matches("\\d+(\\.\\d+)?");
		}
		return false ;
	}
	public boolean validateDate(String str) {
		if (this.validateString(str)) {
			return str.matches("\\d{4}-\\d{2}-\\d{2}");
		}
		return false ;
	}
	public boolean validateDatetime(String str) {
		if (this.validateString(str)) {
			return str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		}
		return false ;
	}
	
}
