package com.camelot.kuka.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * <p>Description: [时间工具类]</p>
 * Created on 2019年8月7日
 *
 *
 * @version 1.0
 * Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class DateUtils {

	public static final String DATEFORMAT = "yyyy-MM-dd";
	public static final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDDHHMMSS ="yyyyMMddHHmmss";

	/**
	 * 字符串转换为Date
	 *
	 * @param dateStr    - 时间字符串
	 * @param formatType - 该字符串格式
	 **/
	public static Date strToDate(String dateStr, String formatType) {
		try {
			if (StringUtils.isBlank(dateStr)) {
				return null;
			}
			if (StringUtils.isBlank(formatType)) {
				formatType = DATEFORMAT;
			}
			DateFormat sdf = new SimpleDateFormat(formatType);
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * 字符串转换为Date,严格校验,2019-01-35这种日期将报错
     *
     * @param dateStr    - 时间字符串
     * @param formatType - 该字符串格式
     **/
    public static Date strToDateStrict(String dateStr, String formatType) {
        try {
            if (StringUtils.isBlank(dateStr)) {
                return null;
            }
            if (StringUtils.isBlank(formatType)) {
                formatType = DATEFORMAT;
            }
            DateFormat sdf = new SimpleDateFormat(formatType);
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * 字符串转换为字符串格式
	 *
	 * @param dateStr    - 时间字符串
	 * @param sourceFormatType - 需要转换的该字符串格式
	 * @param targetFormatType - 目标转换后的时间格式
	 **/
	public static String strToStr(String dateStr, String sourceFormatType,String targetFormatType) {
		try {
			if (StringUtils.isBlank(dateStr)||StringUtils.isBlank(sourceFormatType)||StringUtils.isBlank(targetFormatType)) {
				return null;
			}
			DateFormat sourcesdf = new SimpleDateFormat(sourceFormatType);
			DateFormat targetsdf = new SimpleDateFormat(targetFormatType);
			Date date = sourcesdf.parse(dateStr);
			return targetsdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Date转换为字符串
	 *
	 * @param date       - 时间
	 * @param formatType - 该字符串格式
	 **/
	public static String dateToStr(Date date, String formatType) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isBlank(formatType)) {
			formatType = DATEFORMAT;
		}
		DateFormat sdf = new SimpleDateFormat(formatType);
		return sdf.format(date);
	}

	/**
	 * 时间字符串增加天数
	 *
	 * @param dateStr    - 时间字符串
	 * @param addDateNum - 增加天数
	 * @param formatType - 该字符串格式
	 **/
	public static String addDateStr(String dateStr, Integer addDateNum, String formatType) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(strToDate(dateStr, formatType));
		if (addDateNum == null) {
			addDateNum = 1;
		}
		calendar.add(Calendar.DATE, addDateNum);
		return dateToStr(calendar.getTime(), formatType);
	}

	/**
	 * <p>Discription:[获取时间戳]</p>
	 * Created on 2018年2月27日
	 *
	 * @return
	 */
	public static String getTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * <p>Discription:[String时间格式转换]</p>
	 * Created on 2018年6月20日
	 *
	 * @param dateStr 时间
	 * @param pattern 格式
	 * @return
	 */
	public static Date getFormatDateFromString(String dateStr, String pattern) {
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>Discription:[获取当月第一天]</p>
	 * Created on 2018年6月27日
	 *
	 * @return
	 */
	public static Date getFirstMotnDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal_1 = Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.MONTH, 0);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());
		return getFormatDateFromString(firstDay, "yyyy-MM-dd");
	}


	/**
	 * <p>Discription:[获取月份]</p>
	 * Created on 2018年6月27日
	 *
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(date);
		int month = cale.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 
	    * @Title: getYear
	    * @Description: 获取年
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	 */
	 public static 	int getYear(Date date) {
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(date);
		int year = cale.get(Calendar.YEAR);
		return year;		 
	}
	 
	 /**
		 * 
	    * @Title: getDay
	    * @Description: 获取天
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	 */
	 public static 	int getDay(Date date) {
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(date);
		int day = cale.get(Calendar.DAY_OF_MONTH);
		return day;		 
	}
	 
	 /**
		 * 
	    * @Title: getWeek
	    * @Description: 获取星期几  星期一 week = 1，星期天 week = 0
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	 */
	 public static 	int getWeek(Date date) {
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(date);
		int week = cale.get(Calendar.DAY_OF_WEEK)-1;
		return week;		 
	}
	 
	/**
	 * Description: [获取当天过期时间]
	 *
	 * @param
	 * @return: java.lang.Long
	 * Created on 2019年08月16日
	 * @version 1.0
	 * @author: 贺小波
	 * Copyright (c) 2019 北京柯莱特科技有限公司
	 **/
	public static Long getCurrent2TodayEndMillisTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTimeInMillis() - System.currentTimeMillis();
	}

	/**
	 * Description: [获取当前零点]
	 *
	 * @param
	 * @return: java.util.Date
	 * Created on 2019年08月23日
	 * @version 1.0
	 * @author: 贺小波
	 * Copyright (c) 2019 北京柯莱特科技有限公司
	 **/
	public static Date getCurrentZero() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Description: [获取源时间相差天数时间]
	 *
	 * @param date 源时间-必填,为空默认是当前时间
	 * @param day  相差天数,为空默认为0
	 * @return: java.util.Date
	 * Created on 2019年08月23日
	 * @version 1.0
	 * Copyright (c) 2019 北京柯莱特科技有限公司
	 **/
	public static Date getCalculationDay(Date date, Integer day) {
		if (date == null) {
			date = Calendar.getInstance().getTime();
		}
		if (day == null) {
			day = 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, monthDay - day);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getyyyyMMddHHmmss() {
		DateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
		return sdf.format(new Date());
	}

	/**
	 * Description: [判断当天时间是否过期]
	 * @param date 源时间-必填
	 * @return: java.time.LocalTime
	 * Created on 2019年09月3日
	 * @version 1.0
	 **/
	public static Date getCurrDate(Date date) {
	    if(date==null){
	       date = new Date();
	    }
		Date dateEnd = getNewDate(date,1,0);
		LocalTime dateEndTime = getLocalTime(dateEnd);
		LocalTime dateTime = getLocalTime(date);		
		if(dateTime.isBefore(dateEndTime)) {
			date = getCalculationDay(date , 1);
		}
		return date;
	}
	
	/**
	 * Description: [判断当天时间是否过期若过期，减1天，并赋予新的时分秒]
	 * @param date 源时间-必填
	 * @return: java.time.LocalTime
	 * Created on 2019年09月3日
	 * @version 1.0
	 **/
	public static Date getCurrDate(Date date ,LocalTime LocalTime) {
	    if(date==null){
	       date = new Date();
	    }
		Date dateEnd = getNewDate(date,1,0);
		LocalTime dateEndTime = getLocalTime(dateEnd);
		LocalTime dateTime = getLocalTime(date);		
		if(dateTime.isBefore(dateEndTime)) {
			date = getCalculationDay(date , 1);
			date = LocalTimeToUdate(date,LocalTime);
		}
		return date;
	}

	/**
	 * Description: [Date 转 localTime]
	 * @param date 源时间-必填
	 * @return: java.time.LocalTime
	 * Created on 2019年09月3日
	 * @version 1.0
	 **/
	public static LocalTime getLocalTime(Date date) {
	    if(date==null){
	       date = new Date();
	    }
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    LocalTime localTime = localDateTime.toLocalTime();
	    return localTime;
	}
	
	/**
	 * Description: [Date 转 localDate]
	 * @param date 源时间-必填
	 * @return: java.time.LocalDate
	 * Created on 2019年09月6日
	 * @version 1.0
	 **/
	public static LocalDate getLocalDate(Date date) {
	    if(date==null){
	    	 date = new Date();
	    }
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    LocalDate localTime = localDateTime.toLocalDate();
	    return localTime;
	}	

	/**
	 * Description: [localTime 转 Date]
	 * @param date 源时间-必填
	 * @return: java.time.LocalDate
	 * Created on 2019年09月6日
	 * @version 1.0
	 **/
	public static Date LocalTimeToUdate(Date date, LocalTime localTime) {
		if(localTime == null) {
			return null;
		}
		if(date==null){
	    	 date = new Date();
	    }
	    LocalDate localDate = getLocalDate(date);
	    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDateTime.atZone(zone).toInstant();
	    Date newDate = Date.from(instant);
	    return newDate;
	}
	
	/**
	 * 
	    * @Title: getCurrentZero
	    * @Description: 获取指定日期零点 ,null时默认当前时间
	    * @param @param date
	    * @param @return    参数
	    * @return Date    返回类型
	    * @throws
	 */
	public static Date getCurrentZero(Date date) {    
	    if(date==null) {
	        date = Calendar.getInstance().getTime();
	    }
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	
	/**
	 * 
	    * @Title: getCurrentZero
	    * @Description: 获取指定日期过期点 ,null时默认当前时间
	    * @param @param date
	    * @param @return    参数
	    * @return Date    返回类型
	    * @throws
	 */
	public static Date getCurrentEnd(Date date) {    
	    if(date==null) {
	        date = Calendar.getInstance().getTime();
	    }
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTime();
	}
	
	/**
	 * 
	    * @Title: getNewDate
	    * @Description: 获取指定日期 ,指定时间
	    * @param @param date
	    * @param @return    参数
	    * @return Date    返回类型
	    * @throws
	 */
	public static Date getNewDate(Date date,Integer hour, Integer minute) {    
	    if(date==null) {
	        date = Calendar.getInstance().getTime();
	    }
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, hour);
	    calendar.set(Calendar.MINUTE, minute);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	
	/**
	 * Description: [获取指定日期过期 ,null时默认当前时间
	 * @param
	 * @return: java.lang.Long
	 * Created on 2019年08月16日
	 * @version 1.0
	 **/
	public static Date getCurrent2TodayEndMillisTime(Date date) {
	    if(date==null) {
	        date = Calendar.getInstance().getTime();
	    }
	    Calendar todayEnd = Calendar.getInstance();
	    todayEnd.setTime(date);
	    todayEnd.set(Calendar.HOUR_OF_DAY, 23);
	    todayEnd.set(Calendar.MINUTE, 59);
	    todayEnd.set(Calendar.SECOND, 59);
	    todayEnd.set(Calendar.MILLISECOND, 999);
	    return todayEnd.getTime();
	}
	
	
	/**
	 * 获取指定日期所在月份开始的时间
	 * 时间格式yyyyMMdd
	 * @param date 指定日期
	 * @return
	 */
	public static Date getMonthBegin(Date date) {
	    if(date==null) {
	        date = Calendar.getInstance().getTime();
	    }
	    SimpleDateFormat aDateFormat=new SimpleDateFormat("yyyyMMdd");
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);        
	    //设置为1号,当前日期既为本月第一天  
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    //将小时至0  
	    c.set(Calendar.HOUR_OF_DAY, 0);  
	    //将分钟至0  
	    c.set(Calendar.MINUTE, 0);  
	    //将秒至0  
	    c.set(Calendar.SECOND,0);  
	    //将毫秒至0  
	    c.set(Calendar.MILLISECOND, 0);  
	    // 获取本月第一天的时间
	    return c.getTime();  
	}
	
	/**
	 * 获取指定日期所在月份结束的时间戳
	 * @param date 指定日期
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
	    if(date==null) {
	        date = Calendar.getInstance().getTime();
	    }
	    Calendar c = Calendar.getInstance();  
	    c.setTime(date);    
	    //设置为当月最后一天
	    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    //将小时至23
	    c.set(Calendar.HOUR_OF_DAY, 23);  
	    //将分钟至59
	    c.set(Calendar.MINUTE, 59);  
	    //将秒至59
	    c.set(Calendar.SECOND,59);  
	    //将毫秒至999
	    c.set(Calendar.MILLISECOND, 999);  
	    // 获取本月最后一天的时间戳  
	    return c.getTime();
	}
	 
	 /**
		 * 
	    * @Title: getComSpeDate
	    * @Description: 特定格式轉化hh:mm
	    * @param @param dateDate
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	 public static String getComSpeDate(LocalTime date) {
		 if(date==null){
	    	 return null;
	    }			 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String dateString = date.format(formatter) ;		 
		return dateString;
	}
	 
	 /**
      * 判断时间是否在时间段内
      * 
      * @param nowTime
      * @param beginTime
      * @param endTime
      * @return
      */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if (date.after(begin) && date.before(end)) {
			return true;
		} else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 时间增加天数
	 *
	 * @param addDateNum - 增加天数
	 **/
	public static Date addDate(Date date, Integer addDateNum,Integer addHourNum) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		if (addDateNum == null) {
			addDateNum = 0;
		}
		if(addHourNum == null){
			addHourNum = 0;
		}
		calendar.add(Calendar.DATE, addDateNum);
		calendar.add(Calendar.HOUR, addHourNum);
		return calendar.getTime();
	}

	
	/**
      * 查询某日期是周几
      * 
      * @param nowTime
      * @return
      */
	public static int queryWeekDayNum(Date nowTime) {
		Calendar c=Calendar.getInstance();
		c.setTime(nowTime);
		int weekday=c.get(Calendar.DAY_OF_WEEK);
		weekday--;
		if(weekday == 0) {
			weekday = 7;
		}
		return weekday;
	}
	
	/**
     * 获取上(下)周周几的日期
     * 
     * @param dayOfWeek      设置查询的时间是周几Calendar.MONDAY-Calendar.SUNDAY
     * @param weekOffset     周偏移，上周为-1，本周为0，下周为1，以此类推
     * @return
     */
    public static Date getNextDayOfWeek(int dayOfWeek, int weekOffset) {
        if (dayOfWeek > Calendar.SATURDAY || dayOfWeek < Calendar.SUNDAY) {
            return null;
        }
        Calendar date = Calendar.getInstance(Locale.CHINA);
        date.setFirstDayOfWeek(Calendar.MONDAY);//设置中国是从周一开始算
        // 周数减一，即上周
        date.add(Calendar.WEEK_OF_MONTH, weekOffset);
        // 日子设为周几
        date.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        // 时分秒全部置0
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }
    
    /**
     * 获取当前日期是第几周  
     * 一年第一周必须满7天，否则为上年最后一周
     * @return
     */
    public static int getDayOfWeek(Date now) {
    	Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(now);
		return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 获取某年第几周的日期 
     * @param year 年
     * @param weekNo 周序数
     * @param dayOfWeek 周几 Calendar.MONDAY-Calendar.SUNDAY
     * 一年第一周必须满7天，否则为上年最后一周
     * @return
     */
	public static Date getDayOfWeekNo(int year,int weekNo,int dayOfWeek){
		Calendar cal = Calendar.getInstance();
        cal.setMinimalDaysInFirstWeek(7);
        cal.setFirstDayOfWeek(Calendar.MONDAY); 
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return cal.getTime(); 
	}

	/**
	 * <p>Discription:[获取某月第一天]</p>
	 * Created on 2018年6月27日
	 *
	 * @return
	 */
	public static Date getFirstMotnDay(int year,int mouth) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();//获取当前日期
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, mouth-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal.getTime());
		return getFormatDateFromString(firstDay, "yyyy-MM-dd");
	}

	/**
	 * <p>Discription:[获取某月最后一天]</p>
	 * Created on 2018年6月27日
	 *
	 * @return
	 */
	public static Date getLastMotnDay(int year,int mouth) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();//获取当前日期
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, mouth-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		String lastDay = format.format(cal.getTime());
		return getFormatDateFromString(lastDay, "yyyy-MM-dd");
	}

	/**
	 * 获取过去第几天的日期
	 *
	 * @param past
	 * @return
	 */
	public static Date getPastDate(int past, Date date) {
		if (date == null ) {
			date = new Date();
		}
		if (past < 0 ) {
			return new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
		Date today = calendar.getTime();
		return today;
	}
	/**
	 * 日期格式转换
	 * @author 陈贵兵
	 */
	public static String formatConvert(String dateStr,String pattern) {
		if (StringUtils.isEmpty(dateStr)) {
			return dateStr;
		}
		try {
			Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(dateStr);
			return dateToStr(date,pattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
}
