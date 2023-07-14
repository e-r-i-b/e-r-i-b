package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Krenev
 * @ created 23.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * проверяет, что значение поля (дата) находится в промежутке между
 * текущая дата + значение параметра before (в днях)
 * и
 * текущая дата + значение параметра after (в днях)
 *
 * если параметры before и after не заданы, то значение их принимается равным 0
 *
 * Также проверяет, соответствует ли значение поля (дата) текущей дате или больше текущей даты
 *
 */
public class DatePeriodFieldValidator extends FieldValidatorBase
{
	public static final String BEFORE_DAY = "before";
	public static final String AFTER_DAY = "after";
	public static final String INFINITY = "infinity";
	private String beforeDays;
	private String afterDays;
	private DateFormat format;

	/**
	 * Формат даты ДД.ММ.ГГГГ
	 */
	public DatePeriodFieldValidator()
	{
		format = DateFormat.getDateInstance(DateFormat.SHORT);
		format.setLenient(false);
	}

	/**
	 * @param formatString формат даты
	 */
	public DatePeriodFieldValidator(String formatString)
	{
		this.format = new SimpleDateFormat(formatString);
		this.format.setLenient(false);
	}

	public String getParameter(String name)
	{
		if (BEFORE_DAY.equals(name))
		{
			return String.valueOf(BEFORE_DAY);
		}
		if (AFTER_DAY.equals(name))
		{
			return String.valueOf(AFTER_DAY);
		}
		return super.getParameter(name);
	}

	public void setParameter(String name, String value)
	{
		if (BEFORE_DAY.equals(name))
		{
			beforeDays = value;
		}
		if (AFTER_DAY.equals(name))
		{
			afterDays = value;
		}
	}

    private DateFormat getFormat()
    {
		return (DateFormat)format.clone();    
    }

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (isValueEmpty(value))
		    return true;

		Calendar date = null;
		try
		{
			date = DateHelper.toCalendar(getFormat().parse(value));
			if (date == null)
				return false;
		}
		catch (ParseException ignore)
		{
			return false;
		}

		if (afterDays == null)
		{
			afterDays = "0";
		}

		if (beforeDays == null)
		{
			beforeDays = "0";
		}

		if(afterDays.equals(INFINITY)){
			Calendar today = DateHelper.getCurrentDate();
			return (today.compareTo(date)<=0);
		}
		Calendar before = DateHelper.getCurrentDate();
		before.add(Calendar.DAY_OF_MONTH, Integer.parseInt(beforeDays));

		Calendar after = DateHelper.getCurrentDate();
		after.add(Calendar.DAY_OF_MONTH, Integer.parseInt(afterDays));
		return date.compareTo(before)>=0 && date.compareTo(after)<=0;
	}
}
