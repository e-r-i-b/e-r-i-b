package com.rssl.phizic.config.loanreport;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Erkin
 * @ created 18.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Время дня (суток)
 */
@Immutable
public final class TimeOfDay
{
	private final Calendar calendar;

	private TimeOfDay(Calendar calendar)
	{
		this.calendar = calendar;
	}

	/**
	 * Читает время дня из строки формата "чч:мм:сс"
	 * @param timeString - время суток в виде строки (never null nor empty)
	 * @return время дня (never null)
	 */
	public static TimeOfDay fromHHMMSS(String timeString)
	{
		if (StringHelper.isEmpty(timeString))
		    throw new IllegalArgumentException("Не указано время суток");

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		try
		{
			Date date = dateFormat.parse(timeString);
			return new TimeOfDay(DateHelper.toCalendar(date));
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException("Указано непонятное время суток: " + timeString, e);
		}
	}

	/**
	 * Возвращает время дня в виде строки формата "чч:мм:сс"
	 * @return время суток в виде строки (never null nor empty)
	 */
	public String toHHMMSS()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = DateHelper.toDate(calendar);
		return dateFormat.format(date);
	}

	/**
	 * Возвращает дату+время на заданную дату
	 * @param instantDate - дата, относительно которой рассчитывается дата+время
	 * @return дата+время (never null)
	 */
	public Calendar toInstantDateTime(Calendar instantDate)
	{
		Calendar instantDateTime = (Calendar) instantDate.clone();
		instantDateTime.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
		instantDateTime.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
		instantDateTime.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
		instantDateTime.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND));
		return instantDateTime;
	}

	@Override
	public String toString()
	{
		return toHHMMSS();
	}
}
