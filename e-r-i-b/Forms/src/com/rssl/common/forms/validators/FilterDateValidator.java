package com.rssl.common.forms.validators;

import java.util.Map;
import java.util.Date;

/**
 * ��������� ���������� ���� ������ � ���� ���������.
 * ������ ���� ������ ���� ��� ���� �����, ���� �� �����.
 * ���� ��� ���� ���������, �� ���� ������ ������ ���� ������ ���� ����� ���� ���������.
 * ��������� �� ��������� ��������� �� ���� � �������.
 * @author Dorzhinov
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class FilterDateValidator extends MultiFieldsValidatorBase
{
	public static final String FROM_DATE = "from";
	public static final String TO_DATE = "to";

	public boolean validate(Map values)
	{
		Date from = (Date) retrieveFieldValue(FROM_DATE, values);
		Date to = (Date) retrieveFieldValue(TO_DATE, values);

		if (from == null && to == null)
			return true;

		return validateBothDates(from, to);
	}

	protected boolean validateBothDates(Date from, Date to)
	{
		if (from == null || to == null)
		{
			setMessage("���������� ���������� ���� ������ � ��������� ������ � ����");
			return false;
		}

		if (to.before(from))
		{
			setMessage("���� ������ �� ����� ���� ������ ���� ���������");
			return false;
		}
		return true;
	}
}

