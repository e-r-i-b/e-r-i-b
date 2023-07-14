package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.enums.ExecutionEventType;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigInteger;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 06.09.2005
 * Time: 15:26:12
 */
public final class DateHelper
{
	private static final Log log = LogFactory.getLog(DateHelper.class);
	private static final int RAW_OFFSET_TO_HOUR     = 1000*60*60;

	public static final int MILLISECONDS_IN_DAY     = 60*60*24*1000;    //����������� � ���
	public static final int MILLISECONDS_IN_HOUR    = 3600000;          //����������� � ����
	public static final int MILLISECONDS_IN_MINUTE  = 1000*60;          //����������� � ������
	public static final int MILLISECONDS_IN_SECOND  = 1000;             //����������� � �������

	public static final String BEGIN_DAY_TIME = "00:00:00";  // ����� ������ ���
	public static final String END_DAY_TIME = "23:59:59"; // ����� ����� ���
	public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";

	public static final String TIME_PATTERN = "HH:mm";
	public static final String DATE_PATTERN = "dd.MM";
	public static final String DATE_TIME_PATTERN = "HH:mm dd.MM";
	public static final String TIME_FORMAT = "HH:mm:ss";
	private static final String XML_TIME_FORMAT = "HH:mm:ss.SSS";
    private static final String XML_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	private static final String XML_DATE_FORMAT_TIME_ZONE = "yyyy-MM-dd";
	private static final String XML_DATE_FORMAT = "dd.MM.yyyy";
	private static final String XML_DATE_TIME_WITHOUT_MILLISECONDS = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String XML_DATE_TIME_RUSSIAN_FORMAT = "dd.MM.yyyy'T'HH:mm:ss.SSSS";
    private static final String DOT = ".";


	public static final String DATE_ISO8601_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS";
	public static final String DATE_ISO8601_FORMAT_24_HOUR = "yyyy-MM-dd HH:mm:ss.SSS";
	private static Pattern XML_DATE_PATTERN = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");

	public static final int MONTH_IN_QUARTER = 3;
	public static final int MONTH_IN_HALF_YEAR = 6;

	private static final String[] daysOfWeek = {"�������������", "���������", "������", "���������", "��������", "��������", "������������"};

	private static final String[] months = {"������", "�������", "�����",
											"������", "���",     "����",
											"����",   "�������", "��������",
											"�������","������",  "�������"};


	private static final Calendar MJD_START_DATE = getMJDStartDate();

	private DateHelper ()
	{
	}

	private static Calendar getMJDStartDate()
	{
		Calendar result = Calendar.getInstance();
		//noinspection MagicNumber
		result.set(1858, Calendar.NOVEMBER, 17);
		return result;
	}

	/**
	 * @return ��������� 1-�� ����� ���������� ������
	*/
	public static Calendar getPreviousWeek ()
	{
		Calendar current = getCurrentDate();

		int week = current.get(Calendar.WEEK_OF_MONTH);

		current.set(Calendar.WEEK_OF_MONTH, week - 1);
		current.get(Calendar.MONTH);
		
		return current;
	}

	/**
	 * ��������� ���� �����������  ��� ������������ ������� ����. �. �. ���� ������� ���� ����� 25.05.2010,
	 * �� ���������� ���� ����� ����� 24.05.2010.
	 * @param dateOfReference ������� ����
	 * @return ���� ���������� ������
	 */
	public static Calendar getPreviousDay(Calendar dateOfReference)
	{
		Calendar reference = Calendar.getInstance();
		reference.setTime(dateOfReference.getTime());
		reference.add(Calendar.DAY_OF_YEAR, -1);
		reference.get(Calendar.DAY_OF_YEAR);

		return reference;
	}

	/**
	 * ��������� ���� �����������  ��� ������������ ������� ����. �. �. ���� ������� ���� ����� 25.05.2010,
	 * �� ���������� ���� ����� ����� 24.05.2010.
	 * @return ���� ����������� ���
	 */
	public static Calendar getPreviousDay()
	{
		return getPreviousDay(1);
	}

	/**
	 * ��������� ���� - n  ����  ������������ ������� ����.
	 * @param day ������� ���� �����
	 * @return ���� ����������� ���
	 */
	public static Calendar getPreviousDay(int day)
	{
		Calendar reference = Calendar.getInstance();
		reference.add(Calendar.DAY_OF_YEAR, - day);

		return reference;
	}

	/**
	 * @return ���� ����������� ���
	 */
	public static Calendar getNextDay()
	{
		Calendar reference = Calendar.getInstance();
		reference.add(Calendar.DAY_OF_YEAR, 1);

		return reference;
	}

	/**
	 * ��������� ���� "hours ����� �����" ������������ ������� ����. �. �. ���� ������� ���� ����� 25.05.2010 14:00,
	 * �� ���������� ���� ����� ����� 25.05.2010 14-hours:00.
	 * @return ���� �� ��������� �� hours ����� ������������ �������� �������
	 */
	public static Calendar getPreviousHours(int hours)
	{
		Calendar reference = Calendar.getInstance();
		reference.add(Calendar.HOUR_OF_DAY, -hours);

		return reference;
	}

	/**
	 * ��������� ���� �����������  n ��� ������������ ������� ����. �. �. ���� ������� ���� ����� 25.05.2010,
	 * a n = 5
	 * �� ���������� ���� ����� ����� 20.05.2010.
	 * @param dateOfReference ������� ����
	 * @param n ����
	 * @return
	 */
	public static Calendar getPreviousNDay(Calendar dateOfReference,int n)
	{
		Calendar reference = Calendar.getInstance();
		reference.setTime(dateOfReference.getTime());
		reference.add(Calendar.DAY_OF_YEAR, - n);
		reference.get(Calendar.DAY_OF_YEAR);

		return reference;
	}


	/**
	 * ��������� ���� ���������� ������ ������������ ������� ����. �. �. ���� ������� ���� ����� 25.05.2010,
	 * �� ���������� ���� ����� ����� 18.05.2010.
	 * @param dateOfReference ������� ����
	 * @return ���� ���������� ������
	 */
	public static Calendar getPreviousWeek(Calendar dateOfReference)
	{
		Calendar reference = Calendar.getInstance();
		reference.setTime(dateOfReference.getTime());
		reference.add(Calendar.WEEK_OF_YEAR, -1);
		reference.get(Calendar.WEEK_OF_YEAR);

		return reference;
	}

	 /**
	 * ��������� ���� ����������� ���� ������������ ������� ����. �. �. ���� ������� ���� ����� 25.05.2010,
	 * �� ���������� ���� ����� ����� 25.05.2009.
	 * @param dateOfReference ������� ����
	 * @return ���� ���������� ������
	 */
	public static Calendar getPreviousYear(Calendar dateOfReference)
	{
		Calendar reference = Calendar.getInstance();
		reference.setTime(dateOfReference.getTime());
		reference.add(Calendar.YEAR, -1);
	    reference.get(Calendar.YEAR);
		
		return reference;
	}

	/**
	 * �������� ���� �� ��������� �������
	 * @param date ������� ����
	 * @param amount ���������� �������
	 * @return ���������� ����
	 */
	public static Calendar addMonths(Calendar date, int amount)
	{
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(date.getTime());
		newDate.add(Calendar.MONTH, amount);
		newDate.get(Calendar.MONTH);

		return newDate;
	}
	/**
	 * ��������� ���� ����������� ������ ������������ ������� ����. �. �. ���� ������� ���� ����� 22.05.2010,
	 * �� ���������� ���� ����� ����� 22.04.2010.
	 * @param dateOfReference ������� ����
	 * @return ���� ����������� ������
	 */
	public static Calendar getPreviousMonth (Calendar dateOfReference)
	{
		return addMonths(dateOfReference, -1);
	}

	/**
	 * @return ��������� 1-�� ����� ����������� ������
	 */
	public static Calendar getPreviousMonth ()
	{
		Calendar newDate = DateHelper.getCurrentDate();
		newDate.add(Calendar.MONTH, -1);
		newDate.set(Calendar.DATE, 1);
		newDate.get(Calendar.MONTH); //�� �������, ����� �� ���������� ��������
		return newDate;
	}

	/**
	 * �������� ���� � ������� �� ������������ ���������� ����������
	 * @param milliseconds ���������� ����������
	 * @return ���� � �������
	 */
	public static Calendar getPreviousMilliSeconds(long milliseconds)
	{
		Calendar res = Calendar.getInstance();
		res.setTimeInMillis(res.getTimeInMillis() - milliseconds);
		res.getTimeInMillis();
		return res;
	}

