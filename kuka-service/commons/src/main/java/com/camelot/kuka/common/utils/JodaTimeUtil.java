package com.camelot.kuka.common.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * JodaTime日期工具类
 * 
 * @author shidianhui
 *
 */
public class JodaTimeUtil {
	
	public static final String SDF_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	
    public static final String SDF_YMDHMS1 = "yyyy/MM/dd HH:mm:ss";
	
    public static final String SDF_YMDHMS2 = "yyyyMMddHHmmss";
	
    public static final String SDF_YMD = "yyyy-MM-dd";
	
	/**
	 * format java.util.Date to String
	 * 
	 * @param dateTime
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(format);
	}
	
	/**
	 * format org.joda.time.DateTime to String
	 * 
	 * @param dateTime
	 * @param format
	 * @return
	 */
	public static String formatDateTime(DateTime dateTime, String format) {
		return dateTime == null ? null : dateTime.toString(format);
	}
	
	/**
	 * return a String of now
	 * 
	 * @param format eg:YYYYMMdd
	 * @return str
	 * @throws Exception
	 */
	public static String getNowWithFormat(String format) {
		DateTime dateTime = new DateTime();
		return dateTime.toString(format);
	}
	
	/**
	 * use the specified format parse the String date,return org.joda.time.DateTime
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static DateTime parseDateTime(String date, String format) {
		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
			DateTime dateTime = formatter.parseDateTime(date);
			return dateTime;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * use the specified format parse the String date,return java.util.Date
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parseDate(String date, String format) {
		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
			DateTime dateTime = formatter.parseDateTime(date);
			return dateTime.toDate();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取指定日期的开始时间 
	 * 
	 * @param date
	 * @return DateTime
	 */
	public static DateTime getStartOfTheDay(DateTime date) {
		if (null == date) {
			return null;
		}

		return date.withTimeAtStartOfDay();
	}
	
	/**
	 * 获取指定日期的结束时间
	 * 
	 * @param date
	 * @return DateTime
	 */
	public static DateTime getEndOfTheDay(DateTime date) {
		if (null == date) {
			return null;
		}

		return date.millisOfDay().withMaximumValue();
	}
	
	/**
	 *  获取指定日期所在周的开始时间
	 *  
	 * @param date
	 * @return DateTime
	 */
	public static DateTime getStartOfTheWeek(DateTime date) {
		if (null == date) {
			return null;
		}
		
		date = getStartOfTheDay(date);

		return date.dayOfWeek().withMinimumValue();
	}

	/**
	 * 获取指定日期所在周的结束时间
	 * 
	 * @param date
	 * @return DateTime
	 */
	public static DateTime getEndOfTheWeek(DateTime date) {
		if (null == date) {
			return null;
		}
		
		date = getEndOfTheDay(date);

		return date.dayOfWeek().withMaximumValue();
	}
	
	/**
	 *  获取指定日期所在月的开始时间
	 *  
	 * @param date
	 * @return DateTime
	 */
	public static DateTime getStartOfTheMonth(DateTime date) {
		if (null == date) {
			return null;
		}
		
		date = getStartOfTheDay(date);

		return date.dayOfMonth().withMinimumValue();
	}

	/**
	 * 获取指定日期所在月的结束时间
	 * 
	 * @param date
	 * @return DateTime
	 */
	public static DateTime getEndOfTheMonth(DateTime date) {
		if (null == date) {
			return null;
		}

		date = getEndOfTheDay(date);

		return date.dayOfMonth().withMaximumValue();
	}
	
}
