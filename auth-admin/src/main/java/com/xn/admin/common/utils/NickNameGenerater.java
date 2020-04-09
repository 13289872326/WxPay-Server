package com.xn.admin.common.utils;


public class NickNameGenerater {
	public static String getRandomHan() {
		String nickName="";
		for(int i=0;i<3;i++){
			nickName=nickName+(char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));;
		}
		return nickName;
	}
}
