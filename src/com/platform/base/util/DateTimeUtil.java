package com.platform.base.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @title :DateTimeUtil
 * @description :日期时间处理工具类
 * @update :2013-8-13 下午02:05:38
 * @author :wang_ll
 * @version :1.0.0
 * @since :2013-8-13
 */
public class DateTimeUtil {

	/**
	 * 指定日期加上N天
	 * 
	 * @comment format 默认格式为yyyy-MM-dd HH:mm:ss
	 * @param format
	 *            返回的日期格式
	 * @param addDays
	 *            需要累加的天数（可以为负数）
	 * @return
	 */
	public static String addDaysToDate(Date date, int addDays, String format) {
		if (date == null) {
			System.out.println("input parameter date is null...");
			return "";
		}

		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addDays);
		Date d = cal.getTime();
		SimpleDateFormat sp = new SimpleDateFormat(format);
		// 转换
		try {
			String time = sp.format(d);
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 当前日期加上N天
	 * 
	 * @comment format 默认格式为yyyy-MM-dd HH:mm:ss
	 * @param format
	 *            返回的日期格式
	 * @param addDays
	 *            当前日期上需要累加的天数（可以为负数）
	 * @return
	 */
	public static String addDaysToNow(int addDays, String format) {
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(nowAsDate());
		cal.add(Calendar.DATE, addDays);
		Date d = cal.getTime();
		SimpleDateFormat sp = new SimpleDateFormat(format);
		try {
			String time = sp.format(d);
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取当前时间Date类型
	 * 
	 * @return
	 */
	public static Date nowAsDate() {
		Date time = new Date();
		Timestamp timestamp = new Timestamp(time.getTime());
		return timestamp;
	}

	/**
	 * 获取当前系统时间 Timestamp类型
	 */
	public static Timestamp nowAsTimestamp() {
		Timestamp start = new Timestamp(System.currentTimeMillis());
		return start;
	}

	/**
	 * 返回系统当前系统时间对应的字符串,格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String nowAsString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date()).toString();
	}

	/**
	 * 获取指定格式的当前时间
	 * 
	 * @param format
	 *            返回日期的格式，默认为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String nowAsString(String format) {
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}

		SimpleDateFormat tempDate = new SimpleDateFormat(format);
		String datetime = tempDate.format(new java.util.Date());
		return datetime;
	}

	/**
	 * Date转换成String
	 * 
	 * @param format
	 *            :返回日期格式
	 * @param date
	 *            :待转换的日期对象
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			System.out.println("input parameter date is null...");
			return "";
		}

		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat tempDate = new SimpleDateFormat(format);
		String datetime = tempDate.format(date);
		return datetime;
	}

	/**
	 * Date类型转换成Timesatmp类型
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		if (date == null) {
			System.out.println("input parameter date is null...");
			return null;
		}

		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	/**
	 * String转换成Date类型
	 * 
	 * @param time
	 *            :待转换的时间字符串
	 * @param format
	 *            :时间转换格式，默认为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date stringToDate(String time, String format) {
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * String转换成Timestamp类型
	 * 
	 * @param time
	 *            :待转换的时间字符串
	 * @param format
	 *            :时间转换格式，默认为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Timestamp stringToTimestamp(String time, String format) {
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * Timestamp类型转换成Date类型
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Date timestampToDate(Timestamp timestamp) {
		if (timestamp == null) {
			System.out.println("input parameter timestamp is null...");
			return null;
		}

		Date date = new Date(timestamp.getTime());
		return date;
	}

	/**
	 * 将毫秒数转换成具体的天/小时/分：xx天xx小时xx分钟
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public static String formatMiniseconds(long mss) {
		StringBuffer sbf = new StringBuffer();
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		if (days != 0) {
			sbf.append(days + "天");
		}
		if (hours != 0) {
			sbf.append(hours + "小时");
		}
		if (minutes != 0) {
			sbf.append(minutes + "分钟");
		}
		if (seconds != 0) {
			sbf.append(seconds + "秒");
		}
		return sbf.toString();
	}

	/**
	 * 将日期格式时间转换为1970年以来的毫秒数
	 * */
	public static long formatDateToMiniseconds(String time, String format){
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 格式化日期时间字符串
	 * 例如：formatDateTimeString("20140624","yyyyMMdd","yyyy-MM-dd")
	 * */
	public static String formatDateTimeString(String dateTime, String formatFrom, String formatTo){
		String result = null;
		SimpleDateFormat sdfFrom = new SimpleDateFormat(formatFrom);
		SimpleDateFormat sdfTo = new SimpleDateFormat(formatTo);
		try {
			Date date= sdfFrom.parse(dateTime);
			result = sdfTo.format(date);
		} catch (ParseException e) {
//			LogUtil.error(e);
		}
		
		return result;
	}
	public static void main(String[] args) {
		long s = formatDateToMiniseconds("2014-04-29 06:00:00", null);
		System.out.println(s);
	}
	
	
}
