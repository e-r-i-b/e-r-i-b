package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author akrenev
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 *
 * ���������, ��� ���� �� � �������.
 */
public class DateNotInPastValidator extends DateFieldValidator
{
	/**
	 * ������ ��.��.����
	 */
	public DateNotInPastValidator()
	{
		super();
	}

	/**
	 *  @param  formatString ������ ����\�������
	 */
	public DateNotInPastValidator(String formatString)
	{
		super(formatString);
	}

	/**
	 *  @param  formatString ������ ����\�������
	 */
	public DateNotInPastValidator(String formatString,String message)
	{
		super(formatString,message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		return validate(value, DateHelper.getCurrentDate().getTime(), maxDate);
	}
}