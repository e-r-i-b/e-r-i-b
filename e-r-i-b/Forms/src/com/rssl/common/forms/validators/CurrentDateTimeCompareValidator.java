package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.text.ParseException;

/**
 * @author vagin
 * @ created 20.11.2012
 * @ $Author$
 * @ $Revision$
 * ¬алидатор проверки указаной даты и времени(!) с текущим датой и временем.
 */
public class CurrentDateTimeCompareValidator extends DateTimeCompareValidator
{
	public boolean validate(Map values) throws TemporalDocumentException
	{
		Date fromDate = (Date) retrieveFieldValue("date", values);
		Date fromTime = (Date) retrieveFieldValue("time", values);

		String op = getParameter(OPERATOR);
		if (op == null)
			throw new RuntimeException("ѕараметр '" + OPERATOR + "' не определен!");

		Date fromDateWithTime;
		try
		{
			fromDateWithTime = DateHelper.createCalendar(fromDate, fromTime).getTime();
		}
		catch (ParseException e)
		{
			throw new TemporalDocumentException("Ќекореектное значение даты и времени.");
		}
		Date toDate = Calendar.getInstance().getTime();

		int resultDateCompare = fromDateWithTime.compareTo(toDate);

		if (op.equals(EQUAL))
		{
			return resultDateCompare == 0;
		}
		else if (op.equals(NOT_EQUAL))
		{
			return resultDateCompare != 0;
		}
		else if (op.equals(LESS))
		{
			return resultDateCompare < 0;
		}
		else if (op.equals(GREATE))
		{
			return resultDateCompare > 0;
		}
		else if (op.equals(LESS_EQUAL))
		{
			return resultDateCompare <= 0;
		}
		else if (op.equals(GREATE_EQUAL))
		{
			return resultDateCompare >= 0;
		}
		return false;
	}
}
