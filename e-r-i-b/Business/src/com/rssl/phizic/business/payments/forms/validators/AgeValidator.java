package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.utils.DateHelper;

import java.text.ParseException;
import java.util.Calendar;

/**
 * @author Gulov
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Валидатор проверяет заданное количество лет от введенной даты
 */
public class AgeValidator extends FieldValidatorBase
{
	private final int minYear;
	private final int maxYear;

	public AgeValidator(int minYear, int maxYear, String message)
	{
		super(message);
		this.minYear = minYear;
		this.maxYear = maxYear;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		Calendar date = parseValue(value);
		if (date == null)
			return false;
		DateSpan age = new DateSpan(date, Calendar.getInstance());
		int year = age.getYears();
		return year >= minYear && year <= maxYear;
	}

	private Calendar parseValue(String value)
	{
		try
		{
			return DateHelper.parseCalendar(value, "dd.MM.yyyy");
		}
		catch (ParseException e)
		{
			return null;
		}
	}
}
