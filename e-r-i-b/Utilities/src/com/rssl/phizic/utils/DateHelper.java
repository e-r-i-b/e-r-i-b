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

	public static final int MILLISECONDS_IN_DAY     = 60*60*24*1000;    //миллисекунд в дне
	public static final int MILLISECONDS_IN_HOUR    = 3600000;          //миллисекунд в часе
	public static final int MILLISECONDS_IN_MINUTE  = 1000*60;          //миллисекунд в минуте
	public static final int MILLISECONDS_IN_SECOND  = 1000;             //миллисекунд в секунде

	public static final String BEGIN_DAY_TIME = "00:00:00";  // время начала дня
	public static final String END_DAY_TIME = "23:59:59"; // время конца дня
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

	private static final String[] daysOfWeek = {"понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"};

	private static final String[] months = {"января", "февраля", "марта",
											"апреля", "мая",     "июня",
											"июля",   "августа", "сентября",
											"октября","ноября",  "декабря"};


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
	 * @return Получение 1-го числа предыдущей недели
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
	 * Получение даты преведущего  дня относительно текущей даты. Т. е. если текущая дата равна 25.05.2010,
	 * то полученная дата будет равна 24.05.2010.
	 * @param dateOfReference текущая дата
	 * @return дата предыдущей недели
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
	 * Получение даты преведущего  дня относительно текущей даты. Т. е. если текущая дата равна 25.05.2010,
	 * то полученная дата будет равна 24.05.2010.
	 * @return дата предыдущего дня
	 */
	public static Calendar getPreviousDay()
	{
		return getPreviousDay(1);
	}

	/**
	 * Получение даты - n  дней  относительно текущей даты.
	 * @param day сколько дней назад
	 * @return дата предыдущего дня
	 */
	public static Calendar getPreviousDay(int day)
	{
		Calendar reference = Calendar.getInstance();
		reference.add(Calendar.DAY_OF_YEAR, - day);

		return reference;
	}

	/**
	 * @return дата завтрашнего дня
	 */
	public static Calendar getNextDay()
	{
		Calendar reference = Calendar.getInstance();
		reference.add(Calendar.DAY_OF_YEAR, 1);

		return reference;
	}

	/**
	 * Получение даты "hours часов назад" относительно текущей даты. Т. е. если текущая дата равна 25.05.2010 14:00,
	 * то полученная дата будет равна 25.05.2010 14-hours:00.
	 * @return дата со смещением на hours часов относительно текущего времени
	 */
	public static Calendar getPreviousHours(int hours)
	{
		Calendar reference = Calendar.getInstance();
		reference.add(Calendar.HOUR_OF_DAY, -hours);

		return reference;
	}

	/**
	 * Получение даты преведущего  n дня относительно текущей даты. Т. е. если текущая дата равна 25.05.2010,
	 * a n = 5
	 * то полученная дата будет равна 20.05.2010.
	 * @param dateOfReference текущая дата
	 * @param n день
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
	 * Получение даты предыдущей недели относительно текущей даты. Т. е. если текущая дата равна 25.05.2010,
	 * то полученная дата будет равна 18.05.2010.
	 * @param dateOfReference текущая дата
	 * @return дата предыдущей недели
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
	 * Получение даты предыдущего года относительно текущей даты. Т. е. если текущая дата равна 25.05.2010,
	 * то полученная дата будет равна 25.05.2009.
	 * @param dateOfReference текущая дата
	 * @return дата предыдущей недели
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
	 * Изменить дату на несколько месяцев
	 * @param date текущая дата
	 * @param amount количество месяцев
	 * @return измененная дата
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
	 * Получение даты предыдущего месяца относительно текущей даты. Т. е. если текущая дата равна 22.05.2010,
	 * то полученная дата будет равна 22.04.2010.
	 * @param dateOfReference текущая дата
	 * @return дата предыдущего месяца
	 */
	public static Calendar getPreviousMonth (Calendar dateOfReference)
	{
		return addMonths(dateOfReference, -1);
	}

	/**
	 * @return Получение 1-го числа предыдущего месяца
	 */
	public static Calendar getPreviousMonth ()
	{
		Calendar newDate = DateHelper.getCurrentDate();
		newDate.add(Calendar.MONTH, -1);
		newDate.set(Calendar.DATE, 1);
		newDate.get(Calendar.MONTH); //не убирать, иначе не обновиться значение
		return newDate;
	}

	/**
	 * Получить дату в прошлом на определенное количество милисекунд
	 * @param milliseconds количество милисекунд
	 * @return дата в прошлом
	 */
	public static Calendar getPreviousMilliSeconds(long milliseconds)
	{
		Calendar res = Calendar.getInstance();
		res.setTimeInMillis(res.getTimeInMillis() - milliseconds);
		res.getTimeInMillis();
		return res;
	}

	@Deprecated //TODO убрать (что это?)
	public static Calendar getOperDate ()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(1999, 0, 1);
		return calendar;
	}

	/**
	 * @return Возвращает текущую дату (время 0:00:00 !!!)
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
			log.error("Ошибка получения текущей даты.", e);
			return null;
		}
	}

	/**
	 * Возвращает текущий год
	 * @return текущий год
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
			log.error("Ошибка получения значения текущего года.", e);
			return 0;
		}
	}

	/**
	 * @return Получение 1-го числа месяца
	 */
	public static Calendar getFirstDayOfMonth (Calendar date)
	{
		return DateUtils.truncate(date, Calendar.MONTH);
	}

	/**
	 * Возвращает дату последнего дня месяца
	 * @return дата последнего дня месяца
	 */
	public static Calendar getLastDayOfMonth(Calendar date)
	{
		Calendar newDate = (Calendar) date.clone();
		newDate.set(Calendar.DAY_OF_MONTH, newDate.getActualMaximum(Calendar.DAY_OF_MONTH));
		newDate.get(Calendar.MONTH);

		return newDate;
	}

	/**
	 * @return Получаем месяц словом на русском
	 */
	@Deprecated //TODO 1 - имя функции непонятное 2
	public static String toFormDate ( Calendar date )
	{
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
		dfs.setMonths(months);
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM", dfs);
		return sdf.format(date.getTime());
	}

	/**
	 * Месяц словом на русском.
	 * @param monthNumber - номер месяца(1..12)
	 * @return месяц в родительном падеже
	 */
	public static String monthNumberToString(int monthNumber)
	{
		return months[monthNumber-1];
	}

	/**
	 * Месяц словом на русском.
	 * @param date дата
	 * @return месяц в родительном падеже
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
	 * Преобразование даты в строку вида - 01 января 2014 года
	 * @param date дата
	 * @return строка даты
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
	 * День недели на русском.
	 * @param dayNumber - номер дня из Calendar (1-SUNDAY..7-SATURDAY)
	 * @return день недели в родительном падеже
	 */
	public static String dayNumberToString(int dayNumber)
	{
		return daysOfWeek[(dayNumber + 5) % 7];
	}

	/**
	 *
	 * Преобразует дату к строке вида dd MM(прописью) yyyy
	 *
	 * @param  calendar преобразуемая дата
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
	 * Получить номер дня из даты
	 * @param date дата
	 * @return номер дня
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * Получить номер дня из даты без ведущего нуля (1, а не 01)
	 * @param date дата
	 * @return номер дня
	 */
	public static String getDayOfDateWithoutNought(Calendar date)
	{
		return getDayOfDate(date).replaceFirst("^0*", "");
	}

	/**
	 * преобразование даты к строке формата dd месяц yyyy, где название месяца в родительном падеже
	 * @param date дата к форматированию
	 * @return возвращает строку с датой или пустую строку, если дата есть null
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * преобразование объекта типа Calendar к строке формата "dd month", где dd - число (цифрами), month - месяц (буквами, в родительном падеже)
	 * @param date - преобразуемая дата
	 * @return строка в указанном выше формате
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * преобразование формата даты из yyyy-mm-ddThh:mm:ss в dd.mm.yyyy
	 * @param date - преобразуемая дата
	 * @return строка в указанном выше формате
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * преобразование объекта типа Calendar к строке формата "dd month", где dd - число (цифрами), month - месяц (буквами, в родительном падеже), но без ведущих нулей
	 * @param date - преобразуемая дата
	 * @return строка в указанном выше формате
	 */
	public static String formatDayWithStringMonthWithoutNought(Calendar date)
	{
		return formatDayWithStringMonth(date).replaceFirst("^0*", "");
	}

	/**
	 * Возвращает название месяца в родительном падеже
	 * @param month номер месяца (от 1 до 12)
	 * @return название месяца в родительном падеже
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
	 * форматирует дату в строку вида
	 * dd.MM
	 * @param actualDate форматируемая дата
	 * @return строковое представление даты
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}


	/**
	 * Преобразование даты к строке в фотмате, зависящем от ее давности
	 * Если дата совпадает с сегодняшней, то на выходе будет строка формата "сегодня в hh:mm"
	 * Если дата совпадает со вчерашней, то на выходе будет строка формата "вчера в hh:mm"
	 * Если дата относится к текущему году, то на выходе будет строка формата "dd.MM"
	 * Если дата не относится к текущему году, то на выходе будет строка формата "dd.MM.yyyy"
	 * @param date преобразуемая дата
	 * @param withTime нужно ли показывать время для операций, выполненных вчера и сегодня
	 * @param needYear нужно ли дописывать 'г.' для операций, выполненных не в текущем году
	 * @return строка определенного формата
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
			// если год совпадает с текущим
			if (isSameYear(date, sysDate))
			{
				// если день совпадает с текущим
				if (DateUtils.isSameDay(actualDate, sysDate.getTime()))
				{
					builder.append("Сегодня");
					if (withTime)
					{
						builder.append(" в ");
						builder.append(new SimpleDateFormat("HH:mm").format(actualDate));
					}
				}
				//если день совпадает со вчерашним
				else if (DateUtils.isSameDay(actualDate, DateUtils.addDays(sysDate.getTime(), -1)))
				{
					builder.append("Вчера");
					if (withTime)
					{
						builder.append(" в ");
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
					builder.append("г.");
				}
			}
			return builder.toString();
		}
		catch (Exception e)
		{
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * форматирует дату в строку вида
	 * 1. HH:mm dd.MM - если дата не совпадает с текущим днем
	 * 1. HH:mm - если дата совпадает с текущим днем
	 * @param actualDate форматируемая дата
	 * @return строковое представление даты
	 */
	public static String formatDateDependsOnSysDate(Date actualDate)
	{
		if (actualDate == null)
			return org.apache.commons.lang.StringUtils.EMPTY;

		try
		{
			Calendar sysDate = DateHelper.getCurrentDate();
			String timePattern = DATE_TIME_PATTERN;
			// если день совпадает с текущим
			if (DateUtils.isSameDay(actualDate, sysDate.getTime()))
				timePattern = TIME_PATTERN;

			return new SimpleDateFormat(timePattern).format(actualDate);
		}
		catch (Exception e)
		{
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * Проверяет, принадлежат ли два объекта типа Calendar к одному году
	 * @param date1 первая дата, д.б. not null
	 * @param date2 вторая дата, д.б. not null
	 * @return true, если обе даты относятся к одному году
	 * @throws IllegalArgumentException, если любая из дат null
	 */
	private static boolean isSameYear(Calendar date1, Calendar date2)
	{
		if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("Входной параметр не должен быть null");
        }
		return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
	}

	/**
	 * преобразование даты к строке формата YYYY-MM-DD
	 * @param date дата к форматированию
	 * @return возвращает строку с датой или пустую строку, если дата есть null
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * Форматирует дату по шаблону
	 * @param date форматируемая дата
	 * @param pattern шаблон
	 * @return строковое представление в соответствие с шаблоном
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * преобразование даты к строке формата ДД/ММ/ГГГГ
	 * @param date дата к форматированию
	 * @return возвращает строку с датой или пустую строку, если дата есть null
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * Форматирование даты ДД/ММ/ГГГГ hh24:mm:ss.
	 * @param date входные данные
	 * @return результирующая строка
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * преобразование даты к строке формата ДД.ММ.ГГГГ
	 * @param date дата к форматированию
	 * @return возвращает строку с датой или пустую строку, если дата есть null
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * преобразование даты к строке формата DDMM
	 * @param date дата к форматированию
	 * @return возвращает строку с датой или пустую строку, если дата есть null
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
			log.error("Ошибка форматирования даты.",e);
			return "";
		}
	}

	/**
	 * преобразование даты к строке формата ddmmyy
	 * @param date дата к форматированию
	 * @return возвращает строку с датой или пустую строку, если дата есть null
	 */
	public static String formatDateDDMMYY( Calendar date )
	{
		if (date == null)
			return "";

		return new SimpleDateFormat("ddMMyy").format(date.getTime());

	}

	/**
	 * преобразование даты к строке формата yyyyMMdd
	 * @param date дата к форматированию
	 * @return возвращает строку с датой или пустую строку, если дата есть null
	 */
	public static String formatDateYYYYMMDD( Calendar date )
	{
		if (date == null)
			return "";

		return new SimpleDateFormat("yyyyMMdd").format(date.getTime());

	}

	/**
	 * @return Преобразует Date в Calendar
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
	 * @return Преобразует Date в String формата dd.MM.yyyy
	 */
	@Deprecated // используйте String.format
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
	 * Форматирование даты ДД.ММ.ГГГГ hh24:mm:ss.
	 * @param date входные данные
	 * @return результирующая строка
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
	// используйте String.format
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
	 * Преобразование даты в строку(без секунд)
	 * @param date дата, которую нужно преобразовать
	 * @return дата ввиде строки
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
	 * Преобразование даты ввиде строки в тип Date
	 * @param date дата ввиде строки без секунд
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseStringTime ( String date ) throws ParseException
	{
		return parseStringTime(date, "dd.MM.yyyy HH:mm:ss");
	}

	/**
	 * Преобразование даты ввиде строки без секунд в тип Date
	 * @param date дата ввиде строки без секунд
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseStringTimeWithoutSecond( String date ) throws ParseException
	{
		return parseStringTime(date, "dd.MM.yyyy HH:mm");
	}

    /**
     * @param date дата
     * @return строка вида yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static String getXmlDateTimeFormat(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat(XML_DATE_TIME_FORMAT);
        return dateFormat.format(date);
    }

	/**
	 * @param date дата
	 * @return строка вида yyyy-MM-dd'T'HH:mm:ss
	 */
	public static String getXmlDateTimeWithoutMillisecondsFormat(Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat(XML_DATE_TIME_WITHOUT_MILLISECONDS);
		return dateFormat.format(date);
	}

	/**
	 * преобразование даты к строке формата dd/MM/yyyy HH:mm:ss
	 * @param date дата к форматированию
	 * @return
	 */
	public static String getSimpleDateFormatWithHhMmSs(Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT + " " + TIME_FORMAT);
		return dateFormat.format(date);
	}

	/**
     * @param date дата
     * @return строка вида dd.MM.yyyy'T'HH:mm:ss.SSSS
     */
    public static String getXmlDateTimeRussianFormat(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat(XML_DATE_TIME_RUSSIAN_FORMAT);
        return dateFormat.format(date);
    }

    /**
     *
     * @param date дата
     * @return строка вида yyyy-MM-dd
     */
    public static String getXmlDateFormat(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat(XML_DATE_FORMAT_TIME_ZONE);
        return dateFormat.format(date);
    }

	/**
     *
     * @param date дата
     * @return строка вида dd.MM.yyyy
     */
    public static String getDateFormat(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat(XML_DATE_FORMAT);
        return dateFormat.format(date);
    }

	/**
  *
  * @param date дата
  * @return строка вида HH:mm:ss.SSS
  */
 public static String getXmlTimeFormat(Date date)
 {
     DateFormat dateFormat = new SimpleDateFormat(XML_TIME_FORMAT);
     return dateFormat.format(date);
 }

	/**
	 *
	 * @param date - дата
	 * @return - строковое представление даты, отформатированное как HH:mm:ss;
	 */
	public static String getTimeFormat(Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
		return dateFormat.format(date);
	}

	/**
	 * @param time время
	 * @return HH:mm
	 */
	public static String getTime(Time time)
	{
		DateFormat dateFormat = new SimpleDateFormat(TIME_PATTERN);
		return dateFormat.format(time);
	}

    /**
     *
     * @param date дата в формате yyyy-MM-dd'T'HH:mm:ss.SSS
     * @return  Date
     * @throws ParseException
     */
    public static Date parseXmlDateTimeFormat(String date) throws ParseException
    {
        return parseStringTime(date, XML_DATE_TIME_FORMAT);
    }

	/**
	 *
	 * @param date дата в формате yyyy-MM-dd
	 * @return  Date
	 * @throws ParseException
	 */
	public static Date parseXmlDateFormat(String date) throws ParseException
	{
		return parseStringTime(date, "yyyy-MM-dd");
	}

	/**
	 *
	 * @param date дата в формате yyyy-MM-dd'T'HH:mm:ss.SSS
	 * @return  Date
	 * @throws ParseException
	 */
	public static Date parseXmlDateTimeFormatWithoutMilliseconds(String date) throws ParseException
	{
		return parseStringTime(date, XML_DATE_TIME_WITHOUT_MILLISECONDS);
	}

	/**
	 * Возвращает дату с установленным временем (дата ставится по умолчанию 01.01.1970)
	 * @param time - время в формате HH:mm:ss
	 * @return дата со временем
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
	 * Преобразование даты формата ISO8601 ввиде строки в тип Date
	 * @param date дата ввиде строки формата ISO8601
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseISO8601(String date) throws ParseException
	{
		return parseStringTime(date, DATE_ISO8601_FORMAT);
	}

	/**
	 * Преобразование даты формата ISO8601 ввиде строки без миллисекунд в тип Date
	 * @param date дата ввиде строки формата ISO8601 без миллисекунд
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseISO8601WithoutMilliSeconds(String date) throws ParseException
	{
		return parseStringTime(date, "yyyy-MM-dd hh:mm:ss");
	}

	/**
	 * Форматирование даты yyyy-mm-dd hh:mm:ss.milliseconds
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
	 * Форматирование даты yyyy-mm-dd HH:mm:ss.milliseconds
	 * @param calendar дата
	 * @return дата в в виде строки
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
	 * Форматирование даты yyyy-mm-dd HH:mm:ss.milliseconds
	 * @param date дата
	 * @return дата в в виде строки
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
	 * Форматирование даты yyyy-mm-dd hh:mm:ss.milliseconds
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
	 * Приводит дату к формату "yyMM"
	 *
	 * @param calendar дата
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
	 * Разбор даты
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
	 * преобразовать календарь в строку
	 * @param date дата
	 * @return строка
	 */
	public static String formatCalendar ( Calendar date )
	{
		return date == null? null : getDateFormat().format(toDate(date));
	}

	/**
	 * распарсить строку в календарь
	 * @param dateString дата в виде строки
	 * @param dateFormat формат даты
	 * @return дата
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
	 * Сдвиг даты
	 * @param date - начальная дата
	 * @param years
	 * @param months
	 * @param days
	 */
	public static Date add ( Date date, int years, int months, int days )
	{
		return add(date, years, months, days, 0, 0, 0, 0);
	}

	/**
	 * Сдвиг даты
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
	 * Сдвиг даты
	 * @param date дата
	 * @param dateSpan период
	 * @return новая дата
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
	 * Сдвиг даты
	 * @param date - начальная дата
	 * @param dateSpan - период
	 */
	public static Date add ( Date date, DateSpan dateSpan )
	{
		return add(date, dateSpan.getYears(), dateSpan.getMonths(), dateSpan.getDays());
	}

	/**
	 * @return Преобразует Date в String формата yyyy-MM-dd
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
	 * @return Преобразует Date в String формата  dd/mm/yyyy
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
	 * @return Преобразует из String формата yyyy-MM-dd в Date или Calendar
	 */
	public static Date fromXMlDateToDate ( String strDate ) throws ParseException
	{
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", dfs);
		return sdf.parse(strDate);
	}

	/**
	 * @return Преобразует из String формата dd.MM.yyyy в Date
	 */
	public static Date fromStringToDate ( String strDate ) throws ParseException
	{
		DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ru"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", dfs);
		return sdf.parse(strDate);
	}

	/**
	 * @return Преобразует из String формата dd/mm/yyyy в Calendar
	 */
	public static Calendar fromDMYDateToDate ( String strDate ) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar clnd = new GregorianCalendar();
		clnd.setTime(sdf.parse(strDate));
		return clnd;
	}

	/**
	 * @return Преобразует из String формата HH:MM в Calendar (UTC)
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
	 * Устанавливает для даты заданное время
	 * в отличие от метода add не учитывается переход на летнее время
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
	 * Устанавливает для даты заданное время
	 * в отличие от метода add не учитывается переход на летнее время
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
	 * Получить представление периода от текущей дата до переданной
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
			log.error("Ошибка получения представление периода от текущей дата до переданной", e);
			return null;
		}
	}

	/**
	 * получить кол-во дней в месяце.
 	 * @param month номер месяца (0 - январь, 1 - февраль и т.п.)
	 * @param year год
	 * @return кол-во дней в месяце
	 */
	public static int getDaysInMonth(int month, int year)
	{
		int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
		if(month==1 && (year%4==0) && (year%1000!=0))
			return 29;
		return days[month];
	}

	/**
	 * Добавить календарный месяц. Если число больше количества дней в месяце, то последнее число месяца.
	 * @param calendar - предыдущая дата, к которой добавляем месяц
	 * @param needDate - необходимая дата (т.е. постоянная дата, к которой прибавляем календарный месяц)
	 * @return  следующую дату (через календарный месяц)
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
	 * Добавлякт к дате указанное количество дней
	 * @param calendar - дата в виде календаря
	 * @param days - количество дней, которые нужно добавить
	 * @return дата с добавленным количеством дней
	 */
	public static Calendar addDays(Calendar calendar, int days)
	{
		return toCalendar(DateUtils.addDays(calendar.getTime(), days));
	}

	/**
	 * Добавлякт к дате указанное количество секунд
	 * @param calendar - дата в виде календаря
	 * @param seconds - количество секунд, которые нужно добавить
	 * @return дата с добавленным количеством секунд
	 */
	public static Calendar addSeconds(Calendar calendar, int seconds)
	{
		return toCalendar(DateUtils.addSeconds(calendar.getTime(), seconds));
	}

	/**
	 * Возвращает длину периода (разницу между временем начала и окончания)
	 * @param end - время окончания или null
	 * @param start - время начала или null
	 * @return длина периода в муллисекундах
	 *  (null, если не указано время начала или время окончания)
	 */
	public static Long diff(Date end, Date start)
	{
		if (start == null || end == null)
			return null;
		return end.getTime() - start.getTime();
	}

	/**
	 * Возвращает длину периода (разницу между временем начала и окончания)
	 * @param end - время окончания или null
	 * @param start - время начала или null
	 * @return длина периода в секундах
	 *  (null, если не указано время начала или время окончания)
	 */
	public static Long diff(Calendar end, Calendar start)
	{
		if (start == null || end == null)
			return null;
		return diff(end.getTime(), start.getTime());
	}

	/**
	 * Очищает время у даты.
	 * @param calendar дата у которой нужно очистить время.
	 * @return дата с очищенным временем.
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
	 * Очищает секунды у даты.
	 * @param calendar дата у которой нужно очистить секунды.
	 * @return дата с очищенными секундами.
	 */
	public static Calendar clearSeconds(Calendar calendar)
	{
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar;
	}

	/**
	 * Возвращает дату без времени
	 * @param calendar дата со временем
	 * @return дата без времени
	 */
	public static Calendar getOnlyDate(Calendar calendar)
	{
		return clearTime((Calendar)calendar.clone());
	}

	/**
	 * Сравниваем 2 календаря
	 * @param c1 - первая дата
	 * @param c2 - вторая дата
	 * @return Если оба null = 0, если первое null - оно меньше = -1, если второе null = 1. Далее стандартное сравнение.
	 */
	public static int nullSafeCompare(Calendar c1, Calendar c2)
	{
		if (c1 == null && c2 == null)
			return 0;
		//Если заполнено хоть одно из полей, есть по чему сортировать
		if (c1 == null)
			return -1;
		if (c2 == null)
			return 1;
		return c1.compareTo(c2);
	}

	/**
	 * Получить календарь с одним временем (без даты)
	 * @param calendar календарь с  датой и временем
	 * @return время
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
	 * Возвращает юлианскую дату (число дней от начала года до даты calendar)
	 * @param calendar Дата, которую надо преобразовать в юлианскую
	 * @return Юлианская дата
	 */
	public static int getJulianDate(Calendar calendar)
	{
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Объединяет дату и время
	 * @param date дата
	 * @param time время
	 * @return общая дата
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
	 * Получить номер дня, и время из cron выражения
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
	 * время в часах(!до целых!)
     * @param timeInterval время в миллисекундах
	 * @return
	 */
	public static String toHour(Long timeInterval)
	{
		Long hour = timeInterval / MILLISECONDS_IN_HOUR;
		return hour.toString();
	}

	/**
	 * Возвращает начало дня
	 * @param cal - дата, для которой нужен конец дня
	 * @return - дата от <cal> а время = 00:00:00.000
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
	 * Возвращает конец дня
	 * @param cal - дата, для которой нужен конец дня
	 * @return - дата от <cal> а время = 23:59:59.9999
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
	 * Разница между датой и текущей датой
	 * @param start начало периода
	 * @return разница в днях
	 */
	public static Long hourToCurrentDate(Calendar start)
	{
		if (start == null)
			return null;

		return diff(Calendar.getInstance(), start) / MILLISECONDS_IN_HOUR;
	}

	/**
	 * Разница между датами в днях
	 * @param start начало периода
	 * @param end конец периода
	 * @return разница в днях
	 */
	public static Long daysDiff(Calendar start, Calendar end)
	{
		Calendar first = (Calendar) start.clone();
		Calendar second = (Calendar) end.clone();
		clearTime(first);
		clearTime(second);

		if (first.after(second))
			throw new IllegalArgumentException("Начальная дата должна быть не больше конечной");

		return diff(end, start) / MILLISECONDS_IN_DAY;
	}

	/**
	 * Разница между датами в днях
	 * @param start начало периода
	 * @param end конец периода
	 * @param skipBissextile - не учитывать 29 февраля при подсчете количества дней
	 * @return разница в днях
	 */
	public static Long daysDiffByIncrease(Calendar start, Calendar end, boolean skipBissextile)
	{
		if (start.after(end))
			throw new IllegalArgumentException("Начальная дата должна быть больше конечной");

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
	 * Количество полных лет между датами
	 * @param start начало периода
	 * @param end конец периода
	 * @return разница в годах
	 */
	public static Long yearsDiff(Calendar start, Calendar end)
	{
		if (start.after(end))
			throw new IllegalArgumentException("Начальная дата должна быть не больше конечной");

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
	 * Возвращает квартал по дате
	 * @param date дата
	 * @return квартал
	 */
	public static int getQuarter(Calendar date)
    {
        if (date==null)
			return 0;

	    return date.get(Calendar.MONTH)/MONTH_IN_QUARTER+1;
    }

	/**
	 * Получить номер месяца в квартале по дате (1й, 2й или 3й)
	 * @param date дата
	 * @return номер месяца в квартале
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
	 * Получить номер месяца в полугодии по дате
	 * @param date дата
	 * @return номер месяца в полугодии
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
	 * Возвращает день недели по дате
	 * @param date дата
	 * @return день недели
	 */
	public static int getDayOfWeek(Calendar date)
    {
        if (date == null)
			return 0;

	    return date.get(Calendar.DAY_OF_WEEK);
    }

	/**
	 * Возвращает день недели по дате словом в дательном падеже с предлогом по (по понедельникам)
	 * @param date дата
	 * @return день недели
	 */
	public static String getDayOfWeekWord(Calendar date)
	{
		if (date == null)
			return "";

		return "по " + daysOfWeek[(date.get(Calendar.DAY_OF_WEEK)+5)%7];
	}

	/**
	 * Взвращает строку вида "3 квартал 2012"
	 * @param date дата
	 * @return строка квартал-год
	 */
	public static String formatDateWithQuarters(Calendar date)
	{
		if (date==null)
			return "";

		return getQuarter(date) + " квартал " + date.get(Calendar.YEAR);
	}

	/**
	 * Возвращает разницу между датами в кварталах
	 * @param date1 первая дата
	 * @param date2 вторая дата
	 * @return кварталы
	 */
	public static int getDiffInQuarters(Calendar date1, Calendar date2)
    {
	    int years = date2.get(Calendar.YEAR) - date1.get(Calendar.YEAR);
		int quarters = DateHelper.getQuarter(date2) - DateHelper.getQuarter(date1);
		return years*4 + quarters;
    }

	/**
	 * Получение даты начала текущего квартала
	 * @param date - дата
	 * @return дата начала квартала
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
	 * @return начало следующего квартала
	 */
	public static Calendar getNextQuarter()
    {
        return getStartQuarterDate(getNextQuarter(getCurrentDate()));
    }

	/**
	 * добавляет кварталы к дате
	 * @param date дата
	 * @param quarters количество кварталов
	 */
	public static void addQuarters(Calendar date, int quarters)
	{
		date.add(Calendar.YEAR, quarters/4);
		date.add(Calendar.MONTH, 3*(quarters%4));
	}

	/**
	 * Возвращает текущее время с учетом временной зоны
	 * @param timeZone - врем. зона
	 * @return время
	 */
	public static Calendar getCurrentTimeWithRawOffset(int timeZone)
	{
		Calendar result = Calendar.getInstance();
					//начальная зона (Калининград) = UTC+2        вычитаем смещение от UTC времени сервера
		int offset = timeZone + 2 - result.getTimeZone().getRawOffset()/RAW_OFFSET_TO_HOUR;
		result.add(Calendar.HOUR_OF_DAY, offset);
		return result;
	}

	/**
	 * Преобразует строку из формата xsd:datetime (2012-10-15T22:30:00+04:00) в формат mAPI DateTime (15.10.2012T22:30:00)
	 * @param date строка формата xsd:datetime
	 * @return строка в формате mAPI DateTime 'dd.MM.yyyyTHH:mm:ss'
	 */
	public static String formatXsdDateTimeToMobileApiDateTime(String date)
	{
		String regex = "^(\\d{4})-(\\d{2})-(\\d{2})T*(\\d{2}):(\\d{2}):(\\d{2})(\\.\\d{1,6})?(Z|((-|\\+)\\d{2}:\\d{2}))?$";
		String replace = "$3.$2.$1T$4:$5:$6";
		return date.replaceAll(regex, replace);
	}

	/**
	 * Преобразует строку из формата xsd:date (2012-10-15+04:00) в формат (15.10.2012)
	 * @param date строка формата xsd:date
	 * @return строка в формате 'dd.MM.yyyy'
	 */
	public static String formatXsdDateToString(String date)
	{
		String regex = "^(\\d{4})-(\\d{2})-(\\d{2})(Z|((-|\\+)\\d{2}:\\d{2}))?$";
		String replace = "$3.$2.$1";
		return date.replaceAll(regex, replace);
	}

	/**
	 * преобразование даты в строку вида dd.MM.yyyy HH:mm:ss
	 * @param date - дата
	 * @return строковое представление даты
	 */
	public static String dateToString(String format, Date date)
	{
		return String.format(format, date);
	}

	/**
	 * Привести дату к определеному формату
	 * @param format формат
	 * @param calendar дата
	 * @return строка
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
	 * Ближайшая к текущей дата в разрезе недели для указанного дня недели
	 * @param dayOfWeek день недели(0 - понедельник, 1 - вторник и т.д.)
	 * @return ближайшая дата
	 */
	public static Calendar getNearDateByWeek(int dayOfWeek)
	{
		if(dayOfWeek < 0 || dayOfWeek > 6)
			throw new IllegalArgumentException("День недели должен быть от 0 до 6");

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
	 * Ближайшая к текущей дата в разрезе года для указанного числа месяца и номера месяца.
	 * Если номер месяца меньше текущего месяца, то берется следующий год
	 * Если в месяце меньше днем чем указано, то берется последнее число месяца
	 * (например, если сегодня 10.10.10 и указали 9 число, то вернется 09.11.10)
	 * @param dateOfMonth число месяца
	 * @param monthOfYear  номер месяца (от 1 до 12)
	 * @return ближайшая дата
	 */
	public static Calendar getNearDateByYear(int dateOfMonth, int monthOfYear)
	{
		return  getNearDateByYear(Calendar.getInstance(), dateOfMonth, monthOfYear);
	}

	public static Calendar getNearDateByYear(Calendar fromDate,int dateOfMonth, int monthOfYear)
	{
		if(dateOfMonth < 1 || dateOfMonth > 31)
		{
			throw new IllegalArgumentException("Число месяца должно быть от 1 до 31");
		}
		if(monthOfYear < 1 || monthOfYear > 12)
		{
			throw new IllegalArgumentException("Номер месяца в году должно быть от 1 до 12");
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
	 * Ближайшая к текущей дата в разрезе месяца для указанного числа месяца.
	 * Если в месяце меньше днем чем указано, то берется последнее число месяца
	 * (например, если сегодня 10.10.10 и указали 9 число, то вернется 09.11.10)
	 * @param dateOfMonth число месяца
	 * @return ближайшая дата
	 */
	public static Calendar getNearDateByMonth(int dateOfMonth)
	{
		return getNearDateByMonth(Calendar.getInstance(), dateOfMonth);
	}

	public static Calendar getNearDateByMonth(Calendar fromDate, int dateOfMonth)
	{
		if(dateOfMonth < 1 || dateOfMonth > 31)
			throw new IllegalArgumentException("Число месяца должно быть от 1 до 31");

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
	 * Получить ближайшую дату из прошлого, наиболее подходящуюю по параметрам
	 * @param fromDate дата, относительно которой считать
	 * @param dayOfMonth номер для в месяце
	 * @return дата из прошлого
	 */
	public static Calendar getNearLastDateByMonth(Calendar fromDate, int dayOfMonth)
	{
		if(dayOfMonth < 1 || dayOfMonth > 31)
			throw new IllegalArgumentException("Число месяца должно быть от 1 до 31");

		Calendar res = (Calendar) fromDate.clone();
		int currentDayOfMonth = res.get(Calendar.DAY_OF_MONTH);

		if(currentDayOfMonth < dayOfMonth)
			res.add(Calendar.MONTH, -1);

		setDayOfMonth(res, dayOfMonth);
		return res;
	}

	/**
	 * Ближайшая к текущей дата в разрезе месяца для указанного числа месяца.
	 * Не может вернуть текующую дату, т.е если сегодня 10.10.10 и указали 10 число, то вернется 10.11.10)
	 * @param dateOfMonth число месяца
	 * @return ближайшая дата
	 */
	public static Calendar getNearDateByMonthWithoutCurrent(int dateOfMonth)
	{
		if(dateOfMonth < 1 || dateOfMonth > 31)
			throw new IllegalArgumentException("Число месяца должно быть от 1 до 31");

		Calendar res = Calendar.getInstance();
		int currentDayOfMonth = res.get(Calendar.DAY_OF_MONTH);

		if(currentDayOfMonth >= dateOfMonth)
			res.add(Calendar.MONTH, 1);

		setDayOfMonth(res, dateOfMonth);
		return res;
	}

	/**
	 * Ближайшая к текущей дата в разрезе квартала для указанного месяца в квартале и числа месяца.
	 * (например, если сегодня 10.01.10 и указали 09 число 1го месяца квартал, то вернется 09.04.10)
	 * @param monthOfQuarter месяц в квартале
	 * @param dateOfMonth день месяца
	 * @return ближайшая дата
	 */
	public static Calendar getNearDateByQuarter(int monthOfQuarter, int dateOfMonth)
	{
		return getNearDateByQuarter(Calendar.getInstance(), monthOfQuarter, dateOfMonth);
	}

	public static Calendar getNearDateByQuarter(Calendar fromDate, int monthOfQuarter, int dateOfMonth)
	{
		if(dateOfMonth < 1 || dateOfMonth > 31)
			throw new IllegalArgumentException("Число месяца должно быть от 1 до 31");

		if(monthOfQuarter < 1 || monthOfQuarter > 3)
			throw new IllegalArgumentException("Месяц в квартале должен быть от 1 до 3");

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
	 * Получить ближайшую дату из прошлого, наиболее подходящуюю по параметрам
	 * @param fromDate дата, относительно которой считать
	 * @param monthOfQuarter номер месяца в квартале
	 * @param dayOfMonth номер для в месяце
	 * @return дата из прошлого
	 */
	public static Calendar getNearLastDateByQuarter(Calendar fromDate, int monthOfQuarter, int dayOfMonth)
	{
		if(dayOfMonth < 1 || dayOfMonth > 31)
			throw new IllegalArgumentException("Число месяца должно быть от 1 до 31");

		if(monthOfQuarter < 1 || monthOfQuarter > 3)
			throw new IllegalArgumentException("Месяц в квартале должен быть от 1 до 3");

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
	 * Выставление наибольшего существующего дня месяца, меньшего или равного данного дня
	 * setDayOfMonth(*.02.2013, 31) = 28.02.2013
	 * setDayOfMonth(*.03.2013, 31) = 31.03.2013
	 * setDayOfMonth(*.02.2013, 15) = 15.02.2013
	 * @param date дата
	 * @param dateOfMonth граничный день месяца
	 */
	public static void setDayOfMonth(Calendar date, int dateOfMonth)
	{
		int daysOfMonth = date.getActualMaximum(Calendar.DATE);
		date.set(Calendar.DAY_OF_MONTH, daysOfMonth < dateOfMonth ? daysOfMonth : dateOfMonth);
	}

	/**
	 * Получить день недели по дате в представлении BigInteger
	 * @param date дата
	 * @return день недели
	 */
	public static BigInteger getBigIntDayOfWeekByDate(Date date)
	{
		Calendar calendar = toCalendar(date);
		return calendar == null ? null : new BigInteger(Integer.toString((calendar.get(Calendar.DAY_OF_WEEK)+5)%7));
	}

	/**
	 * Получить число месяца по дате в представлении BigInteger
	 * @param date дата
	 * @return число месяца
	 */
	public static BigInteger getBigIntDayOfMonthByDate(Date date)
	{
		Calendar calendar = toCalendar(date);
		return calendar == null ? null : new BigInteger(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
	}

	/**
	 * Получить число месяца по дате в представлении BigInteger
	 * @param calendar дата
	 * @return число месяца
	 */
	public static BigInteger getBigIntDayOfMonthByDate(Calendar calendar)
	{
		return calendar == null ? null : new BigInteger(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
	}

	/**
	 * Получить номер месяца в квартале по дате в предсталении BigInteger
	 * @param date дата
	 * @return номер квартала
	 */
	public static BigInteger getBigIntMonthOfQuarterByDate(Date date)
	{
		Calendar calendar = toCalendar(date);
		return calendar == null ? null : new BigInteger(Integer.toString(getMonthOfQuarter(calendar)));
	}

	/**
	 * Имеет ли переданная строка формат xml (yyyy-mm-dd)
	 * @param strDate строка для проверки
	 * @return true - строка имеет формат xml(yyyy-mm-dd)
	 */
	public static boolean isXMLDateFormat(String strDate)
	{
		return XML_DATE_PATTERN.matcher(strDate).matches();
	}

	/**
	 * Конвертировать строку в формате yyyy-mm-dd(если она таковой является) в формат dd.mm.yyyy
	 * @param xmlDate строка для конвертации
	 * @return сконвертированная строка, либо параметр(если формат не подходит)
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
	 * Возвращает наибольшую из двух дат
	 * @param date1 - первая дата
	 * @param date2 - вторая дата
	 * @return наибольшая дата
	 */
	public static Calendar maxOfDates(Calendar date1, Calendar date2)
	{
		if (date1.after(date2))
			return  date1;
		return date2;
	}

	/**
	 * @return максимальная дата
	 */
	public static Calendar getMaximumDate()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.getMaximum(Calendar.YEAR));

		return calendar;
	}

	/**
	 * Сравнение дат без учета времени
	 * @param cal1 первая дата
	 * @param cal2 вторая дата
	 * @return true - даты равны
	 */
	public static boolean isEqualDate(Calendar cal1, Calendar cal2)
	{
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
				&& cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Получение текущего времени в милесекундах
	 * @return Время в милисекундах
	 */
	public static long getCurrentTimeInMillis(){
		return new GregorianCalendar().getTimeInMillis();
	}

	private static String getWeeklyP2PExecutionTimeInfo(Calendar date)
	{
		return "по " + daysOfWeek[date.get(Calendar.DAY_OF_WEEK) + 1 % 6];
	}

	private static String getMonthlyP2PExecutionTimeInfo(Calendar date)
	{
		return date.get(Calendar.DAY_OF_MONTH) + "-го числа";
	}

	private static String getQuarterlyP2PExecutionTimeInfo(Calendar date)
	{
		return date.get(Calendar.DAY_OF_MONTH) + "-го числа, " + getMonthOfQuarter(date) + "-го месяца квартала";
	}

	private static String getYearlyP2PExecutionTimeInfo(Calendar date)
	{
		return date.get(Calendar.DAY_OF_MONTH) + "-го " + months[date.get(Calendar.MONTH)];
	}

	/**
	 * Получение строковой информации о периодичнсть автоплатежа
	 * @param stringEventType - тип периодичности
	 * @param formattedDate - дата ближайшего платежа
	 * @return
	 * @throws ParseException
	 */
	public static String getP2PExecutionTimeInfo(String stringEventType, String formattedDate) throws ParseException
	{
		if (StringHelper.isEmpty(stringEventType))
		{
			throw new IllegalArgumentException("Некорректное значения типа события");
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
				throw new IllegalArgumentException("Некорректное значения типа события");
		}
	}

	/**
	 * получить MJD для даты
	 * @param date дата, для которой считается MJD
	 * @return MJD
	 */
	public static long getMJD(Calendar date)
	{
		return daysDiff(MJD_START_DATE, date);
	}

	/**
	 * возвращает количество дней от текущей даты до заданной в виде (1 день, 4 дня, 15 дней и т.д.)
	 * @param date дата, до которой считаем
	 * @return строка вида  (1 день, 4 дня, 15 дней и т.д.)
	 */
	public static String getCountDaysToString(Calendar date)
	{
		long dayVal = daysDiff(getCurrentDate(), date);
		long day10 = dayVal % 10;
		long day100 = dayVal % 100;
		if (day10 == 1 && day100 != 11)
			return dayVal + " день";
		else if ((2 <= day10 && day10 <= 4) && !(11 <= day100 && day100 <= 14))
			return  dayVal + " дня";
		else
			return dayVal + " дней";
	}
}