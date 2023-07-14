package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 * ѕровер€ет, что значение даты находитс€ в промежутке между
 * первый день текущего мес€ца + значение параметра before (в мес€цах)
 * и
 * первый день текущего мес€ца + значение параметра after (в мес€цах),
 * если параметр after не задан, то считаетс€, что дата текуща€
 *
 * @author lepihina
 * @ created 19.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class MonthPeriodFieldValidator extends FieldValidatorBase
{
	public static final String BEFORE_MONTH = "before";
	public static final String AFTER_MONTH = "after";
	private Integer beforeMonths = null;
	private Integer afterMonths = null;
	private DateFormat format;

	/**
	 * ‘ормат даты ƒƒ.ћћ.√√√√
	 */
	public MonthPeriodFieldValidator()
	{
		format = DateFormat.getDateInstance(DateFormat.SHORT);
		format.setLenient(false);
	}

	/**
	 * @param formatString формат даты
	 */
	public MonthPeriodFieldValidator(String formatString)
	{
		this.format = new SimpleDateFormat(formatString);
		this.format.setLenient(false);
	}

	public void setParameter(String name, int value)
	{
		if (BEFORE_MONTH.equals(name))
		{
			beforeMonths = value;
		}
		if (AFTER_MONTH.equals(name))
		{
			afterMonths = value;
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

		Calendar before = DateHelper.getFirstDayOfMonth(DateHelper.getCurrentDate());
		if (beforeMonths != null)
			before.add(Calendar.MONTH, beforeMonths);

		Calendar after;
		if (afterMonths != null)
		{
			after = DateHelper.getFirstDayOfMonth(DateHelper.getCurrentDate());
			after.add(Calendar.MONTH, afterMonths);
		}
		else
			after = DateHelper.getCurrentDate();

		return date.compareTo(before) >= 0 && date.compareTo(after) <= 0;
	}
}
