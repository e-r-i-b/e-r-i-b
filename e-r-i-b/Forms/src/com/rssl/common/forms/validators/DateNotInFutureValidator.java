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
	 * ������ ��.��.����
	 */
	public DateNotInFutureValidator()
	{
		super();
	}

	/**
	 *  @param  formatString ������ ����\�������
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
