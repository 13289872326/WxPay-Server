package com.xn.admin.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

	public static int getYear(Date date) {
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String format = a.format(date);
		String sub = format.substring(0, 4);
		int year = Integer.parseInt(sub);
		return year;
	}

	public static int getMonth(Date date) {
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String format = a.format(date);
		String sub = format.substring(5, 7);
		int month = Integer.parseInt(sub);
		return month;
	}

	public static int getDay(Date date) {
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String format = a.format(date);
		String sub = format.substring(8, 10);
		int day = Integer.parseInt(sub);
		return day;
	}

	public static int getHour(Date date) {
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String format = a.format(date);
		String sub = format.substring(11, 13);
		int hour = Integer.parseInt(sub);
		return hour;
	}

	public static int getMinutes(Date date) {
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String format = a.format(date);
		String sub = format.substring(14, 16);
		int minutes = Integer.parseInt(sub);
		return minutes;
	}

	public static int getSecondse(Date date) {
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String format = a.format(date);
		String sub = format.substring(17, 19);
		int seconds = Integer.parseInt(sub);
		return seconds;
	}

	public static String getFormatDate(Date date) {
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = a.format(date);
		return format;
	}


}
