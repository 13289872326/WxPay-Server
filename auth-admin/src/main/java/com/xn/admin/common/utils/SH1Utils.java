package com.xn.admin.common.utils;

import java.security.MessageDigest;

/**
 * 编码解码工具类
 * 
 * @author lhs
 * 
 */
public class SH1Utils {

	public static String encryptSH1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '6', '7', '8', '9','a', 'b', 'e', 'f', 'g', 'h', 'i', 'j',
				             'k', 'l', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j*2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}