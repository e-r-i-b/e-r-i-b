package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author egorova
 * @ created 24.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class DateNotInFutureValidator extends DateFieldValidator
{
	/**
	 * Формат ДД.ММ.ГГГГ
	 */
	public DateNotInFutureValidator()
	{
		super();
	}

	/**
	 *  @param  formatString формат даты\времени
	 */
	public DateNotInFutureValidator(String formatString)
	{
		super(formatString);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		return validate(value,minDate,DateHelper.getCurrentDate().getTime());
	}
}
