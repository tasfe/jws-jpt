package ${package}.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {

	private static final Log logger = LogFactory.getLog(DateUtil.class);

	private static String dateFormat = null;

	private static String timeFormat = " HH:mm:ss.S";

	private static synchronized String getDateFormat() {
		if (dateFormat == null) {
			dateFormat = JptLocalizedTextUtil.getInstance().getDateFormat();
		}
		return dateFormat;
	}

	public static String getDateTimeFormat() {
		return DateUtil.getDateFormat() + timeFormat;
	}

	public static Date str2date(String str) throws ParseException {
		return str2date(getDateFormat(), str);
	}

	public static Date str2datetime(String str) throws ParseException {
		return str2date(getDateTimeFormat(), str);
	}

	public static String date2str(Date date) {
		return datetime2str(getDateFormat(), date);
	}

	public static String time2str(Date time) {
		return datetime2str(timeFormat, time);
	}

	public static String datetime2str(Date datetime) {
		return datetime2str(getDateTimeFormat(), datetime);
	}

	public static Date str2date(String pattern, String str)
			throws ParseException {
		if (logger.isDebugEnabled()) {
			logger.debug("converting '" + str + "' to date with pattern '"
					+ pattern + "'");
		}
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		return new SimpleDateFormat(pattern).parse(str);
	}

	public static String datetime2str(String pattern, Date datetime) {
		if (logger.isDebugEnabled()) {
			logger.debug("converting '" + datetime
					+ "' to string with pattern '" + pattern + "'");
		}

		if (datetime == null) {
			return "";
		}
		return new SimpleDateFormat(pattern).format(datetime);
	}

	public static Date getYearFrom(int year) {
		return getMonthFrom(year, 1);
	}

	public static Date getYearTo(int year) {
		return getMonthTo(year, 12);
	}

	public static Date getQuarterFrom(int year, int quarter) {
		return getMonthFrom(year, (quarter - 1) * 3 + 1);
	}

	public static Date getQuarterTo(int year, int quarter) {
		return getMonthTo(year, quarter * 3);
	}

	public static Date getMonthFrom(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getMonthTo(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date getDayFrom(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getDayTo(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date getDayOfWeekFrom(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getDayOfWeekTo(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static int getYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.YEAR);
	}

	public static Date now() {
		return new Date();
	}

	public static int diff(Date date1, Date date2) {
		return (int) ((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
	}

	public static int diff(Date date) {
		return diff(new Date(), date);
	}

	public static Date addYear(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		return cal.getTime();
	}

	public static Date addMonth(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}

	public static Date addMonth(int amount) {
		return addMonth(new Date(), amount);
	}
}
