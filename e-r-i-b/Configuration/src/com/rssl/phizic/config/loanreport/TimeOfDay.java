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
 * ����� ��� (�����)
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
	 * ������ ����� ��� �� ������ ������� "��:��:��"
	 * @param timeString - ����� ����� � ���� ������ (never null nor empty)
	 * @return ����� ��� (never null)
	 */
	public static TimeOfDay fromHHMMSS(String timeString)
	{
		if (StringHelper.isEmpty(timeString))
		    throw new IllegalArgumentException("�� ������� ����� �����");

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		try
		{
			Date date = dateFormat.parse(timeString);
			return new TimeOfDay(DateHelper.toCalendar(date));
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException("������� ���������� ����� �����: " + timeString, e);
		}
	}

	/**
	 * ���������� ����� ��� � ���� ������ ������� "��:��:��"
	 * @return ����� ����� � ���� ������ (never null nor empty)
	 */
	public String toHHMMSS()
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = DateHelper.toDate(calendar);
		return dateFormat.format(date);
	}

	/**
	 * ���������� ����+����� �� �������� ����
	 * @param instantDate - ����, ������������ ������� �������������� ����+�����
	 * @return ����+����� (never null)
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
