package com.speech.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends java.util.Date {

	/**
	*
	*/
	private static final long serialVersionUID = 2155545266875552658L;

	/**
	 * 功能：转换为Calendar。
	 * @return Calendar
	 */
	public static Calendar toCalendar() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c;
	}

	/**
	 * 功能：判断日期是否和当前date对象在同一天。
	 * @param date 比较的日期
	 * @return boolean 如果在返回true，否则返回false。
	 */
	public boolean isSameDay(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("日期不能为null");
		}
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		return this.isSameDay(cal2);
	}

	/**
	 * 功能：判断日期是否和当前date对象在同一天。
	 * @param cal 比较的日期
	 * @return boolean 如果在返回true，否则返回false。
	 */
	public boolean isSameDay(Calendar cal) {
		if (cal == null) {
			throw new IllegalArgumentException("日期不能为null");
		}
		// 当前date对象的时间
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(this);
		return (cal1.get(Calendar.ERA) == cal.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * 功能：将当前日期的秒数进行重新设置。
	 * @param second 秒数
	 * @return 设置后的日期
	 */
	public Date setSecondNew(int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.SECOND, second);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：将当前日期的分钟进行重新设置。
	 * @param minute 分钟数
	 * @return 设置后的日期
	 */
	public Date setMinuteNew(int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.MINUTE, minute);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：将当前日期的小时进行重新设置。
	 * @param hours 小时数 (24小时制)
	 * @return 设置后的日期
	 */
	public Date setHourNew(int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.HOUR_OF_DAY, hour);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：将当前日期的天进行重新设置。
	 * @param days 某一天
	 * @return 设置后的日期
	 */
	public Date setDayNew(int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.DATE, day);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：将当前日期的月进行重新设置。
	 * @param months  某一月
	 * @return 设置后的日期
	 */
	public Date setMonthNew(int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.MONTH, month - 1);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：将当前日期的年进行重新设置。
	 * @param years 某一年
	 * @return 设置后的日期
	 */
	public Date setYearNew(int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.YEAR, year);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：得到当月有多少天。
	 * @return int
	 */
	public int daysNumOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this);
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss字符串转换成日期(net.maxt.util.Date)<br/>
	 * @param dateStr 时间字符串
	 * @param 当前时间字符串的格式。
	 * @return net.maxt.util.Date 日期 ,转换异常时返回null。
	 */
	public static Date parseDate(String dateStr, SimpleDateFormat dataFormat) {
		try {
			java.util.Date d = dataFormat.parse(dateStr);
			return new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss字符串转换成日期(net.maxt.util.Date)<br/>
	 * @param dateStr yyyy-MM-dd HH:mm:ss字符串
	 * @return net.maxt.util.Date 日期 ,转换异常时返回null。
	 */
	public static Date parseDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date d = sdf.parse(dateStr);
			return new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 功能：当前时间增加毫秒数。
	 * @param milliseconds 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public Date addMilliseconds(int milliseconds) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.MILLISECOND, c.get(Calendar.MILLISECOND) + milliseconds);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：当前时间增加秒数。
	 * @param seconds 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public Date addSeconds(int seconds) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.SECOND, c.get(Calendar.SECOND) + seconds);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：当前时间增加分钟数。
	 * @param minutes 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public Date addMinutes(int minutes) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minutes);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：当前时间增加小时数。
	 * @param hours 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public Date addHours(int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hours);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：当前时间增加天数。
	 * @param days 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public Date addDays(int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：当前时间增加月数。
	 * @param months 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public Date addMonths(int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
		return new Date(c.getTimeInMillis());
	}
	
	/**
	 * 功能：指定时间增加一个月。
	 * @param date 指定日期。
	 * @return Date
	 */
	public Date addOneMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 功能：当前时间增加年数。注意遇到2月29日情况，系统会自动延后或者减少一天。
	 * @param years 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public Date addYears(int years) {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) + years);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 得到秒。格式：56<br/>
	 * @return int
	 */
	public int secondInt() {
		return Integer.parseInt(toString("ss"));
	}

	/**
	 * 得到分钟。格式：56<br/>
	 * @return int
	 */
	public int minuteInt() {
		return Integer.parseInt(toString("mm"));
	}

	/**
	 * 得到小时。格式：23<br/>
	 * @return int
	 */
	public int hourInt() {
		return Integer.parseInt(toString("HH"));
	}

	/**
	 * 得到日。格式：26<br/>
	 * 注意：这里1日返回1,2日返回2。
	 * @return int
	 */
	public int dayInt() {
		return Integer.parseInt(toString("dd"));
	}

	/**
	 * 得到月。格式：5<br/>
	 * 注意：这里1月返回1,2月返回2。
	 * @return int
	 */
	public int monthInt() {
		return Integer.parseInt(toString("MM"));
	}

	/**
	 * 得到年。格式：2013
	 * @return int
	 */
	public int yearInt() {
		return Integer.parseInt(toString("yyyy"));
	}

	/**
	 * 得到短时间。格式：12:01
	 * @return String
	 */
	public String shortTime() {
		return toString("HH:mm");
	}

	/**
	 * 得到长时间。格式：12:01:01
	 * @return String
	 */
	public String longTime() {
		return toString("HH:mm:ss");
	}

	/**
	 * 得到今天的第一秒的时间。
	 * @return Date
	 */
	public Date dayStart() {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 得到今天的最后一秒的时间。
	 * @return Date
	 */
	public Date dayEnd() {
		Calendar c = Calendar.getInstance();
		c.setTime(this);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * 根据日期得到星期几,得到数字。<br/>
	 * 7, 1, 2, 3, 4, 5, 6
	 * @return Integer 如：6
	 */
	public int dayOfWeekInt() {
		Integer dayNames[] = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		return dayNames[dayOfWeek];
	}

	/**
	 * 将日期转换成长日期字符串 例如：2009-09-09 01:01:01
	 * @return String
	 */
	public String toLongDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (null == this) ? null : df.format(this);
	}

	/**
	 * 将日期按照一定的格式进行格式化为字符串。<br/>
	 * 例如想将时间格式化为2012-03-05 12:56 ,则只需要传入formate为yyyy-MM-dd HH:mm即可。
	 * @param formate 格式化格式，如：yyyy-MM-dd HH:mm
	 * @return String 格式后的日期字符串。如果当前对象为null，则直接返回null。
	 */
	public String toString(String formate) {
		DateFormat df = new SimpleDateFormat(formate);
		return (null == this) ? null : df.format(this);
	}

	/**
	 * 得到某个时间的时间戳yyyyMMddHHmmss。
	 * @param date 时间
	 * @return String 如果当前对象为null，则直接返回null。
	 */
	public String toTimeStamp() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return (null == this) ? null : df.format(this);
	}

	/**
	 * 将日期转换成短日期字符串,例如：2009-09-09。
	 * @return String ,如果当前对象为null，返回null。
	 */
	public String toShortDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return (null == this) ? null : df.format(this);
	}
	
	/**
	 * 将日期类型转换成字符串类型
	 * @param args
	 */
	public static String dateToString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		String str = format.format(date);  
		return str;
	}
	
	/**
	 * 将字符串类型转换成日期类型
	 * @param args
	 */
	public static Date stringToDate(String str) { 
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		   Date date = null; 
		   try { 
		    date = format.parse(str); 
		   } catch (ParseException e) { 
		    e.printStackTrace(); 
		   } 
		   return date; 
		} 
	
	/**
	 * 判断两个日期的大小、计算差值 差值为天
	 * @param args
	 */
	public static int getIntervalDays(Date fDate, Date oDate) {
	       if (null == fDate || null == oDate) {
	           return -1;
	       }
	       long intervalMilli = oDate.getTime() - fDate.getTime();
	       return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	    }
	
	/**
	 * 判断两个日期的大小、计算差值 差值为月
	 * @param args
	 */
	 public static int getMonthSpace(Date d1, Date d2)
	            throws ParseException {
		Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if(month1 < month2 || month1 == month2 && day1 < day2) yearInterval --;
        // 获取月数差值
        int monthInterval =  (month1 + 12) - month2  ;
        if(day1 < day2) monthInterval --;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
	    }
	 
	 /**
	 * 指定日期加上天数后的日期
	 * @param num 为增加的天数
	 * @param newDate 创建时间
	 * @return
	 * @throws ParseException 
	 */
	 public static Date addDate(Date d) throws ParseException {
		  long time = d.getTime(); 
		  long day;
		  day = 1*24*60*60*1000; 
		  time+=day; 
		  return new Date(time);
	 }
	 
	 /**
	  * 截取日期只取月、日用来校验
	  * 截取月日后计算差值绝对值
	  * 后减前
	  */
	 public static int getMonthAndDay(Date fDate,Date oDate) throws ParseException{
		 SimpleDateFormat sdf = new SimpleDateFormat("MM-dd"); 
		 String fStr = sdf.format(fDate);
		 String oStr = sdf.format(oDate);
		 Date firDate = sdf.parse(fStr);
		 Date senDate = sdf.parse(oStr);
		 int num = Math.abs(DateUtil.getIntervalDays(firDate, senDate));
		 return num;
	 }
	 
	 /**
	  * 传入秒数 获取时间 "HH:mm:ss"
	  * 返回时间String类型
	  * @param seconds
	  * @return
	  * @throws ParseException
	  */
	 public static String getTimeBySecond(int seconds) throws ParseException{

		//开始时间  
		int startHours = seconds/3600;  
		int startMinutes = (seconds%3600)/60;  
		int startSeconds = (seconds%3600)%60;  
          
        Calendar cal=Calendar.getInstance();//使用日历类  
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式  
          
        //时间：yyyy-MM-dd HH:mm:ss  
        String startDateStr = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1)  
			                   + "-" + cal.get(Calendar.DAY_OF_MONTH) + " " + startHours  
			                   + ":" + startMinutes + ":" + startSeconds;  
		return startDateStr;  
	 }
	 
}
