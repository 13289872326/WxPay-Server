package com.xn.admin.common.utils;


import java.security.KeyPair;

public interface KeyPairGenerater {

	/**
	 * 生成默认长度的密钥对，默认为32位
	 * 
	 * @return
	 */
	public KeyPair generateKeyPair() throws Exception;

	/**
	 * 生成固定长度的密钥对
	 * 
	 * @param len
	 *            在8 ~ 32 之间
	 * @return
	 */
	public  KeyPair generateKeyPair(int len) throws Exception;
}
