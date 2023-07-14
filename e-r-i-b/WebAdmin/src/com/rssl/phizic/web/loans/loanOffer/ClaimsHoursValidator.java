package com.rssl.phizic.web.loans.loanOffer;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

/**
 * ���������, �����������, ��� ��������� �������� ������������ � ����� �� ��������� 7 ����.
 * @author Dorzhinov
 * @ created 21.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ClaimsHoursValidator extends FieldValidatorBase
{
	private static final short HOURS = 24 * 7;

	public ClaimsHoursValidator()
	{
		setMessage("����� ������� ���������� �� ����� 7 ����");
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		short hours = Short.parseShort(value);
		return hours <= HOURS;
	}
}
