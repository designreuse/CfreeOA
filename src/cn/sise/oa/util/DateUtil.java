package cn.sise.oa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 计算两个字符串之间的日期天数
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return
	 * @throws ParseException
	 */
	public static int getDateSpace(String startTime, String endTime){

		int result = 0;
		
		String dateFromat = "yyyy-MM-dd"; //日期格式

		Calendar calst = Calendar.getInstance();
		Calendar caled = Calendar.getInstance();

		try {
			calst.setTime(parseDate(dateFromat, startTime));
			caled.setTime(parseDate(dateFromat, endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);
		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}

	/**
	 * 将字符串日期转换成指定格式的Date日期类型
	 * @param dateFormat 日期格式
	 * @param strDate 日期的字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateFormat, String strDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = sdf.parse(strDate);
		return date;
	}
	
	/**
	 * 将Date类型转换成指定格式的字符串类型
	 * @param dateFormat 日期类型
	 * @param date 日期
	 * @return
	 */
	public static String dateToString(String dateFormat, Date date){
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		String resultDate = sf.format(date);
		return resultDate;
	}
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 字符串时间转LONG
	 * @param sdate
	 * @return
	 */
	public static long string2long(String sdate){
		if(sdate.length() < 11){
			sdate = sdate + " 00:00:00";
		}
		SimpleDateFormat sdf= new SimpleDateFormat(DEFAULT_PATTERN);
		Date dt2 = null;
		try {
			dt2 = sdf.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//继续转换得到秒数的long型
		long lTime = dt2.getTime() / 1000;
		return lTime;
	}
	
	/**
	 * LONG时间转字符串
	 * @param ldate
	 * @return
	 */
	public static String long2string(long ldate){
		SimpleDateFormat sdf= new SimpleDateFormat(DEFAULT_PATTERN);
		//前面的ldate是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		java.util.Date dt = new Date(ldate * 1000);  
		String sDateTime = sdf.format(dt);  //得到精确到秒的表示
		if(sDateTime.endsWith("00:00:00")){
			sDateTime = sDateTime.substring(0,10);
		}
		return sDateTime;
	}
	
	
}