	@Deprecated //TODO ������ (��� ���?)
	public static Calendar getOperDate ()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(1999, 0, 1);
		return calendar;
	}

	/**
	 * @return ���������� ������� ���� (����� 0:00:00 !!!)
	 */
	public static Calendar getCurrentDate ()
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar;
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������� ����.", e);
			return null;
		}
	}

	/**
	 * ���������� ������� ���
	 * @return ������� ���
	 */
	public static int getCurrentYear()
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			return calendar.get(Calendar.YEAR);
		}
		catch (Exception e)
		{
			log.error("������ ��������� �������� �������� ����.", e);
			return 0;
		}
	}

	/**
	 * @return ��������� 1-�� ����� ������
	 */
	public static Calendar getFirstDayOfMonth (Calendar date)
	{
		return DateUtils.truncate(date, Calendar.MONTH);
	}

	/**
	 * ���������� ���� ���������� ��� ������
	 * @return ���� ���������� ��� ������
	 */
	public static Calendar getLastDayOfMonth(Calendar date)
	{
		Calendar newDate = (Calendar) date.clone();
		newDate.set(Calendar.DAY_OF_MONTH, newDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		newDate.get(Calendar.MONTH);

		return newDate;
	}

	/**
	 * @return �������� ����� ������ �� �������
	 */
	@Deprecated //TODO 1 - ��� ������� ���������� 2
	public static String toFormDate ( Calendar date )
	{
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
		dfs.setMonths(months);
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM", dfs);
		return sdf.format(date.getTime());
	}

	/**
	 * ����� ������ �� �������.
	 * @param monthNumber - ����� ������(1..12)
	 * @return ����� � ����������� ������
	 */
	public static String monthNumberToString(int monthNumber)
	{
		return months[monthNumber-1];
	}

	/**
	 * ����� ������ �� �������.
	 * @param date ����
	 * @return ����� � ����������� ������
	 */
	public static String monthToString(Calendar date)
	{
		if (date == null)
		{
			return StringUtils.EMPTY;
		}
		return DateHelper.monthNumberToString(date.get(Calendar.MONTH) + 1);
	}

	/**
	 * �������������� ���� � ������ ���� - 01 ������ 2014 ����
	 * @param date ����
	 * @return ������ ����
	 */
	public static String formatDateWithMonthString(Calendar date)
	{
		if (date == null)
			return "";
		return date.get(Calendar.DATE) + " " + monthToString(date) + " "  + date.get(Calendar.YEAR);
	}

	public static String calendarToPromoFormat(Calendar calendar)
	{
		String result = String.valueOf(calendar.get(Calendar.DATE)) + " " + monthToString(calendar);

		int currentYear  = Calendar.getInstance().get(Calendar.YEAR);
		if (currentYear != calendar.get(Calendar.YEAR))
		{
			result += " " + calendar.get(Calendar.YEAR);
		}

		return result;
	}

	/**
	 * ���� ������ �� �������.
	 * @param dayNumber - ����� ��� �� Calendar (1-SUNDAY..7-SATURDAY)
	 * @return ���� ������ � ����������� ������
	 */
	public static String dayNumberToString(int dayNumber)
	{
		return daysOfWeek[(dayNumber + 5) % 7];
	}

	/**
	 *
	 * ����������� ���� � ������ ���� dd MM(��������) yyyy
	 *
	 * @param  calendar ������������� ����
	 * @return String
	 */
	public static String formatDateToMonthInWords(Calendar calendar)
	{
		return customFormatDateToMonthInWords(calendar, "dd MMMM yyyy");
	}

	public static String customFormatDateToMonthInWords(Calendar calendar, String format)
	{
		if (calendar == null)
            return "";

		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
		dateFormatSymbols.setMonths(months);

		SimpleDateFormat  dateFormat = new SimpleDateFormat(format, dateFormatSymbols);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * �������� ����� ��� �� ����
	 * @param date ����
	 * @return ����� ���
	 */
	public static String getDayOfDate(Calendar date)
	{
		if (date == null)
		{
			return "";
		}
		try
		{
			DateFormat dayFormat = new SimpleDateFormat("dd");
			return dayFormat.format(date.getTime());
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * �������� ����� ��� �� ���� ��� �������� ���� (1, � �� 01)
	 * @param date ����
	 * @return ����� ���
	 */
	public static String getDayOfDateWithoutNought(Calendar date)
	{
		return getDayOfDate(date).replaceFirst("^0*", "");
	}

	/**
	 * �������������� ���� � ������ ������� dd ����� yyyy, ��� �������� ������ � ����������� ������
	 * @param date ���� � ��������������
	 * @return ���������� ������ � ����� ��� ������ ������, ���� ���� ���� null
	 */
	public static String formatDateWithStringMonth( Calendar date )
	{
		if (date == null)
			return "";
		
		try
		{
			StringBuilder builder = new StringBuilder();
			DateFormat dayFormat = new SimpleDateFormat("dd");
			DateFormat yearFormat = new SimpleDateFormat("yyyy");
			builder.append(dayFormat.format(date.getTime()));
			builder.append(" ");
			builder.append(toFormDate(date));
			builder.append(" ");
			builder.append(yearFormat.format(date.getTime()));
			return builder.toString();
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * �������������� ������� ���� Calendar � ������ ������� "dd month", ��� dd - ����� (�������), month - ����� (�������, � ����������� ������)
	 * @param date - ������������� ����
	 * @return ������ � ��������� ���� �������
	 */
	public static String formatDayWithStringMonth(Calendar date)
	{
		if (date == null)
			return "";
		try
		{
			StringBuilder builder = new StringBuilder();
			DateFormat dayFormat = new SimpleDateFormat("dd");
			builder.append(dayFormat.format(date.getTime()));
			builder.append(" ");
			builder.append(toFormDate(date));
			return builder.toString();
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * �������������� ������� ���� �� yyyy-mm-ddThh:mm:ss � dd.mm.yyyy
	 * @param date - ������������� ����
	 * @return ������ � ��������� ���� �������
	 */
    public static String formatDateFroSubscription(String date)
	{
		if (date == null)
			return "";
        if (!date.contains("-"))
            return date;
		try
		{
			StringBuffer builder = new StringBuffer(10);
			builder.append(date.substring(8,10));
			builder.append(DOT);
			builder.append(date.substring(5,7));
			builder.append(DOT);
			builder.append(date.substring(0,4));
			return builder.toString();
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * �������������� ������� ���� Calendar � ������ ������� "dd month", ��� dd - ����� (�������), month - ����� (�������, � ����������� ������), �� ��� ������� �����
	 * @param date - ������������� ����
	 * @return ������ � ��������� ���� �������
	 */
	public static String formatDayWithStringMonthWithoutNought(Calendar date)
	{
		return formatDayWithStringMonth(date).replaceFirst("^0*", "");
	}

	/**
	 * ���������� �������� ������ � ����������� ������
	 * @param month ����� ������ (�� 1 �� 12)
	 * @return �������� ������ � ����������� ������
	 */
	public static String dateMonthToString(int month)
	{
		if (month <= 0)
			return "";

		Calendar date = DateHelper.getCurrentDate();
		date.set(Calendar.DATE, 1);
		date.set(Calendar.MONTH, month-1);
		return toFormDate(date);
	}

	/**
	 * ����������� ���� � ������ ����
	 * dd.MM
	 * @param actualDate ������������� ����
	 * @return ��������� ������������� ����
	 */
	public static String formatDateDDMM(Calendar actualDate)
	{
		if (actualDate == null)
			return org.apache.commons.lang.StringUtils.EMPTY;

		try
		{
			String timePattern = DATE_PATTERN;
			return new SimpleDateFormat(timePattern).format(actualDate.getTime());
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}


	/**
	 * �������������� ���� � ������ � �������, ��������� �� �� ��������
	 * ���� ���� ��������� � �����������, �� �� ������ ����� ������ ������� "������� � hh:mm"
	 * ���� ���� ��������� �� ���������, �� �� ������ ����� ������ ������� "����� � hh:mm"
	 * ���� ���� ��������� � �������� ����, �� �� ������ ����� ������ ������� "dd.MM"
	 * ���� ���� �� ��������� � �������� ����, �� �� ������ ����� ������ ������� "dd.MM.yyyy"
	 * @param date ������������� ����
	 * @param withTime ����� �� ���������� ����� ��� ��������, ����������� ����� � �������
	 * @param needYear ����� �� ���������� '�.' ��� ��������, ����������� �� � ������� ����
	 * @return ������ ������������� �������
	 */
	public static String formatDateDependsOnSysDate(Calendar date, boolean withTime, boolean needYear)
	{
		if (date == null)
			return "";

		try
		{
			Date actualDate = date.getTime();
			Calendar sysDate = DateHelper.getCurrentDate();
			StringBuilder builder = new StringBuilder();
			// ���� ��� ��������� � �������
			if (isSameYear(date, sysDate))
			{
				// ���� ���� ��������� � �������
				if (DateUtils.isSameDay(actualDate, sysDate.getTime()))
				{
					builder.append("�������");
					if (withTime)
					{
						builder.append(" � ");
						builder.append(new SimpleDateFormat("HH:mm").format(actualDate));
					}
				}
				//���� ���� ��������� �� ���������
				else if (DateUtils.isSameDay(actualDate, DateUtils.addDays(sysDate.getTime(), -1)))
				{
					builder.append("�����");
					if (withTime)
					{
						builder.append(" � ");
						builder.append(new SimpleDateFormat("HH:mm").format(actualDate));
					}
				}
				else
				{
					return new SimpleDateFormat("dd.MM").format(actualDate);
				}
			}
			else
			{
				builder.append(new SimpleDateFormat("dd.MM.yyyy").format(actualDate));
				if (needYear)
				{
					builder.append("�.");
				}
			}
			return builder.toString();
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * ����������� ���� � ������ ����
	 * 1. HH:mm dd.MM - ���� ���� �� ��������� � ������� ����
	 * 1. HH:mm - ���� ���� ��������� � ������� ����
	 * @param actualDate ������������� ����
	 * @return ��������� ������������� ����
	 */
	public static String formatDateDependsOnSysDate(Date actualDate)
	{
		if (actualDate == null)
			return org.apache.commons.lang.StringUtils.EMPTY;

		try
		{
			Calendar sysDate = DateHelper.getCurrentDate();
			String timePattern = DATE_TIME_PATTERN;
			// ���� ���� ��������� � �������
			if (DateUtils.isSameDay(actualDate, sysDate.getTime()))
				timePattern = TIME_PATTERN;

			return new SimpleDateFormat(timePattern).format(actualDate);
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * ���������, ����������� �� ��� ������� ���� Calendar � ������ ����
	 * @param date1 ������ ����, �.�. not null
	 * @param date2 ������ ����, �.�. not null
	 * @return true, ���� ��� ���� ��������� � ������ ����
	 * @throws IllegalArgumentException, ���� ����� �� ��� null
	 */
	private static boolean isSameYear(Calendar date1, Calendar date2)
	{
		if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("������� �������� �� ������ ���� null");
        }
		return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
	}

	/**
	 * �������������� ���� � ������ ������� YYYY-MM-DD
	 * @param date ���� � ��������������
	 * @return ���������� ������ � ����� ��� ������ ������, ���� ���� ���� null
	 */
	public static String formatDateToString( Calendar date )
	{
		if (date == null)
			return "";

		try
		{
			StringBuilder builder = new StringBuilder();
			DateFormat yearFormat = new SimpleDateFormat("yyyy");
			DateFormat monthFormat = new SimpleDateFormat("MM");
			DateFormat dayFormat = new SimpleDateFormat("dd");
			builder.append(yearFormat.format(date.getTime()));
			builder.append("-");
			builder.append(monthFormat.format(date.getTime()));
			builder.append("-");
			builder.append(dayFormat.format(date.getTime()));
			return builder.toString();
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * ����������� ���� �� �������
	 * @param date ������������� ����
	 * @param pattern ������
	 * @return ��������� ������������� � ������������ � ��������
	 */
	public static String formatDateToStringOnPattern( Calendar date, String pattern)
	{
		if (date == null)
			return "";

		try
		{
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(date.getTime());
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * �������������� ���� � ������ ������� ��/��/����
	 * @param date ���� � ��������������
	 * @return ���������� ������ � ����� ��� ������ ������, ���� ���� ���� null
	 */
	public static String formatDateToStringWithSlash( Calendar date )
	{
		if (date == null)
			return "";

		try
		{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.format(date.getTime());
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * �������������� ���� ��/��/���� hh24:mm:ss.
	 * @param date ������� ������
	 * @return �������������� ������
	 */
	public static String formatDateToStringWithSlash2 ( Calendar date )
	{
		if (date == null)
			return "";

		try
		{
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
			return dateFormat.format(date.getTime());
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * �������������� ���� � ������ ������� ��.��.����
	 * @param date ���� � ��������������
	 * @return ���������� ������ � ����� ��� ������ ������, ���� ���� ���� null
	 */
	public static String formatDateToStringWithPoint( Calendar date )
	{
		if (date == null)
			return "";

		try
		{
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			return dateFormat.format(date.getTime());
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * �������������� ���� � ������ ������� DDMM
	 * @param date ���� � ��������������
	 * @return ���������� ������ � ����� ��� ������ ������, ���� ���� ���� null
	 */
	public static String formatToDateToString( Calendar date )
	{
		if (date == null)
			return "";

		try
		{
			StringBuilder builder = new StringBuilder();
			DateFormat dayFormat = new SimpleDateFormat("dd");
			DateFormat monthFormat = new SimpleDateFormat("MM");

			builder.append(dayFormat.format(date.getTime()));
			builder.append(monthFormat.format(date.getTime()));
			return builder.toString();
		}
		catch (Exception e)
		{
			log.error("������ �������������� ����.",e);
			return "";
		}
	}

	/**
	 * �������������� ���� � ������ ������� ddmmyy
	 * @param date ���� � ��������������
	 * @return ���������� ������ � ����� ��� ������ ������, ���� ���� ���� null
	 */
	public static String formatDateDDMMYY( Calendar date )
	{
		if (date == null)
			return "";

		return new SimpleDateFormat("ddMMyy").format(date.getTime());

	}

	/**
	 * �������������� ���� � ������ ������� yyyyMMdd
	 * @param date ���� � ��������������
	 * @return ���������� ������ � ����� ��� ������ ������, ���� ���� ���� null
	 */
	public static String formatDateYYYYMMDD( Calendar date )
	{
		if (date == null)
			return "";

		return new SimpleDateFormat("yyyyMMdd").format(date.getTime());

	}

	/**
	 * @return ����������� Date � Calendar
	 */
	public static Calendar toCalendar ( Date date )
	{
		if (date==null)
		{
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Date toDate ( Calendar calendar )
	{
		return calendar==null ? null : calendar.getTime();
	}

	/**
	 * @return ����������� Date � String ������� dd.MM.yyyy
	 */
	@Deprecated // ����������� String.format
	public static String toString ( Date date )
	{
		if (date==null)
		{
			return "";
		}
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.format(date);
	}

	/**
	 * �������������� ���� ��.��.���� hh24:mm:ss.
	 * @param date ������� ������
	 * @return �������������� ������
	 */
	public static String formatDateToString2 ( Calendar date )
	{
		if (date == null)
		{
			return "";
		}
		return String.format("%1$td.%1$tm.%1$tY %1$tT", date);
	}

	@Deprecated
	// ����������� String.format
	public static String toStringTime ( Date date )
	{
		if (date==null)
		{
			return "";
		}
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		return df.format(date);
	}

	/**
	 * �������������� ���� � ������(��� ������)
	 * @param date ����, ������� ����� �������������
	 * @return ���� ����� ������
	 */
	public static String toStringTimeWithoutSecond ( Date date )
	{
		if (date==null)
		{
			return "";
		}
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return df.format(date);
	}

	/**
	 * �������������� ���� ����� ������ � ��� Date
	 * @param date ���� ����� ������ ��� ������
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseStringTime ( String date ) throws ParseException
	{
		return parseStringTime(date, "dd.MM.yyyy HH:mm:ss");
	}

	/**
	 * �������������� ���� ����� ������ ��� ������ � ��� Date
	 * @param date ���� ����� ������ ��� ������
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseStringTimeWithoutSecond( String date ) throws ParseException
	{
		return parseStringTime(date, "dd.MM.yyyy HH:mm");
	}

    /**
     * @param date ����
     * @return ������ ���� yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static String getXmlDateTimeFormat(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat(XML_DATE_TIME_FORMAT);
        return dateFormat.format(date);
    }

	/**
	 * @param date ����
	 * @return ������ ���� yyyy-MM-dd'T'HH:mm:ss
	 */
	public static String getXmlDateTimeWithoutMillisecondsFormat(Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat(XML_DATE_TIME_WITHOUT_MILLISECONDS);
		return dateFormat.format(date);
	}

	/**
	 * �������������� ���� � ������ ������� dd/MM/yyyy HH:mm:ss
	 * @param date ���� � ��������������
	 * @return
	 */
	public static String getSimpleDateFormatWithHhMmSs(Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT + " " + TIME_FORMAT);
		return dateFormat.format(date);
	}

	/**
     * @param date ����
     * @return ������ ���� dd.MM.yyyy'T'HH:mm:ss.SSSS
     */
    public static String getXmlDateTimeRussianFormat(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat(XML_DATE_TIME_RUSSIAN_FORMAT);
        return dateFormat.format(date);
    }

    /**
     *
     * @param date ����
     * @return ������ ���� yyyy-MM-dd
     */
    public static String getXmlDateFormat(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat(XML_DATE_FORMAT_TIME_ZONE);
        return dateFormat.format(date);
    }

	/**
     *
     * @param date ����
     * @return ������ ���� dd.MM.yyyy
     */
    public static String getDateFormat(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat(XML_DATE_FORMAT);
        return dateFormat.format(date);
    }

	/**
  *
  * @param date ����
  * @return ������ ���� HH:mm:ss.SSS
  */
 public static String getXmlTimeFormat(Date date)
 {
     DateFormat dateFormat = new SimpleDateFormat(XML_TIME_FORMAT);
     return dateFormat.format(date);
 }

	/**
	 *
	 * @param date - ����
	 * @return - ��������� ������������� ����, ����������������� ��� HH:mm:ss;
	 */
	public static String getTimeFormat(Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
		return dateFormat.format(date);
	}

	/**
	 * @param time �����
	 * @return HH:mm
	 */
	public static String getTime(Time time)
	{
		DateFormat dateFormat = new SimpleDateFormat(TIME_PATTERN);
		return dateFormat.format(time);
	}

    /**
     *
     * @param date ���� � ������� yyyy-MM-dd'T'HH:mm:ss.SSS
     * @return  Date
     * @throws ParseException
     */
    public static Date parseXmlDateTimeFormat(String date) throws ParseException
    {
        return parseStringTime(date, XML_DATE_TIME_FORMAT);
    }

	/**
	 *
	 * @param date ���� � ������� yyyy-MM-dd
	 * @return  Date
	 * @throws ParseException
	 */
	public static Date parseXmlDateFormat(String date) throws ParseException
	{
		return parseStringTime(date, "yyyy-MM-dd");
	}

	/**
	 *
	 * @param date ���� � ������� yyyy-MM-dd'T'HH:mm:ss.SSS
	 * @return  Date
	 * @throws ParseException
	 */
	public static Date parseXmlDateTimeFormatWithoutMilliseconds(String date) throws ParseException
	{
		return parseStringTime(date, XML_DATE_TIME_WITHOUT_MILLISECONDS);
	}

	/**
	 * ���������� ���� � ������������� �������� (���� �������� �� ��������� 01.01.1970)
	 * @param time - ����� � ������� HH:mm:ss
	 * @return ���� �� ��������
	 * @throws ParseException
	 */
	public static Date fromXMlTimeToDate(String time) throws ParseException
	{
		return parseStringTime(time, TIME_FORMAT);
	}

	private static Date parseStringTime ( String date, String format) throws ParseException
	{
		if (date==null)
		{
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		return df.parse(date);
	}

	/**
	 * �������������� ���� ������� ISO8601 ����� ������ � ��� Date
	 * @param date ���� ����� ������ ������� ISO8601
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseISO8601(String date) throws ParseException
	{
		return parseStringTime(date, DATE_ISO8601_FORMAT);
	}

	/**
	 * �������������� ���� ������� ISO8601 ����� ������ ��� ����������� � ��� Date
	 * @param date ���� ����� ������ ������� ISO8601 ��� �����������
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseISO8601WithoutMilliSeconds(String date) throws ParseException
	{
		return parseStringTime(date, "yyyy-MM-dd hh:mm:ss");
	}

	/**
	 * �������������� ���� yyyy-mm-dd hh:mm:ss.milliseconds
	 * @param date
	 */
	public static String toISO8601DateFormat ( Date date )
	{
		if (date == null)
			return "empty";
		Calendar calendar = toCalendar(date);
		return toISO8601DateFormat(calendar);
	}

	/**
	 * �������������� ���� yyyy-mm-dd HH:mm:ss.milliseconds
	 * @param calendar ����
	 * @return ���� � � ���� ������
	 */
	public static String toISO8601_24HourDateFormat(Calendar calendar)
	{
		if (calendar == null)
		{
			return "";
		}

		return toISO8601_24HourDateFormat(calendar.getTime());
	}

	/**
	 * �������������� ���� yyyy-mm-dd HH:mm:ss.milliseconds
	 * @param date ����
	 * @return ���� � � ���� ������
	 */
	public static String toISO8601_24HourDateFormat(Date date)
	{
		if (date == null)
		{
			return "";
		}

		return new SimpleDateFormat(DateHelper.DATE_ISO8601_FORMAT_24_HOUR).format(date);
	}

	/**
	 * �������������� ���� yyyy-mm-dd hh:mm:ss.milliseconds
	 * @param calendar
	 */
	public static String toISO8601DateFormat ( Calendar calendar )
	{
		if (calendar == null)
			return "empty";
		return String.format(null, "%1$tFT%1$tT.%1$tL", new Object[] {calendar});
	}

	/**
	 *
	 * �������� ���� � ������� "yyMM"
	 *
	 * @param calendar ����
	 * @return String
	 */
	public static String toDisplayedExpiredate(Calendar calendar)
	{
		if (calendar == null)
		{
			return null;
		}
		return new SimpleDateFormat("yyMM").format(calendar.getTime());
	}

	public static boolean validate ( String dateString )
	{
		try
		{
			parseDate(dateString);
			return true;
		}
		catch (ParseException e)
		{
			return false;
		}
	}

	/**
	 * ������ ����
	 * @param dateString
	 * @throws ParseException
	 */
	public static Date parseDate ( String dateString ) throws ParseException
	{
		return getDateFormat().parse(dateString);
	}

	public static Calendar parseCalendar ( String dateString ) throws ParseException
	{
		Calendar clnd = new GregorianCalendar();
		clnd.setTime(parseDate(dateString));
		return clnd;
	}

	/**
	 * ������������� ��������� � ������
	 * @param date ����
	 * @return ������
	 */
	public static String formatCalendar ( Calendar date )
	{
		return date == null? null : getDateFormat().format(toDate(date));
	}

	/**
	 * ���������� ������ � ���������
	 * @param dateString ���� � ���� ������
	 * @param dateFormat ������ ����
	 * @return ����
	 * @throws ParseException
	 */
	public static Calendar parseCalendar (String dateString, String dateFormat) throws ParseException
	{
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(parseStringTime(dateString, dateFormat));
		return calendar;
	}

	public static Date parseTime ( String timeString ) throws ParseException
	{
		return getTimeFormat().parse(timeString);
	}

	private static DateFormat getDateFormat()
	{
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		dateFormat.setLenient(false);
		return dateFormat;
	}

	private static DateFormat getTimeFormat()
	{
		return new SimpleDateFormat("HH:mm");
	}

	/**
	 * ����� ����
	 * @param date - ��������� ����
	 * @param years
	 * @param months
	 * @param days
	 */
	public static Date add ( Date date, int years, int months, int days )
	{
		return add(date, years, months, days, 0, 0, 0, 0);
	}

	/**
	 * ����� ����
	 * @param date
	 * @param years
	 * @param months
	 * @param days
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @param milliseconds
	 */
	public static Date add ( Date date, int years, int months, int days, int hours, int minutes, int seconds,
	                         int milliseconds )
	{
		try
		{
			Date result = date;
			if (date!=null)
			{
				Calendar toCalendar = new GregorianCalendar();
				toCalendar.setTime(date);
				toCalendar.add(Calendar.YEAR        , years);
				toCalendar.add(Calendar.MONTH       , months);
				toCalendar.add(Calendar.DAY_OF_MONTH, days);
				toCalendar.add(Calendar.HOUR_OF_DAY , hours);
				toCalendar.add(Calendar.MINUTE      , minutes);
				toCalendar.add(Calendar.SECOND      , seconds);
				toCalendar.add(Calendar.MILLISECOND , milliseconds);
				result = toCalendar.getTime();
			}
			return result;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * ����� ����
	 * @param date ����
	 * @param dateSpan ������
	 * @return ����� ����
	 */
	public static Calendar add(Calendar date, DateSpan dateSpan)
	{
		Calendar newDate = (Calendar) date.clone();
		newDate.add(Calendar.YEAR, dateSpan.getYears());
		newDate.add(Calendar.MONTH, dateSpan.getMonths());
		newDate.add(Calendar.DAY_OF_MONTH, dateSpan.getDays());
		return newDate;
	}

	/**
	 * ����� ����
	 * @param date - ��������� ����
	 * @param dateSpan - ������
	 */
	public static Date add ( Date date, DateSpan dateSpan )
	{
		return add(date, dateSpan.getYears(), dateSpan.getMonths(), dateSpan.getDays());
	}

	/**
	 * @return ����������� Date � String ������� yyyy-MM-dd
	 */
	public static String toXMLDateFormat ( Date date )
	{
		if (date==null)
		{
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * @return ����������� Date � String �������  dd/mm/yyyy
	 */
	public static String toXMLDateFormat2 ( Calendar date )
	{
		if (date==null)
		{
			return "";
		}

		DateFormat df = new SimpleDateFormat(" dd/mm/yyyy");
		return df.format(toDate(date));
	}

	/**
	 * @return ����������� �� String ������� yyyy-MM-dd � Date ��� Calendar
	 */
	public static Date fromXMlDateToDate ( String strDate ) throws ParseException
	{
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", dfs);
		return sdf.parse(strDate);
	}

	/**
	 * @return ����������� �� String ������� dd.MM.yyyy � Date
	 */
	public static Date fromStringToDate ( String strDate ) throws ParseException
	{
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", dfs);
		return sdf.parse(strDate);
	}

	/**
	 * @return ����������� �� String ������� dd/mm/yyyy � Calendar
	 */
	public static Calendar fromDMYDateToDate ( String strDate ) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar clnd = new GregorianCalendar();
		clnd.setTime(sdf.parse(strDate));
		return clnd;
	}

	/**
	 * @return ����������� �� String ������� HH:MM � Calendar (UTC)
	 */
	public static Calendar makeUTCCalendar(String strDate) throws ParseException
	{
		TimeZone utc = TimeZone.getTimeZone("UTC");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		sdf.setTimeZone(utc);

		Calendar clnd = new GregorianCalendar(utc);
		clnd.setTime(sdf.parse(strDate));
		return clnd;
	}

	/**
	 * ������������� ��� ���� �������� �����
	 * � ������� �� ������ add �� ����������� ������� �� ������ �����
	 * @param toDate
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public static Date setTime  (Date toDate, int hours, int minutes, int seconds)
	{
		if (toDate!=null)
		{
			Calendar toCalendar = new GregorianCalendar();
			toCalendar.setTime(toDate);
			toCalendar.set(Calendar.HOUR_OF_DAY , hours);
			toCalendar.set(Calendar.MINUTE      , minutes);
			toCalendar.set(Calendar.SECOND      , seconds);
			toDate = toCalendar.getTime();
		}
		return toDate;
	}

	/**
	 * ������������� ��� ���� �������� �����
	 * � ������� �� ������ add �� ����������� ������� �� ������ �����
	 * @param toDate
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @param millisecond
	 */
	public static Date setTime  (Date toDate, int hours, int minutes, int seconds, int millisecond)
	{
		if (toDate!=null)
		{
			Calendar toCalendar = new GregorianCalendar();
			toCalendar.setTime(toDate);
			toCalendar.set(Calendar.HOUR_OF_DAY , hours);
			toCalendar.set(Calendar.MINUTE      , minutes);
			toCalendar.set(Calendar.SECOND      , seconds);
			toCalendar.set(Calendar.MILLISECOND , millisecond);
			toDate = toCalendar.getTime();
		}
		return toDate;
	}

	/**
	 * �������� ������������� ������� �� ������� ���� �� ����������
	 * @param toDate
	 * @return
	 */
	public static DateSpan calculatePeriodLeft(Calendar toDate)
	{
		try
		{
			return new DateSpan(DateHelper.getCurrentDate(), toDate);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������������� ������� �� ������� ���� �� ����������", e);
			return null;
		}
	}

	/**
	 * �������� ���-�� ���� � ������.
 	 * @param month ����� ������ (0 - ������, 1 - ������� � �.�.)
	 * @param year ���
	 * @return ���-�� ���� � ������
	 */
	public static int getDaysInMonth(int month, int year)
	{
		int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
		if(month==1 && (year%4==0) && (year%1000!=0))
			return 29;
		return days[month];
	}

	/**
	 * �������� ����������� �����. ���� ����� ������ ���������� ���� � ������, �� ��������� ����� ������.
	 * @param calendar - ���������� ����, � ������� ��������� �����
	 * @param needDate - ����������� ���� (�.�. ���������� ����, � ������� ���������� ����������� �����)
	 * @return  ��������� ���� (����� ����������� �����)
	 */
	public static Calendar addSolarMonth(Calendar calendar, int needDate)
	{
		calendar.add(Calendar.MONTH, +1);

		int daysInMonth = getDaysInMonth(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));

		if (needDate <= daysInMonth)
			calendar.set(Calendar.DATE, needDate);
		else
			calendar.set(Calendar.DATE, daysInMonth);
		return calendar;
	}

	/**
	 * ��������� � ���� ��������� ���������� ����
	 * @param calendar - ���� � ���� ���������
	 * @param days - ���������� ����, ������� ����� ��������
	 * @return ���� � ����������� ����������� ����
	 */
	public static Calendar addDays(Calendar calendar, int days)
	{
		return toCalendar(DateUtils.addDays(calendar.getTime(), days));
	}

	/**
	 * ��������� � ���� ��������� ���������� ������
	 * @param calendar - ���� � ���� ���������
	 * @param seconds - ���������� ������, ������� ����� ��������
	 * @return ���� � ����������� ����������� ������
	 */
	public static Calendar addSeconds(Calendar calendar, int seconds)
	{
		return toCalendar(DateUtils.addSeconds(calendar.getTime(), seconds));
	}

	/**
	 * ���������� ����� ������� (������� ����� �������� ������ � ���������)
	 * @param end - ����� ��������� ��� null
	 * @param start - ����� ������ ��� null
	 * @return ����� ������� � �������������
	 *  (null, ���� �� ������� ����� ������ ��� ����� ���������)
	 */
	public static Long diff(Date end, Date start)
	{
		if (start == null || end == null)
			return null;
		return end.getTime() - start.getTime();
	}

	/**
	 * ���������� ����� ������� (������� ����� �������� ������ � ���������)
	 * @param end - ����� ��������� ��� null
	 * @param start - ����� ������ ��� null
	 * @return ����� ������� � ��������
	 *  (null, ���� �� ������� ����� ������ ��� ����� ���������)
	 */
	public static Long diff(Calendar end, Calendar start)
	{
		if (start == null || end == null)
			return null;
		return diff(end.getTime(), start.getTime());
	}

	/**
	 * ������� ����� � ����.
	 * @param calendar ���� � ������� ����� �������� �����.
	 * @return ���� � ��������� ��������.
	 */
	public static Calendar clearTime(Calendar calendar)
	{
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar;
	}

	/**
	 * ������� ������� � ����.
	 * @param calendar ���� � ������� ����� �������� �������.
	 * @return ���� � ���������� ���������.
	 */
	public static Calendar clearSeconds(Calendar calendar)
	{
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar;
	}

	/**
	 * ���������� ���� ��� �������
	 * @param calendar ���� �� ��������
	 * @return ���� ��� �������
	 */
	public static Calendar getOnlyDate(Calendar calendar)
	{
		return clearTime((Calendar)calendar.clone());
	}

	/**
	 * ���������� 2 ���������
	 * @param c1 - ������ ����
	 * @param c2 - ������ ����
	 * @return ���� ��� null = 0, ���� ������ null - ��� ������ = -1, ���� ������ null = 1. ����� ����������� ���������.
	 */
	public static int nullSafeCompare(Calendar c1, Calendar c2)
	{
		if (c1 == null && c2 == null)
			return 0;
		//���� ��������� ���� ���� �� �����, ���� �� ���� �����������
		if (c1 == null)
			return -1;
		if (c2 == null)
			return 1;
		return c1.compareTo(c2);
	}

	/**
	 * �������� ��������� � ����� �������� (��� ����)
	 * @param calendar ��������� �  ����� � ��������
	 * @return �����
	 */
	public static Calendar getTime(Calendar calendar)
	{
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.clear();
		toCalendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
		toCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
		toCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
		toCalendar.set(Calendar.MILLISECOND,calendar.get(Calendar.MILLISECOND));
		return toCalendar;
	}

	/**
	 * ���������� ��������� ���� (����� ���� �� ������ ���� �� ���� calendar)
	 * @param calendar ����, ������� ���� ������������� � ���������
	 * @return ��������� ����
	 */
	public static int getJulianDate(Calendar calendar)
	{
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * ���������� ���� � �����
	 * @param date ����
	 * @param time �����
	 * @return ����� ����
	 */
	public static Calendar createCalendar(Object date, Object time) throws ParseException
	{
		Calendar dateCalendar = DateHelper.toCalendar((Date) (date instanceof String ? DateHelper.parseDate((String) date) : date));

		if (time != null)
		{
			Calendar timeCalendar = DateHelper.toCalendar((Date) (time instanceof String ? DateHelper.parseTime((String) time) : time));
			dateCalendar.set(Calendar.HOUR_OF_DAY,timeCalendar.get(Calendar.HOUR_OF_DAY));
			dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
			dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
		}
		return dateCalendar;
	}

	/**
	 * �������� ����� ���, � ����� �� cron ���������
	 * @param expression
	 * @return
	 */
	public static Pair<String, String> getTimeFromExpression(String expression)
	{
		Pair<String, String> result = new Pair<String, String>();

		String[] splitExpression = expression.toString().split(" ");
		String day = splitExpression[3].split("/")[1];
		result.setFirst(day);

		String time = splitExpression[2] + ":" + splitExpression[1];
		result.setSecond(time);

		return result;
	}

	/**
	 * ����� � �����(!�� �����!)
     * @param timeInterval ����� � �������������
	 * @return
	 */
	public static String toHour(Long timeInterval)
	{
		Long hour = timeInterval / MILLISECONDS_IN_HOUR;
		return hour.toString();
	}

	/**
	 * ���������� ������ ���
	 * @param cal - ����, ��� ������� ����� ����� ���
	 * @return - ���� �� <cal> � ����� = 00:00:00.000
	 */
	public static Calendar startOfDay(Calendar cal)
	{
		Calendar dayEnd = (Calendar) cal.clone();
		dayEnd.set(Calendar.HOUR_OF_DAY, 0);
		dayEnd.set(Calendar.MINUTE, 0);
		dayEnd.set(Calendar.SECOND, 0);
		dayEnd.set(Calendar.MILLISECOND, 0);
		return dayEnd;
	}

	/**
	 * ���������� ����� ���
	 * @param cal - ����, ��� ������� ����� ����� ���
	 * @return - ���� �� <cal> � ����� = 23:59:59.9999
	 */
	public static Calendar endOfDay(Calendar cal)
	{
		Calendar dayEnd = (Calendar) cal.clone();
		dayEnd.set(Calendar.HOUR_OF_DAY, 23);
		dayEnd.set(Calendar.MINUTE, 59);
		dayEnd.set(Calendar.SECOND, 59);
		dayEnd.set(Calendar.MILLISECOND, 999);
		return dayEnd;
	}

	/**
	 * ������� ����� ����� � ������� �����
	 * @param start ������ �������
	 * @return ������� � ����
	 */
	public static Long hourToCurrentDate(Calendar start)
	{
		if (start == null)
			return null;

		return diff(Calendar.getInstance(), start) / MILLISECONDS_IN_HOUR;
	}

	/**
	 * ������� ����� ������ � ����
	 * @param start ������ �������
	 * @param end ����� �������
	 * @return ������� � ����
	 */
	public static Long daysDiff(Calendar start, Calendar end)
	{
		Calendar first = (Calendar) start.clone();
		Calendar second = (Calendar) end.clone();
		clearTime(first);
		clearTime(second);

		if (first.after(second))
			throw new IllegalArgumentException("��������� ���� ������ ���� �� ������ ��������");

		return diff(end, start) / MILLISECONDS_IN_DAY;
	}

	/**
	 * ������� ����� ������ � ����
	 * @param start ������ �������
	 * @param end ����� �������
	 * @param skipBissextile - �� ��������� 29 ������� ��� �������� ���������� ����
	 * @return ������� � ����
	 */
	public static Long daysDiffByIncrease(Calendar start, Calendar end, boolean skipBissextile)
	{
		if (start.after(end))
			throw new IllegalArgumentException("��������� ���� ������ ���� ������ ��������");

		Calendar first = (Calendar) start.clone();
		Calendar second = (Calendar) end.clone();
		clearTime(first);
		clearTime(second);

		Long daysDiff = -1L;
		while (!first.after(second))
		{
			if (!(skipBissextile && first.get(Calendar.MONTH) == 2 && first.get(Calendar.DAY_OF_MONTH) == 29))
				daysDiff++;
			first.add(Calendar.DAY_OF_MONTH, 1);
		}

		return daysDiff;
	}

	/**
	 * ���������� ������ ��� ����� ������
	 * @param start ������ �������
	 * @param end ����� �������
	 * @return ������� � �����
	 */
	public static Long yearsDiff(Calendar start, Calendar end)
	{
		if (start.after(end))
			throw new IllegalArgumentException("��������� ���� ������ ���� �� ������ ��������");

		Calendar first = (Calendar) start.clone();
		Calendar second = (Calendar) end.clone();
		clearTime(first);
		clearTime(second);

		Long yearsDiff = -1L;
		while (!first.after(second))
		{
			first.add(Calendar.YEAR, 1);
			first.get(Calendar.YEAR);
			yearsDiff++;
		}

		return yearsDiff;
	}

	/**
	 * ���������� ������� �� ����
	 * @param date ����
	 * @return �������
	 */
	public static int getQuarter(Calendar date)
    {
        if (date==null)
			return 0;

	    return date.get(Calendar.MONTH)/MONTH_IN_QUARTER+1;
    }

	/**
	 * �������� ����� ������ � �������� �� ���� (1�, 2� ��� 3�)
	 * @param date ����
	 * @return ����� ������ � ��������
	 */
	public static int getMonthOfQuarter(Calendar date)
	{
		if (date == null)
		{
			return 0;
		}
		return 4 + (date.get(Calendar.MONTH) - MONTH_IN_QUARTER * getQuarter(date));
	}

	/**
	 * �������� ����� ������ � ��������� �� ����
	 * @param date ����
	 * @return ����� ������ � ���������
	 */
	public static int getMonthOfHalfYear(Calendar date)
	{
		if (date == null)
		{
			return 0;
		}
		return date.get(Calendar.MONTH) % MONTH_IN_HALF_YEAR + 1;
	}

	/**
	 * ���������� ���� ������ �� ����
	 * @param date ����
	 * @return ���� ������
	 */
	public static int getDayOfWeek(Calendar date)
    {
        if (date == null)
			return 0;

	    return date.get(Calendar.DAY_OF_WEEK);
    }

	/**
	 * ���������� ���� ������ �� ���� ������ � ��������� ������ � ��������� �� (�� �������������)
	 * @param date ����
	 * @return ���� ������
	 */
	public static String getDayOfWeekWord(Calendar date)
	{
		if (date == null)
			return "";

		return "�� " + daysOfWeek[(date.get(Calendar.DAY_OF_WEEK)+5)%7];
	}

	/**
	 * ��������� ������ ���� "3 ������� 2012"
	 * @param date ����
	 * @return ������ �������-���
	 */
	public static String formatDateWithQuarters(Calendar date)
	{
		if (date==null)
			return "";

		return getQuarter(date) + " ������� " + date.get(Calendar.YEAR);
	}

	/**
	 * ���������� ������� ����� ������ � ���������
	 * @param date1 ������ ����
	 * @param date2 ������ ����
	 * @return ��������
	 */
	public static int getDiffInQuarters(Calendar date1, Calendar date2)
    {
	    int years = date2.get(Calendar.YEAR) - date1.get(Calendar.YEAR);
		int quarters = DateHelper.getQuarter(date2) - DateHelper.getQuarter(date1);
		return years*4 + quarters;
    }

	/**
	 * ��������� ���� ������ �������� ��������
	 * @param date - ����
	 * @return ���� ������ ��������
	 */
	public static Calendar getStartQuarterDate(Calendar date)
	{
		Calendar startQuarterDate = (Calendar)date.clone();
		startQuarterDate.set(Calendar.DAY_OF_MONTH,1);
		int month = startQuarterDate.get(Calendar.MONTH)/MONTH_IN_QUARTER * MONTH_IN_QUARTER;
		startQuarterDate.set(Calendar.MONTH,month);
		startQuarterDate.get(Calendar.MONTH);

		return startQuarterDate;
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static Calendar getNextQuarter(Calendar date)
    {
	    Calendar newDate = (Calendar)date.clone();
	    newDate.set(Calendar.MONTH, newDate.get(Calendar.MONTH) + 3);
	    newDate.get(Calendar.MONTH);

        return newDate;
    }

	/**
	 * @return ������ ���������� ��������
	 */
	public static Calendar getNextQuarter()
    {
        return getStartQuarterDate(getNextQuarter(getCurrentDate()));
    }

	/**
	 * ��������� �������� � ����
	 * @param date ����
	 * @param quarters ���������� ���������
	 */
	public static void addQuarters(Calendar date, int quarters)
	{
		date.add(Calendar.YEAR, quarters/4);
		date.add(Calendar.MONTH, 3*(quarters%4));
	}

	/**
	 * ���������� ������� ����� � ������ ��������� ����
	 * @param timeZone - ����. ����
	 * @return �����
	 */
	public static Calendar getCurrentTimeWithRawOffset(int timeZone)
	{
		Calendar result = Calendar.getInstance();
					//��������� ���� (�����������) = UTC+2        �������� �������� �� UTC ������� �������
		int offset = timeZone + 2 - result.getTimeZone().getRawOffset()/RAW_OFFSET_TO_HOUR;
		result.add(Calendar.HOUR_OF_DAY, offset);
		return result;
	}

	/**
	 * ����������� ������ �� ������� xsd:datetime (2012-10-15T22:30:00+04:00) � ������ mAPI DateTime (15.10.2012T22:30:00)
	 * @param date ������ ������� xsd:datetime
	 * @return ������ � ������� mAPI DateTime 'dd.MM.yyyyTHH:mm:ss'
	 */
	public static String formatXsdDateTimeToMobileApiDateTime(String date)
	{
		String regex = "^(\\d{4})-(\\d{2})-(\\d{2})T*(\\d{2}):(\\d{2}):(\\d{2})(\\.\\d{1,6})?(Z|((-|\\+)\\d{2}:\\d{2}))?$";
		String replace = "$3.$2.$1T$4:$5:$6";
		return date.replaceAll(regex, replace);
	}

	/**
	 * ����������� ������ �� ������� xsd:date (2012-10-15+04:00) � ������ (15.10.2012)
	 * @param date ������ ������� xsd:date
	 * @return ������ � ������� 'dd.MM.yyyy'
	 */
	public static String formatXsdDateToString(String date)
	{
		String regex = "^(\\d{4})-(\\d{2})-(\\d{2})(Z|((-|\\+)\\d{2}:\\d{2}))?$";
		String replace = "$3.$2.$1";
		return date.replaceAll(regex, replace);
	}

	/**
	 * �������������� ���� � ������ ���� dd.MM.yyyy HH:mm:ss
	 * @param date - ����
	 * @return ��������� ������������� ����
	 */
	public static String dateToString(String format, Date date)
	{
		return String.format(format, date);
	}

	/**
	 * �������� ���� � ������������ �������
	 * @param format ������
	 * @param calendar ����
	 * @return ������
	 */
	public static String toFormatString(String format, Calendar calendar)
	{
		if (calendar == null)
		{
			return StringUtils.EMPTY;
		}

		return String.format(format, calendar);
	}

	/**
	 * ��������� � ������� ���� � ������� ������ ��� ���������� ��� ������
	 * @param dayOfWeek ���� ������(0 - �����������, 1 - ������� � �.�.)
	 * @return ��������� ����
	 */
	public static Calendar getNearDateByWeek(int dayOfWeek)
	{
		if(dayOfWeek < 0 || dayOfWeek > 6)
			throw new IllegalArgumentException("���� ������ ������ ���� �� 0 �� 6");

		Calendar res = Calendar.getInstance();

		int convertDayOfWeek = (dayOfWeek+2) % 7;
		int currentDayOfWeek = res.get(Calendar.DAY_OF_WEEK);

		if(currentDayOfWeek <= convertDayOfWeek)
		{
			res.set(Calendar.DAY_OF_WEEK, convertDayOfWeek);
		}
		else
		{
			res.add(Calendar.DAY_OF_WEEK, (7 - (currentDayOfWeek - convertDayOfWeek)));
		}

		return res;
	}

	/**
	 * ��������� � ������� ���� � ������� ���� ��� ���������� ����� ������ � ������ ������.
	 * ���� ����� ������ ������ �������� ������, �� ������� ��������� ���
	 * ���� � ������ ������ ���� ��� �������, �� ������� ��������� ����� ������
	 * (��������, ���� ������� 10.10.10 � ������� 9 �����, �� �������� 09.11.10)
	 * @param dateOfMonth ����� ������
	 * @param monthOfYear  ����� ������ (�� 1 �� 12)
	 * @return ��������� ����
	 */
	public static Calendar getNearDateByYear(int dateOfMonth, int monthOfYear)
	{
		return  getNearDateByYear(Calendar.getInstance(), dateOfMonth, monthOfYear);
	}

	public static Calendar getNearDateByYear(Calendar fromDate,int dateOfMonth, int monthOfYear)
	{
		if(dateOfMonth < 1 || dateOfMonth > 31)
		{
			throw new IllegalArgumentException("����� ������ ������ ���� �� 1 �� 31");
		}
		if(monthOfYear < 1 || monthOfYear > 12)
		{
			throw new IllegalArgumentException("����� ������ � ���� ������ ���� �� 1 �� 12");
		}
		Calendar res = (Calendar) fromDate.clone();
		int currentMonth = res.get(Calendar.MONTH);

		if(currentMonth > monthOfYear - 1)
		{
			res.add(Calendar.YEAR, 1);
		}
		res.set(Calendar.DAY_OF_MONTH, 1);
		res.set(Calendar.MONTH, monthOfYear - 1);
		setDayOfMonth(res, dateOfMonth);
		return res;
	}

	/**
	 * ��������� � ������� ���� � ������� ������ ��� ���������� ����� ������.
	 * ���� � ������ ������ ���� ��� �������, �� ������� ��������� ����� ������
	 * (��������, ���� ������� 10.10.10 � ������� 9 �����, �� �������� 09.11.10)
	 * @param dateOfMonth ����� ������
	 * @return ��������� ����
	 */
	public static Calendar getNearDateByMonth(int dateOfMonth)
	{
		return getNearDateByMonth(Calendar.getInstance(), dateOfMonth);
	}

	public static Calendar getNearDateByMonth(Calendar fromDate, int dateOfMonth)
	{
		if(dateOfMonth < 1 || dateOfMonth > 31)
			throw new IllegalArgumentException("����� ������ ������ ���� �� 1 �� 31");

		Calendar res = (Calendar) fromDate.clone();
		int currentMonth = res.get(Calendar.MONTH);
		int currentDayOfMonth = res.get(Calendar.DAY_OF_MONTH);

		if(currentDayOfMonth > dateOfMonth)
			res.add(Calendar.MONTH, 1);

		if ((currentMonth +1) < res.get(Calendar.MONTH))
			res.add(Calendar.MONTH, -1);

		setDayOfMonth(res, dateOfMonth);
		return res;
	}

	/**
	 * �������� ��������� ���� �� ��������, �������� ����������� �� ����������
	 * @param fromDate ����, ������������ ������� �������
	 * @param dayOfMonth ����� ��� � ������
	 * @return ���� �� ��������
	 */
	public static Calendar getNearLastDateByMonth(Calendar fromDate, int dayOfMonth)
	{
		if(dayOfMonth < 1 || dayOfMonth > 31)
			throw new IllegalArgumentException("����� ������ ������ ���� �� 1 �� 31");

		Calendar res = (Calendar) fromDate.clone();
		int currentDayOfMonth = res.get(Calendar.DAY_OF_MONTH);

		if(currentDayOfMonth < dayOfMonth)
			res.add(Calendar.MONTH, -1);

		setDayOfMonth(res, dayOfMonth);
		return res;
	}

	/**
	 * ��������� � ������� ���� � ������� ������ ��� ���������� ����� ������.
	 * �� ����� ������� �������� ����, �.� ���� ������� 10.10.10 � ������� 10 �����, �� �������� 10.11.10)
	 * @param dateOfMonth ����� ������
	 * @return ��������� ����
	 */
	public static Calendar getNearDateByMonthWithoutCurrent(int dateOfMonth)
	{
		if(dateOfMonth < 1 || dateOfMonth > 31)
			throw new IllegalArgumentException("����� ������ ������ ���� �� 1 �� 31");

		Calendar res = Calendar.getInstance();
		int currentDayOfMonth = res.get(Calendar.DAY_OF_MONTH);

		if(currentDayOfMonth >= dateOfMonth)
			res.add(Calendar.MONTH, 1);

		setDayOfMonth(res, dateOfMonth);
		return res;
	}

	/**
	 * ��������� � ������� ���� � ������� �������� ��� ���������� ������ � �������� � ����� ������.
	 * (��������, ���� ������� 10.01.10 � ������� 09 ����� 1�� ������ �������, �� �������� 09.04.10)
	 * @param monthOfQuarter ����� � ��������
	 * @param dateOfMonth ���� ������
	 * @return ��������� ����
	 */
	public static Calendar getNearDateByQuarter(int monthOfQuarter, int dateOfMonth)
	{
		return getNearDateByQuarter(Calendar.getInstance(), monthOfQuarter, dateOfMonth);
	}

	public static Calendar getNearDateByQuarter(Calendar fromDate, int monthOfQuarter, int dateOfMonth)
	{
		if(dateOfMonth < 1 || dateOfMonth > 31)
			throw new IllegalArgumentException("����� ������ ������ ���� �� 1 �� 31");

		if(monthOfQuarter < 1 || monthOfQuarter > 3)
			throw new IllegalArgumentException("����� � �������� ������ ���� �� 1 �� 3");

		Calendar res = (Calendar) fromDate.clone();

		int currentMonth = res.get(Calendar.MONTH);

		int currentDayOfMonth = res.get(Calendar.DAY_OF_MONTH);
		int currentMonthOfQuarter = getMonthOfQuarter(res);

		if(monthOfQuarter < currentMonthOfQuarter)
		{
			res.add(Calendar.MONTH, 3 - (currentMonthOfQuarter - monthOfQuarter));
		}
		else if(monthOfQuarter > currentMonthOfQuarter)
		{
			res.add(Calendar.MONTH, (monthOfQuarter - currentMonthOfQuarter));
		}
		else if(currentDayOfMonth > dateOfMonth)
		{
			res.add(Calendar.MONTH, 3);
		}
		if ((currentMonth +1) < res.get(Calendar.MONTH))
		{
			res.add(Calendar.MONTH, -1);
		}
		setDayOfMonth(res, dateOfMonth);
		return res;
	}


	/**
	 * �������� ��������� ���� �� ��������, �������� ����������� �� ����������
	 * @param fromDate ����, ������������ ������� �������
	 * @param monthOfQuarter ����� ������ � ��������
	 * @param dayOfMonth ����� ��� � ������
	 * @return ���� �� ��������
	 */
	public static Calendar getNearLastDateByQuarter(Calendar fromDate, int monthOfQuarter, int dayOfMonth)
	{
		if(dayOfMonth < 1 || dayOfMonth > 31)
			throw new IllegalArgumentException("����� ������ ������ ���� �� 1 �� 31");

		if(monthOfQuarter < 1 || monthOfQuarter > 3)
			throw new IllegalArgumentException("����� � �������� ������ ���� �� 1 �� 3");

		Calendar res = (Calendar) fromDate.clone();
		int currentDayOfMonth = res.get(Calendar.DAY_OF_MONTH);
		int currentMonthOfQuarter = getMonthOfQuarter(res);

		if(monthOfQuarter < currentMonthOfQuarter)
		{
			res.add(Calendar.MONTH, -(currentMonthOfQuarter - monthOfQuarter));
		}
		else if(monthOfQuarter > currentMonthOfQuarter)
		{
			res.add(Calendar.MONTH, -(3 + (monthOfQuarter - currentMonthOfQuarter)));
		}
		else if(currentDayOfMonth < dayOfMonth)
		{
			res.add(Calendar.MONTH, 3);
		}

		setDayOfMonth(res, dayOfMonth);
		return res;
	}


	/**
	 * ����������� ����������� ������������� ��� ������, �������� ��� ������� ������� ���
	 * setDayOfMonth(*.02.2013, 31) = 28.02.2013
	 * setDayOfMonth(*.03.2013, 31) = 31.03.2013
	 * setDayOfMonth(*.02.2013, 15) = 15.02.2013
	 * @param date ����
	 * @param dateOfMonth ��������� ���� ������
	 */
	public static void setDayOfMonth(Calendar date, int dateOfMonth)
	{
		int daysOfMonth = date.getActualMaximum(Calendar.DATE);
		date.set(Calendar.DAY_OF_MONTH, daysOfMonth < dateOfMonth ? daysOfMonth : dateOfMonth);
	}

	/**
	 * �������� ���� ������ �� ���� � ������������� BigInteger
	 * @param date ����
	 * @return ���� ������
	 */
	public static BigInteger getBigIntDayOfWeekByDate(Date date)
	{
		Calendar calendar = toCalendar(date);
		return calendar == null ? null : new BigInteger(Integer.toString((calendar.get(Calendar.DAY_OF_WEEK)+5)%7));
	}

	/**
	 * �������� ����� ������ �� ���� � ������������� BigInteger
	 * @param date ����
	 * @return ����� ������
	 */
	public static BigInteger getBigIntDayOfMonthByDate(Date date)
	{
		Calendar calendar = toCalendar(date);
		return calendar == null ? null : new BigInteger(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
	}

	/**
	 * �������� ����� ������ �� ���� � ������������� BigInteger
	 * @param calendar ����
	 * @return ����� ������
	 */
	public static BigInteger getBigIntDayOfMonthByDate(Calendar calendar)
	{
		return calendar == null ? null : new BigInteger(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
	}

	/**
	 * �������� ����� ������ � �������� �� ���� � ������������ BigInteger
	 * @param date ����
	 * @return ����� ��������
	 */
	public static BigInteger getBigIntMonthOfQuarterByDate(Date date)
	{
		Calendar calendar = toCalendar(date);
		return calendar == null ? null : new BigInteger(Integer.toString(getMonthOfQuarter(calendar)));
	}

	/**
	 * ����� �� ���������� ������ ������ xml (yyyy-mm-dd)
	 * @param strDate ������ ��� ��������
	 * @return true - ������ ����� ������ xml(yyyy-mm-dd)
	 */
	public static boolean isXMLDateFormat(String strDate)
	{
		return XML_DATE_PATTERN.matcher(strDate).matches();
	}

	/**
	 * �������������� ������ � ������� yyyy-mm-dd(���� ��� ������� ��������) � ������ dd.mm.yyyy
	 * @param xmlDate ������ ��� �����������
	 * @return ����������������� ������, ���� ��������(���� ������ �� ��������)
	 */
	public static String convertIfXmlDateFormat(String xmlDate)
	{
		if(StringHelper.isEmpty(xmlDate) || !isXMLDateFormat(xmlDate))
			return xmlDate;

		try
		{
			return String.format("%1$td.%1$tm.%1$tY", fromXMlDateToDate(xmlDate));
		}
		catch (ParseException e)
		{
			log.error(e.getMessage(), e);
			return xmlDate;
		}
	}

	/**
	 * ���������� ���������� �� ���� ���
	 * @param date1 - ������ ����
	 * @param date2 - ������ ����
	 * @return ���������� ����
	 */
	public static Calendar maxOfDates(Calendar date1, Calendar date2)
	{
		if (date1.after(date2))
			return  date1;
		return date2;
	}

	/**
	 * @return ������������ ����
	 */
	public static Calendar getMaximumDate()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.getMaximum(Calendar.YEAR));

		return calendar;
	}

	/**
	 * ��������� ��� ��� ����� �������
	 * @param cal1 ������ ����
	 * @param cal2 ������ ����
	 * @return true - ���� �����
	 */
	public static boolean isEqualDate(Calendar cal1, Calendar cal2)
	{
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
				&& cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ��������� �������� ������� � ������������
	 * @return ����� � ������������
	 */
	public static long getCurrentTimeInMillis(){
		return new GregorianCalendar().getTimeInMillis();
	}

	private static String getWeeklyP2PExecutionTimeInfo(Calendar date)
	{
		return "�� " + daysOfWeek[date.get(Calendar.DAY_OF_WEEK) + 1 % 6];
	}

	private static String getMonthlyP2PExecutionTimeInfo(Calendar date)
	{
		return date.get(Calendar.DAY_OF_MONTH) + "-�� �����";
	}

	private static String getQuarterlyP2PExecutionTimeInfo(Calendar date)
	{
		return date.get(Calendar.DAY_OF_MONTH) + "-�� �����, " + getMonthOfQuarter(date) + "-�� ������ ��������";
	}

	private static String getYearlyP2PExecutionTimeInfo(Calendar date)
	{
		return date.get(Calendar.DAY_OF_MONTH) + "-�� " + months[date.get(Calendar.MONTH)];
	}

	/**
	 * ��������� ��������� ���������� � ������������ �����������
	 * @param stringEventType - ��� �������������
	 * @param formattedDate - ���� ���������� �������
	 * @return
	 * @throws ParseException
	 */
	public static String getP2PExecutionTimeInfo(String stringEventType, String formattedDate) throws ParseException
	{
		if (StringHelper.isEmpty(stringEventType))
		{
			throw new IllegalArgumentException("������������ �������� ���� �������");
		}
		ExecutionEventType eventType = ExecutionEventType.valueOf(stringEventType);
		Calendar date = toCalendar(parseStringTime(formattedDate, XML_DATE_FORMAT));
		switch(eventType)
		{
			case ONCE_IN_WEEK:
				return getWeeklyP2PExecutionTimeInfo(date);
			case ONCE_IN_MONTH:
				return getMonthlyP2PExecutionTimeInfo(date);
			case ONCE_IN_QUARTER:
				return getQuarterlyP2PExecutionTimeInfo(date);
			case ONCE_IN_YEAR:
				return getYearlyP2PExecutionTimeInfo(date);
			default:
				throw new IllegalArgumentException("������������ �������� ���� �������");
		}
	}

	/**
	 * �������� MJD ��� ����
	 * @param date ����, ��� ������� ��������� MJD
	 * @return MJD
	 */
	public static long getMJD(Calendar date)
	{
		return daysDiff(MJD_START_DATE, date);
	}

	/**
	 * ���������� ���������� ���� �� ������� ���� �� �������� � ���� (1 ����, 4 ���, 15 ���� � �.�.)
	 * @param date ����, �� ������� �������
	 * @return ������ ����  (1 ����, 4 ���, 15 ���� � �.�.)
	 */
	public static String getCountDaysToString(Calendar date)
	{
		long dayVal = daysDiff(getCurrentDate(), date);
		long day10 = dayVal % 10;
		long day100 = dayVal % 100;
		if (day10 == 1 && day100 != 11)
			return dayVal + " ����";
		else if ((2 <= day10 && day10 <= 4) && !(11 <= day100 && day100 <= 14))
			return  dayVal + " ���";
		else
			return dayVal + " ����";
	}
}