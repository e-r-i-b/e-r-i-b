package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * Валидатор даты в промежутке от года до текущей даты до конца текущего месяца
 * @author Jatsky
 * @ created 03.04.14
 * @ $Author$
 * @ $Revision$
 */

public class DateInYearBeforeCurrentDateValidator extends DateFieldValidator
{

	public DateInYearBeforeCurrentDateValidator()
	{
		super();
	}

	public DateInYearBeforeCurrentDateValidator(String formatString)
	{
		super(formatString);
	}

	public DateInYearBeforeCurrentDateValidator(String formatString, String message)
	{
		super(formatString, message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		Calendar maxDate = DateHelper.getCurrentDate();

		Calendar minDate = DateHelper.getFirstDayOfMonth(DateHelper.getCurrentDate());
		minDate.add(Calendar.YEAR, -1);
		minDate.add(Calendar.MONTH, -1);

		return validate(value, minDate.getTime(), maxDate.getTime());
	}
}
