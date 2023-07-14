package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * ¬алидатор: максимальный период, за который клиент может просмотреть данные Ц 1 год + текущий мес€ц
 * @author Jatsky
 * @ created 03.04.14
 * @ $Author$
 * @ $Revision$
 */

public class DateInYearBeforeEndMonthValidator extends DateFieldValidator
{

	public DateInYearBeforeEndMonthValidator()
	{
		super();
	}

	public DateInYearBeforeEndMonthValidator(String formatString)
	{
		super(formatString);
	}

	public DateInYearBeforeEndMonthValidator(String formatString, String message)
	{
		super(formatString, message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		Calendar maxDate = DateHelper.getLastDayOfMonth(DateHelper.getCurrentDate());

		Calendar minDate = DateHelper.getFirstDayOfMonth(DateHelper.getCurrentDate());
		minDate.add(Calendar.YEAR, -1);
		minDate.add(Calendar.MONTH, -1);

		return validate(value, minDate.getTime(), maxDate.getTime());
	}
}
