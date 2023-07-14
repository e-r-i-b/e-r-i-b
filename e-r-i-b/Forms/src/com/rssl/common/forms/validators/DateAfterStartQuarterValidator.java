package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * Валидатор даты: минимальная дата, которая может придти - дата начала текущего квартала
 * @author Jatsky
 * @ created 03.04.14
 * @ $Author$
 * @ $Revision$
 */

public class DateAfterStartQuarterValidator extends DateFieldValidator
{

	public DateAfterStartQuarterValidator()
	{
		super();
	}

	public DateAfterStartQuarterValidator(String formatString)
	{
		super(formatString);
	}

	public DateAfterStartQuarterValidator(String formatString, String message)
	{
		super(formatString, message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		Calendar minDate = DateHelper.getStartQuarterDate(DateHelper.getCurrentDate());

		return validate(value, minDate.getTime(), maxDate);
	}
}
