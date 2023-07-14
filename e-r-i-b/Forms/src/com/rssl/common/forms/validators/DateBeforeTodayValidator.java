package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author koptyaev
 * @ created 26.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class DateBeforeTodayValidator extends DateFieldValidator
{
	/**
	 * ������ ��.��.����
	 */
	public DateBeforeTodayValidator()
	{
		super();
	}

	/**
	 *  @param  formatString ������ ����\�������
	 */
	public DateBeforeTodayValidator(String formatString)
	{
		super(formatString);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		return validate(value,minDate, DateHelper.endOfDay(DateHelper.getPreviousDay()).getTime());
	}
}
