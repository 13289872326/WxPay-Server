package com.xn.admin.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定义正则表达式验证方法
 * 
 * @author LHS
 * @Time 2017.08.07
 * @version 1.0L
 * 
 */
public class RegexUtils {

	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

	/**
	 * 正则表达式：验证IP地址
	 */
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	/**
	 * 正则表达式：验证QQ
	 */
	public static final String REGEX_QQ_NUM = "^[1-9]([0-9]{4,9})";
	/**
	 * 验证密码
	 */
	public static final String REGEX_PWD_STR = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
	/**
	 * 验证ID
	 */
	public static final String REGEX_ID_STR = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{32,32}$";
	/**
	 * 验证设备注册主题(新)
	 */
	public static final String REG_DEVICE_REQ = "^.[\\w,\\d]{32}.[\\w,\\d]{32}.registry$";
	/**
	 * 验证设备注册主题(旧)
	 */
	public static final String REG_DEVICE_OLD_REQ = "^.device.pub.[\\w,\\d]{32}$";

	// 客户端与服务端交互
	public static final String USER_TO_SERVER = "^.user.pub.[\\w,\\d]{0,32}.[\\w,\\d]{0,32}.[\\w,\\d]{0,32}";
	// 设备与服务端交互
	public static final String DEVICE_TO_SERVER = "^.device.pub.[\\w,\\d]{0,32}.[\\w,\\d]{0,32}.[\\w,\\d]{0,32}";

	/**
	 * 国际号码验证
	 */
	public static final String REGEX_INTERNAl_PHONENUMBER = "[1-9]\\d{5,15}";

	/**
	 * 校验密码
	 * 
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String password) {
		return Pattern.matches(REGEX_PWD_STR, password);
	}

	/**
	 * 校验ID
	 * 
	 * @param uuid
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isAccountId(String id) {
		return Pattern.matches(REGEX_ID_STR, id);
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验IP地址
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isIPAddr(String ipAddr) {
		return Pattern.matches(REGEX_IP_ADDR, ipAddr);
	}

	/**
	 * 验证非空
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isNone(String notEmputy) {
		int length = notEmputy.trim().length();
		return length > 0 ? false : true;
	}

	/**
	 * 验证国际手机号
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isInternalPhoneNumber(String phoneNumber) {
		return Pattern.matches(REGEX_INTERNAl_PHONENUMBER, phoneNumber);
	}

	/**
	 * 验证QQ号码
	 * 
	 * @param QQ
	 * @return
	 */
	public static boolean checkQQ(String QQ) {
		return Pattern.matches(QQ, REGEX_QQ_NUM);
	}

	/**
	 * 验证设备注册订阅主题（新）
	 * 
	 * @param title
	 * @return
	 */
	public static boolean isDeviceRegistryReq(String title) {
		Pattern.matches(title, REG_DEVICE_REQ);
		Pattern compile = Pattern.compile(REG_DEVICE_REQ);
		Matcher m = compile.matcher(title);
		return m.matches();

	}

	/**
	 * 设备注册（旧）
	 * 
	 * @param title
	 * @return
	 */
	public static boolean isDeviceRegisterOldReq(String title) {
		Pattern.matches(title, REG_DEVICE_OLD_REQ);
		Pattern compile = Pattern.compile(REG_DEVICE_OLD_REQ);
		Matcher m = compile.matcher(title);
		return m.matches();
	}

	/**
	 * 客户端与服务端主题
	 * 
	 * @param title
	 * @return
	 */
	public static boolean isUserToServerTitle(String title) {
		Pattern.matches(title, USER_TO_SERVER);
		Pattern compile = Pattern.compile(USER_TO_SERVER);
		Matcher m = compile.matcher(title);
		return m.matches();
	}

	/**
	 * 设备与服务端主题
	 * 
	 * @param title
	 * @return
	 */
	public static boolean isDeviceToServerTitle(String title) {
		Pattern.matches(title, DEVICE_TO_SERVER);
		Pattern compile = Pattern.compile(DEVICE_TO_SERVER);
		Matcher m = compile.matcher(title);
		return m.matches();
	}
}
