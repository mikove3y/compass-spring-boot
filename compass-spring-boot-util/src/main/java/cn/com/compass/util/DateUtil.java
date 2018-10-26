package cn.com.compass.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;

public class DateUtil {
	
	public static final long SECOND = 1000; // 1秒 java已毫秒为单位

	public static final long MINUTE = SECOND * 60; // 一分钟

	public static final long HOUR = MINUTE * 60; // 一小时

	public static final long DAY = HOUR * 24; // 一天

	public static final long WEEK = DAY * 7; // 一周

	public static final long YEAR = DAY * 365; // 一年

	// 日期时间类型格式 24小时制HH:mm:ss 12小时制hh:mm:ss
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 日期类型格式
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	// 时间类型的格式 24小时制HH:mm:ss 12小时制hh:mm:ss
	public static final String TIME_FORMAT = "HH:mm:ss";

	public static final Map<Integer, String> WEEK_DAY = new HashMap<>();
	
	static {
		WEEK_DAY.put(1, "星期天");
		WEEK_DAY.put(2, "星期一");
		WEEK_DAY.put(3, "星期二");
		WEEK_DAY.put(4, "星期三");
		WEEK_DAY.put(5, "星期四");
		WEEK_DAY.put(6, "星期五");
		WEEK_DAY.put(7, "星期六");
	}

