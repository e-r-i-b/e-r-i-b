package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;

/**
 * @author Gololobov
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * Валидатор сравнивает время периода в зависимотси от типа периода.
 */

public class DateTimePeriodMultiFieldValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_DATE_FROM = "date_from"; //дата начала периода
	public static final String FIELD_TIME_FROM = "time_from"; //время начала периода
	public static final String FIELD_DATE_TO = "date_to";     //дата окончания периода
	public static final String FIELD_TIME_TO = "time_to";     //время окончания периода
	public static final String FIELD_PERIOD_TYPE = "type_period"; //тип периода
	public static final String YAER_TYPE_PERIOD = "year"; //тип периода - год
	public static final String MONTH_TYPE_PERIOD = "month"; //тип периода - месяц
	public static final String DAY_TYPE_PERIOD = "day";     //тип периода - день
	public static final String HOUR_TYPE_PERIOD = "hour";   //тип периода - час

	private static final Long ONE_DAY_MILLIS = 1000L*3600*24; //миллисекунд в дне
	private static final Long ONE_HOUR_MILLIS = 1000L*3600;   //миллисекунд в часе

	//Тип периода (час, день, месяц)
	private String typePeriod;
	private int lengthPeriod = 1;//продолжительность периода(например, 10 дней)

	public String getTypePeriod()
	{
		return typePeriod;
	}

	public void setTypePeriod(String typePeriod)
	{
		this.typePeriod = typePeriod;
	}

	public int getLengthPeriod()
	{
		return lengthPeriod;
	}

	public void setLengthPeriod(int lengthPeriod)
	{
		this.lengthPeriod = lengthPeriod;
	}

	/*
		 * Если тип периода час, то период должен быть не более часа.
		 * Если тип периода день - не более дня.
		 * Если месяц - не более месяца.
		 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			Calendar dateTimeFrom = DateHelper.createCalendar(retrieveFieldValue(FIELD_DATE_FROM, values),retrieveFieldValue(FIELD_TIME_FROM, values));
			Calendar dateTimeTo = DateHelper.createCalendar(retrieveFieldValue(FIELD_DATE_TO, values),retrieveFieldValue(FIELD_TIME_TO, values));
			if (typePeriod.equals(MONTH_TYPE_PERIOD))
				dateTimeFrom.add(Calendar.MONTH, lengthPeriod);
			if (typePeriod.equals(YAER_TYPE_PERIOD))
				dateTimeFrom.add(Calendar.YEAR, lengthPeriod);

			return (typePeriod.equals(HOUR_TYPE_PERIOD) && DateHelper.diff(dateTimeTo,dateTimeFrom) <= ONE_HOUR_MILLIS) ||
				   (typePeriod.equals(DAY_TYPE_PERIOD) && DateHelper.diff(dateTimeTo,dateTimeFrom) <= lengthPeriod*ONE_DAY_MILLIS) ||
				   (typePeriod.equals(MONTH_TYPE_PERIOD) && dateTimeTo.compareTo(dateTimeFrom) <= 0)||
				   (typePeriod.equals(YAER_TYPE_PERIOD) && dateTimeTo.compareTo(dateTimeFrom) <= 0);
		}
		catch (ParseException ignored)
		{
			return false;
		}
	}
}