	/**
	 * 获取当前的时间
	 *
	 * @return
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat(TIME_FORMAT).format(new Date());
	}

	/**
	 * 讲指定的时间格式化成出返回
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeOfDate(Date date) {
		return new SimpleDateFormat(TIME_FORMAT).format(date);
	}

	/**
	 * 将指定的字符串解析为时间类型
	 *
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date getTimeOfDateStr(String dateStr) throws ParseException {
		return new SimpleDateFormat(TIME_FORMAT).parse(dateStr);
	}

	/**
	 * 在当前时间的基础上加或减去year年
	 *
	 * @param year
	 * @return
	 */
	public static Date year(int year) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(new Date());
		Cal.add(Calendar.YEAR, year);
		return Cal.getTime();
	}

	/**
	 * 在指定的时间上加或减去几年
	 *
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date year(Date date, int year) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(date);
		Cal.add(java.util.Calendar.YEAR, year);
		return Cal.getTime();
	}

	/**
	 * 在当前时间的基础上加或减去几月
	 *
	 * @param month
	 * @return
	 */
	public static Date month(int month) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(new Date());
		Cal.add(Calendar.MONTH, month);
		return Cal.getTime();
	}

	/**
	 * 在指定的时间上加或减去几月
	 *
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date month(Date date, int month) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(date);
		Cal.add(java.util.Calendar.MONTH, month);
		return Cal.getTime();
	}

	/**
	 * 在当前时间的基础上加或减去几天
	 *
	 * @param day
	 * @return
	 */
	public static Date day(int day) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(new Date());
		Cal.add(Calendar.DAY_OF_YEAR, day);
		return Cal.getTime();
	}

	/**
	 * 在指定的时间上加或减去几天
	 *
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date day(Date date, int day) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(date);
		Cal.add(java.util.Calendar.DAY_OF_YEAR, day);
		return Cal.getTime();
	}

	/**
	 * 在当前时间的基础上加或减去几小时-支持浮点数
	 *
	 * @param hour
	 * @return
	 */
	public static Date hour(float hour) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(new Date());
		Cal.add(java.util.Calendar.MINUTE, (int) (hour * 60));
		return Cal.getTime();
	}

	/**
	 * 在制定的时间上加或减去几小时-支持浮点数
	 *
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date hour(Date date, float hour) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(date);
		Cal.add(java.util.Calendar.MINUTE, (int) (hour * 60));
		return Cal.getTime();
	}

	/**
	 * 在当前时间的基础上加或减去几分钟
	 *
	 * @param minute
	 * @return
	 */
	public static Date minute(int minute) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(new Date());
		Cal.add(java.util.Calendar.MINUTE, minute);
		return Cal.getTime();
	}

	/**
	 * 在制定的时间上加或减去几分钟
	 *
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date minute(Date date, int minute) {
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(date);
		Cal.add(java.util.Calendar.MINUTE, minute);
		return Cal.getTime();
	}

	/**
	 * 判断字符串是否为日期字符串
	 *
	 * @param date
	 *            日期字符串
	 * @return true or false
	 */
	public static boolean isDate(String date) {
		try {
			new SimpleDateFormat(DATETIME_FORMAT).parse(date);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 时间date1和date2的时间差-单位秒
	 *
	 * @param date1
	 * @param date2
	 * @return 秒
	 */
	public static long subtract(Date startDate, Date endDate) {
		long cha = (endDate.getTime() - startDate.getTime()) / 1000;
		return cha;
	}

	/**
	 * 时间date1和date2的时间差-单位秒
	 *
	 * @param date1
	 * @param date2
	 * @return 秒
	 */
	public static long subtract(String startDate, String endDate) {
		long rs = 0;
		try {
			Date start = new SimpleDateFormat(DATETIME_FORMAT).parse(startDate);
			Date end = new SimpleDateFormat(DATETIME_FORMAT).parse(endDate);
			long cha = (end.getTime() - start.getTime()) / 1000;
			rs = cha;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 时间date1和date2的时间差 -单位分钟
	 *
	 * @param date1
	 * @param date2
	 * @return 分钟
	 */
	public static int subtractMinute(String startDate, String endDate) {
		int rs = 0;
		try {
			Date start = new SimpleDateFormat(DATETIME_FORMAT).parse(startDate);
			Date end = new SimpleDateFormat(DATETIME_FORMAT).parse(endDate);
			long cha = (end.getTime() - start.getTime()) / 1000;
			rs = (int) cha / (60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 时间date1和date2的时间差-单位分钟
	 *
	 * @param date1
	 * @param date2
	 * @return 分钟
	 */
	public static int subtractMinute(Date startDate, Date endDate) {
		long cha = endDate.getTime() - startDate.getTime();
		return (int) cha / (1000 * 60);
	}

	/**
	 * 时间date1和date2的时间差-单位小时
	 *
	 * @param date1
	 * @param date2
	 * @return 小时
	 */
	public static int subtractHour(Date startDate, Date endDate) {
		long cha = (startDate.getTime() - endDate.getTime()) / 1000;
		return (int) cha / (60 * 60);
	}

	/**
	 * 时间date1和date2的时间差-单位小时
	 *
	 * @param date1
	 * @param date2
	 * @return 小时
	 */
	public static int subtractHour(String startDate, String endDate) {
		int rs = 0;
		try {
			Date start = new SimpleDateFormat(DATETIME_FORMAT).parse(startDate);
			Date end = new SimpleDateFormat(DATETIME_FORMAT).parse(endDate);
			long cha = (end.getTime() - start.getTime()) / 1000;
			rs = (int) cha / (60 * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 时间date1和date2的时间差-单位天
	 *
	 * @param date1
	 * @param date2
	 * @return 天
	 */
	public static int subtractDay(String startDate, String endDate) {
		int rs = 0;
		try {
			Date start = new SimpleDateFormat(DATETIME_FORMAT).parse(startDate);
			Date end = new SimpleDateFormat(DATETIME_FORMAT).parse(endDate);
			long sss = (end.getTime() - start.getTime()) / 1000;
			rs = (int) sss / (60 * 60 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 时间date1和date2的时间差-单位天
	 *
	 * @param date1
	 * @param date2
	 * @return 天
	 */
	public static int subtractDay(Date startDate, Date endDate) {
		long cha = endDate.getTime() - startDate.getTime();
		return (int) cha / (1000 * 60 * 60 * 24);
	}

	/**
	 * 时间date1和date2的时间差-单位月
	 *
	 * @param date1
	 * @param date2
	 * @return 月
	 */
	public static int subtractMonth(String startDate, String endDate) {
		int result;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(new SimpleDateFormat(DATE_FORMAT).parse(startDate));
			c2.setTime(new SimpleDateFormat(DATE_FORMAT).parse(endDate));
			int year1 = c1.get(Calendar.YEAR);
			int month1 = c1.get(Calendar.MONTH);
			int year2 = c2.get(Calendar.YEAR);
			int month2 = c2.get(Calendar.MONTH);
			if (year1 == year2) {
				result = month2 - month1;
			} else {
				result = 12 * (year2 - year1) + month2 - month1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}

	/**
	 * 时间date1和date2的时间差-单位月
	 *
	 * @param date1
	 * @param date2
	 * @return 月
	 */
	public static int subtractMonth(Date startDate, Date endDate) {
		int result;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		if (year1 == year2) {
			result = month2 - month1;
		} else {
			result = 12 * (year2 - year1) + month2 - month1;
		}
		return result;
	}

	/**
	 * 时间date1和date2的时间差-单位年
	 *
	 * @param date1
	 * @param date2
	 * @return 年
	 */
	public static int subtractYear(String startDate, String endDate) {
		int result;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(new SimpleDateFormat(DATE_FORMAT).parse(startDate));
			c2.setTime(new SimpleDateFormat(DATE_FORMAT).parse(endDate));
			int year1 = c1.get(Calendar.YEAR);
			int year2 = c2.get(Calendar.YEAR);
			result = year2 - year1;
		} catch (ParseException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}

	/**
	 * 时间date1和date2的时间差-单位年
	 *
	 * @param date1
	 * @param date2
	 * @return 年
	 */
	public static int subtractYear(Date startDate, Date endDate) {
		int result;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		result = year2 - year1;
		return result;
	}

	/**
	 * 获取俩个时间的查结果用时秒表示
	 *
	 * @param date1
	 * @param date2
	 * @return 几小时:几分钟:几秒钟
	 * @Summary:此处可以讲计算结果包装成一个结构体返回便于格式化
	 */
	public static String subtractTime(String startDate, String endDate) {
		String result = "";
		try {
			Date start = new SimpleDateFormat(DATETIME_FORMAT).parse(startDate);
			Date end = new SimpleDateFormat(DATETIME_FORMAT).parse(endDate);
			long sss = (end.getTime() - start.getTime()) / 1000;
			int hh = (int) sss / (60 * 60);
			int mm = (int) (sss - hh * 60 * 60) / (60);
			int ss = (int) (sss - hh * 60 * 60 - mm * 60);
			result = hh + ":" + mm + ":" + ss;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取俩个时间的查结果用时秒表示
	 *
	 * @param date1
	 * @param date2
	 * @return 几天-几小时:几分钟:几秒钟
	 * @Summary:此处可以讲计算结果包装成一个结构体返回便于格式化
	 */
	public static String subtractDate(String startDate, String endDate) {
		String result = "";
		try {
			Date start = new SimpleDateFormat(DATETIME_FORMAT).parse(startDate);
			Date end = new SimpleDateFormat(DATETIME_FORMAT).parse(endDate);
			long sss = (end.getTime() - start.getTime()) / 1000;
			int dd = (int) sss / (60 * 60 * 24);
			int hh = (int) (sss - dd * 60 * 60 * 24) / (60 * 60);
			int mm = (int) (sss - dd * 60 * 60 * 24 - hh * 60 * 60) / (60);
			int ss = (int) (sss - dd * 60 * 60 * 24 - hh * 60 * 60 - mm * 60);
			result = dd + "-" + hh + ":" + mm + ":" + ss;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取俩个时间之前的相隔的天数
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static int subDay(Date startTime, Date endTime) {
		int days = 0;
		Calendar can1 = Calendar.getInstance();
		can1.setTime(startTime);
		Calendar can2 = Calendar.getInstance();
		can2.setTime(endTime);
		int year1 = can1.get(Calendar.YEAR);
		int year2 = can2.get(Calendar.YEAR);

		Calendar can = null;
		if (can1.before(can2)) {
			days -= can1.get(Calendar.DAY_OF_YEAR);
			days += can2.get(Calendar.DAY_OF_YEAR);
			can = can1;
		} else {
			days -= can2.get(Calendar.DAY_OF_YEAR);
			days += can1.get(Calendar.DAY_OF_YEAR);
			can = can2;
		}
		for (int i = 0; i < Math.abs(year2 - year1); i++) {
			days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
			can.add(Calendar.YEAR, 1);
		}

		return days;
	}

	/**
	 * 获取俩个时间之前的相隔的天数
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static int subDay(String startTime, String endTime) {
		int days = 0;
		try {
			Date date1 = new SimpleDateFormat(DATE_FORMAT).parse(new SimpleDateFormat(DATE_FORMAT).format(new SimpleDateFormat(DATETIME_FORMAT).parse(startTime)));
			Date date2 = new SimpleDateFormat(DATE_FORMAT).parse(new SimpleDateFormat(DATE_FORMAT).format(new SimpleDateFormat(DATETIME_FORMAT).parse(endTime)));
			Calendar can1 = Calendar.getInstance();
			can1.setTime(date1);
			Calendar can2 = Calendar.getInstance();
			can2.setTime(date2);
			int year1 = can1.get(Calendar.YEAR);
			int year2 = can2.get(Calendar.YEAR);

			Calendar can = null;
			if (can1.before(can2)) {
				days -= can1.get(Calendar.DAY_OF_YEAR);
				days += can2.get(Calendar.DAY_OF_YEAR);
				can = can1;
			} else {
				days -= can2.get(Calendar.DAY_OF_YEAR);
				days += can1.get(Calendar.DAY_OF_YEAR);
				can = can2;
			}
			for (int i = 0; i < Math.abs(year2 - year1); i++) {
				days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
				can.add(Calendar.YEAR, 1);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 返回俩个时间在时间段(例如每天的08:00-18:00)的时长-单位秒
	 *
	 * @param startDate
	 * @param endDate
	 * @param timeBurst
	 *            只就按该时间段内的08:00-18:00时长
	 * @return 计算后的秒数
	 * @throws ParseException
	 * @summary 格式错误返回0
	 */
	public static long subtimeBurst(String startDate, String endDate, String timeBurst) throws ParseException {
		Date start = new SimpleDateFormat(DATETIME_FORMAT).parse(startDate);
		Date end = new SimpleDateFormat(DATETIME_FORMAT).parse(endDate);
		return subtimeBurst(start, end, timeBurst);
	}

	/**
	 * 返回俩个时间在时间段(例如每天的08:00-18:00)的时长-单位秒
	 *
	 * @param startDate
	 * @param endDate
	 * @param timeBurst
	 *            只就按该时间段内的08:00-18:00时长
	 * @return 计算后的秒数
	 * @throws ParseException
	 */
	public static long subtimeBurst(Date startDate, Date endDate, String timeBurst) throws ParseException {
		long second = 0;
		Pattern p = Pattern.compile("^\\d{2}:\\d{2}-\\d{2}:\\d{2}");
		Matcher m = p.matcher(timeBurst);
		boolean falg = false;
		if (startDate.after(endDate)) {
			Date temp = startDate;
			startDate = endDate;
			endDate = temp;
			falg = true;
		}
		if (m.matches()) {
			String[] a = timeBurst.split("-");
			int day = subDay(startDate, endDate);
			if (day > 0) {
				long firstMintues = 0;
				long lastMintues = 0;
				long daySecond = 0;
				String strDayStart = new SimpleDateFormat(DATE_FORMAT).format(startDate) + " " + a[0] + ":00";
				String strDayEnd = new SimpleDateFormat(DATE_FORMAT).format(startDate) + " " + a[1] + ":00";
				Date dayStart = new SimpleDateFormat(DATETIME_FORMAT).parse(strDayStart);
				Date dayEnd = new SimpleDateFormat(DATETIME_FORMAT).parse(strDayEnd);
				daySecond = subtract(dayStart, dayEnd);
				if ((startDate.after(dayStart) || startDate.equals(dayStart)) && startDate.before(dayEnd)) {
					firstMintues = (dayEnd.getTime() - startDate.getTime()) / 1000;
				} else if (startDate.before(dayStart)) {
					firstMintues = (dayEnd.getTime() - dayStart.getTime()) / 1000;
				}
				dayStart = new SimpleDateFormat(DATETIME_FORMAT).parse(new SimpleDateFormat(DATE_FORMAT).format(endDate) + " " + a[0] + ":00");
				dayEnd = new SimpleDateFormat(DATETIME_FORMAT).parse(new SimpleDateFormat(DATE_FORMAT).format(endDate) + " " + a[1] + ":00");
				if (endDate.after(dayStart) && (endDate.before(dayEnd) || endDate.equals(dayEnd))) {
					lastMintues = (endDate.getTime() - dayStart.getTime()) / 1000;
				} else if (endDate.after(dayEnd)) {
					lastMintues = (dayEnd.getTime() - dayStart.getTime()) / 1000;
				}
				// 第一天的秒数 + 最好一天的秒数 + 天数*全天的秒数
				second = firstMintues + lastMintues;
				second += (day - 1) * daySecond;
			} else {
				String strDayStart = new SimpleDateFormat(DATE_FORMAT).format(startDate) + " " + a[0] + ":00";
				String strDayEnd = new SimpleDateFormat(DATE_FORMAT).format(startDate) + " " + a[1] + ":00";
				Date dayStart = new SimpleDateFormat(DATETIME_FORMAT).parse(strDayStart);
				Date dayEnd = new SimpleDateFormat(DATETIME_FORMAT).parse(strDayEnd);
				if ((startDate.after(dayStart) || startDate.equals(dayStart)) && startDate.before(dayEnd)
						&& endDate.after(dayStart) && (endDate.before(dayEnd) || endDate.equals(dayEnd))) {
					second = (endDate.getTime() - startDate.getTime()) / 1000;
				} else {
					if (startDate.before(dayStart)) {
						if (endDate.before(dayEnd)) {
							second = (endDate.getTime() - dayStart.getTime()) / 1000;
						} else {
							second = (dayEnd.getTime() - dayStart.getTime()) / 1000;
						}
					}
					if (startDate.after(dayStart)) {
						if (endDate.before(dayEnd)) {
							second = (endDate.getTime() - startDate.getTime()) / 1000;
						} else {
							second = (dayEnd.getTime() - startDate.getTime()) / 1000;
						}
					}
				}
				if ((startDate.before(dayStart) && endDate.before(dayStart))
						|| startDate.after(dayEnd) && endDate.after(dayEnd)) {
					second = 0;
				}
			}
		} else {
			second = (endDate.getTime() - startDate.getTime()) / 1000;
		}
		if (falg) {
			second = Long.parseLong("-" + second);
		}
		return second;
	}

	/**
	 * 时间Date在时间段(例如每天的08:00-18:00)上增加或减去second秒
	 *
	 * @param date
	 * @param second
	 * @param timeBurst
	 * @return 计算后的时间
	 * @Suumary 指定的格式错误后返回原数据
	 */
	public static Date calculate(String date, int second, String timeBurst) {
		Date start = null;
		try {
			start = new SimpleDateFormat(DATETIME_FORMAT).parse(date);
			return calculate(start, second, timeBurst);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 时间Date在时间段(例如每天的08:00-18:00)上增加或减去second秒
	 *
	 * @param date
	 * @param second
	 * @param timeBurst
	 * @return 计算后的时间
	 * @Suumary 指定的格式错误后返回原数据
	 */
	public static Date calculate(Date date, int second, String timeBurst) {
		Pattern p = Pattern.compile("^\\d{2}:\\d{2}-\\d{2}:\\d{2}");
		Matcher m = p.matcher(timeBurst);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		if (m.matches()) {
			String[] a = timeBurst.split("-");
			try {
				Date dayStart = new SimpleDateFormat(DATETIME_FORMAT).parse(new SimpleDateFormat(DATE_FORMAT).format(date) + " " + a[0] + ":00");
				Date dayEnd = new SimpleDateFormat(DATETIME_FORMAT).parse(new SimpleDateFormat(DATE_FORMAT).format(date) + " " + a[1] + ":00");
				int DaySecond = (int) subtract(dayStart, dayEnd);
				int toDaySecond = (int) subtract(dayStart, dayEnd);
				if (second >= 0) {
					if ((date.after(dayStart) || date.equals(dayStart))
							&& (date.before(dayEnd) || date.equals(dayEnd))) {
						cal.setTime(date);
						toDaySecond = (int) subtract(date, dayEnd);
					}
					if (date.before(dayStart)) {
						cal.setTime(dayStart);
						toDaySecond = (int) subtract(dayStart, dayEnd);
					}
					if (date.after(dayEnd)) {
						cal.setTime(day(dayStart, 1));
						toDaySecond = 0;
					}

					if (second > toDaySecond) {
						int day = (second - toDaySecond) / DaySecond;
						int remainder = (second - toDaySecond) % DaySecond;
						cal.setTime(day(dayStart, 1));
						cal.add(Calendar.DAY_OF_YEAR, day);
						cal.add(Calendar.SECOND, remainder);
					} else {
						cal.add(Calendar.SECOND, second);
					}

				} else {
					if ((date.after(dayStart) || date.equals(dayStart))
							&& (date.before(dayEnd) || date.equals(dayEnd))) {
						cal.setTime(date);
						toDaySecond = (int) subtract(date, dayStart);
					}
					if (date.before(dayStart)) {
						cal.setTime(day(dayEnd, -1));
						toDaySecond = 0;
					}
					if (date.after(dayEnd)) {
						cal.setTime(dayEnd);
						toDaySecond = (int) subtract(dayStart, dayEnd);
					}
					if (Math.abs(second) > Math.abs(toDaySecond)) {
						int day = (Math.abs(second) - Math.abs(toDaySecond)) / DaySecond;
						int remainder = (Math.abs(second) - Math.abs(toDaySecond)) % DaySecond;
						cal.setTime(day(dayEnd, -1));
						cal.add(Calendar.DAY_OF_YEAR, Integer.valueOf("-" + day));
						cal.add(Calendar.SECOND, Integer.valueOf("-" + remainder));
					} else {
						cal.add(Calendar.SECOND, second);
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			cal.setTime(date);
		}
		return cal.getTime();
	}

	/**
	 * 判断是否在某个时间段内
	 * 
	 * @param startTime
	 * @param endTime
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static boolean between(String startTime, String endTime, Date date) throws ParseException {
		return between(parse(startTime), parse(endTime), date);
	}

	/**
	 * 判断在某个时间内
	 * 
	 * @param startTime
	 * @param endTime
	 * @param date
	 * @return
	 */
	public static boolean between(Date startTime, Date endTime, Date date) {
		return date.after(startTime) && date.before(endTime);
	}
	
	
	/** 
     * 获取当前系统时间 
     * @return yyyy-MM-dd HH:mm:ss 
     */  
    public static String getCurrentDateTime() {  
        DateTime dt = new DateTime();  
        String time = dt.toString(DATETIME_FORMAT);  
        return time;  
    }  
  
    /** 
     * 获取系统当前时间按照指定格式返回 
     * @param pattern  yyyy/MM/dd hh:mm:a 
     * @return 
     */  
    public static String getCurrentDateTimePattern(String pattern) {  
        DateTime dt = new DateTime();  
        String time = dt.toString(pattern);  
        return time;  
    }  
  
    /** 
     * 获取当前日期 
     * @return 
     */  
    public static String getCurrentDate() {  
        DateTime dt = new DateTime();  
        String date = dt.toString(DATE_FORMAT);  
        return date;  
    }  
  
    /** 
     * 获取当前日期按照指定格式 
     * @param pattern 
     * @return 
     */  
    public static String getCurrentDatePattern(String pattern) {  
        DateTime dt = new DateTime();  
        String date = dt.toString(pattern);  
        return date;  
    }  
  
    /** 
     * 按照时区转换时间 
     * @param date 
     * @param timeZone 时区 
     * @param parrten 
     * @return 
     */  
    public static String format(Date date, TimeZone timeZone, String parrten) {  
        if (date == null) {  
            return null;  
        }  
        SimpleDateFormat sdf = new SimpleDateFormat(parrten);  
        sdf.setTimeZone(timeZone);  
        return sdf.format(date);  
    }  
  
    /** 
     * 获取指定时间 
     * @param year 年 
     * @param month 月 
     * @param day 天 
     * @param hour 小时 
     * @param minute 分钟 
     * @param seconds 秒 
     * @return yyyy-MM-dd HH:mm:ss 
     */  
    public static String getPointTime(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer seconds) {  
        DateTime dt = new DateTime(year, month, day, hour, minute, seconds);  
        String date = dt.toString(DATETIME_FORMAT);  
        return date;  
    }  
  
    /** 
     * 
     * @param year 年 
     * @param month 月 
     * @param day 天 
     * @param hour 小时 
     * @param minute 分钟 
     * @param seconds 秒 
     * @param parrten 自定义格式 
     * @return parrten 
     */  
    public static String getPointTimePattern(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer seconds, String parrten) {  
        DateTime dt = new DateTime(year, month, day, hour, minute, seconds);  
        String date = dt.toString(parrten);  
        return date;  
    }  
  
    /** 
     * 获取指定日期 
     * @param year 
     * @param month 
     * @param day 
     * @return 
     */  
    public static String getPointDate(Integer year, Integer month, Integer day) {  
        LocalDate dt = new LocalDate(year, month, day);  
        String date = dt.toString(DATE_FORMAT);  
        return date;  
    }  
  
    /** 
     * 获取指定日期 返回指定格式 
     * @param year 
     * @param month 
     * @param day 
     * @param parrten 
     * @return 
     */  
    public static String getPointDatParrten(Integer year, Integer month, Integer day, String parrten) {  
        LocalDate dt = new LocalDate(year, month, day);  
        String date = dt.toString(parrten);  
        return date;  
    }  
  
    /** 
     * 获取当前是一周星期几 
     * @return 
     */  
    public static String getWeek() {  
        DateTime dts = new DateTime();  
        String week = null;  
        switch (dts.getDayOfWeek()) {  
        case DateTimeConstants.SUNDAY:  
            week = "星期日";  
            break;  
  
        case DateTimeConstants.MONDAY:  
            week = "星期一";  
            break;  
  
        case DateTimeConstants.TUESDAY:  
            week = "星期二";  
            break;  
        case DateTimeConstants.WEDNESDAY:  
            week = "星期三";  
            break;  
        case DateTimeConstants.THURSDAY:  
            week = "星期四";  
            break;  
        case DateTimeConstants.FRIDAY:  
            week = "星期五";  
            break;  
        case DateTimeConstants.SATURDAY:  
            week = "星期六";  
        default:  
            break;  
        }  
        return week;  
    }  
  
    /** 
     * 获取指定时间是一周的星期几 
     * @param year 
     * @param month 
     * @param day 
     * @return 
     */  
    public static String getWeekPoint(Integer year, Integer month, Integer day) {  
        LocalDate dts = new LocalDate(year, month, day);  
        String week = null;  
        switch (dts.getDayOfWeek()) {  
        case DateTimeConstants.SUNDAY:  
            week = "星期日";  
            break;  
        case DateTimeConstants.MONDAY:  
            week = "星期一";  
            break;  
        case DateTimeConstants.TUESDAY:  
            week = "星期二";  
            break;  
        case DateTimeConstants.WEDNESDAY:  
            week = "星期三";  
            break;  
        case DateTimeConstants.THURSDAY:  
            week = "星期四";  
            break;  
        case DateTimeConstants.FRIDAY:  
            week = "星期五";  
            break;  
        case DateTimeConstants.SATURDAY:  
            week = "星期六";  
            break;  
  
        default:  
            break;  
        }  
        return week;  
    }  
  
    /** 
     * 格式化日期 
     * @param date 
     * @return yyyy-MM-dd HH:mm:ss 
     */  
    public static String format(Date date) {  
        if (date == null) {  
            return null;  
        }  
        return new SimpleDateFormat(DATETIME_FORMAT).format(date);  
    }  
  
    /** 
     * 格式化日期字符串 
     * @param date 日期 
     * @param pattern 日期格式 
     * @return 
     */  
    public static String format(Date date, String pattern) {  
        if (date == null) {  
            return null;  
        }  
        SimpleDateFormat format = new SimpleDateFormat(pattern);  
        return format.format(date);  
    }  
  
    /** 
     * 解析日期 
     * @param date 日期字符串 
     * @param pattern 日期格式 
     * @return 
     */  
    public static Date parse(String date, String pattern) {  
        if (date == null) {  
            return null;  
        }  
        Date resultDate = null;  
        try {  
            resultDate = new SimpleDateFormat(pattern).parse(date);  
        } catch (ParseException e) {  
  
        }  
        return resultDate;  
    }  
  
    /** 
     * 解析日期yyyy-MM-dd HH:mm:ss 
     * @param date 日期字符串 
     * @return 
     */  
    public static Date parse(String date) throws ParseException{  
        if (date == null) {  
            return null;  
        }  
        return new SimpleDateFormat(DATETIME_FORMAT).parse(date);  
    }  
  
    /** 
     * 解析日期 yyyy-MM-dd HH:mm:ss 
     * @param timestamp 
     * @return 
     */  
    public static String format(Long timestamp, String pattern) {  
        String dateStr = "";  
        if (null == timestamp || timestamp.longValue() < 0) {  
            return dateStr;  
        }  
        Date date = new Date(timestamp);  
        SimpleDateFormat format = new SimpleDateFormat(pattern);  
        return format.format(date);  
    }  
  
    /** 
     * 解析日期 yyyy-MM-dd HH:mm:ss 
     * @param timestamp 
     * @return 
     */  
    public static String format(Long timestamp) {  
        String dateStr = "";  
        if (null == timestamp || timestamp.longValue() < 0) {  
            return dateStr;  
        }  
        Date date = new Date(timestamp);  
        SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT);  
        return format.format(date);  
    }  
  
    /** 
     *获取当前时间前几天时间,按指定格式返回 
     * @param days 
     * @return 
     */  
    public static String forwardDay(Integer days, String format) {  
        DateTime dt = new DateTime();  
        DateTime y = dt.minusDays(days);  
        return y.toString(format);  
    }  
  
    /** 
     *获取当前时间前几天时间 
     * @param days 
     * @return 
     */  
    public static Date forwardDay(Integer days) {  
        DateTime dt = new DateTime();  
        DateTime y = dt.minusDays(days);  
        return y.toDate();  
    }  
  
    /** 
     * 获取指定时间之后或者之前的某一天00:00:00 默认返回当天 
     * @param days 
     * @return 
     */  
    public static Date day00(Integer days, String date, String zimeZone) throws Throwable {  
        DateTime dt;  
        TimeZone timeZone;  
        try {  
            if (StringUtils.isBlank(zimeZone)) {  
                timeZone = TimeZone.getDefault();  
            } else {  
                timeZone = TimeZone.getTimeZone(zimeZone);  
            }  
            if (StringUtils.isBlank(date)) {  
                dt = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();  
            } else {  
                dt = new DateTime(date).withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();  
            }  
        } catch (Exception e) {  
            throw new Throwable(e);  
        }  
  
        DateTime y = dt.minusDays(days).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);  
        return y.toDate();  
    }  
  
    /** 
     *获取指定时间之后或者之前的某一天23:59:59 默认返回当天 
     * @param days 偏移量 
     * @return 
     */  
    public static Date day59(Integer days, String date, String zimeZone) throws Throwable {  
        DateTime dt;  
        TimeZone timeZone;  
        try {  
            if (StringUtils.isBlank(zimeZone)) {  
                timeZone = TimeZone.getDefault();  
            } else {  
                timeZone = TimeZone.getTimeZone(zimeZone);  
            }  
            if (StringUtils.isBlank(date)) {  
  
                dt = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();  
            } else {  
                dt = new DateTime(date).withZone(DateTimeZone.forTimeZone(timeZone)).toLocalDateTime().toDateTime();  
            }  
        } catch (Exception e) {  
            throw new Throwable(e);  
        }  
        DateTime y = dt.minusDays(days).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);  
        return y.toDate();  
    }  
  
    /** 
     * 计算两个时间相差多少天 
     * @param startDate 
     * @param endDate 
     * @return 
     */  
    public static Integer diffDay(Date startDate, Date endDate) {  
        if (startDate == null || endDate == null) {  
            return null;  
        }  
        DateTime dt1 = new DateTime(startDate);  
        DateTime dt2 = new DateTime(endDate);  
        int day = Days.daysBetween(dt1, dt2).getDays();  
        return Math.abs(day);  
    }  
  
    /** 
     * 获取某月之前,之后某一个月最后一天,23:59:59 
     * @return 
     */  
    public static Date lastDay(Date date, Integer month) {  
        DateTime dt1;  
        if (month == null) {  
            month = 0;  
        }  
        if (date == null) {  
            dt1 = new DateTime().minusMonths(month);  
        } else {  
            dt1 = new DateTime(date).minusMonths(month);  
        }  
        DateTime lastDay = dt1.dayOfMonth().withMaximumValue().  
                withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);  
        return lastDay.toDate();  
    }  
  
    /** 
     *获取某月月之前,之后某一个月第一天,00:00:00 
     * @return 
     */  
    public static Date firstDay(Date date, Integer month) {  
        DateTime dt1;  
        if (month == null) {  
            month = 0;  
        }  
        if (date == null) {  
            dt1 = new DateTime().minusMonths(month);  
        } else {  
            dt1 = new DateTime(date).minusMonths(month);  
        }  
        DateTime lastDay = dt1.dayOfMonth().withMinimumValue().  
                withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);  
        return lastDay.toDate();  
    }  
  
    public static Date addDay(Date date, int offset) {  
        DateTime dt1;  
        if (date == null) {  
            dt1 = new DateTime().plusDays(offset);  
            return dt1.toDate();  
        }  
        dt1 = new DateTime(date).plusDays(offset);  
        return dt1.toDate();  
  
    }  
  
    /** 
     * 传入日期时间与当前系统日期时间的比较, 
     * 若日期相同，则根据时分秒来返回 , 
     * 否则返回具体日期 
     * @return 日期或者 xx小时前||xx分钟前||xx秒前 
     */  
    public static String getNewUpdateDateString(Date now, Date createDate) {  
        if (now == null || createDate == null) {  
            return null;  
        }  
        Long time = (now.getTime() - createDate.getTime());  
        if (time > (24 * 60 * 60 * 1000)) {  
            return time / (24 * 60 * 60 * 1000) + "天前";  
        } else if (time > (60 * 60 * 1000)) {  
            return time / (60 * 60 * 1000) + "小时前";  
        } else if (time > (60 * 1000)) {  
            return time / (60 * 1000) + "分钟前";  
        } else if (time >= 1000) {  
            return time / 1000 + "秒前";  
        }  
        return "刚刚";  
    } 

}
