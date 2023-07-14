package com.rssl.common.forms.validators;

import java.util.Date;
import java.util.Map;

/**
 * ��������� ���������� ���� ������, ���� ��������� � ���-�� �������.
 * ������ ���� ������ ���� ��� ���� �����, ���� ���-�� �������.
 * ���� ��� ���� ���������, �� ���� ������ ������ ���� ������ ���� ����� ���� ���������.
 * ��������� �� ��������� ��������� �� ���� � �������.
 * @author Rydvanskiy
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class FilterDateCountValidator extends FilterDateValidator
{
	public static final String COUNT = "count";

	public boolean validate(Map values)
	{
		Long count = (Long) retrieveFieldValue(COUNT, values);
		Date from = (Date) retrieveFieldValue(FROM_DATE, values);
		Date to = (Date) retrieveFieldValue(TO_DATE, values);

		if (count == null)
		{
			if (from == null && to == null)
			{
				setMessage("���������� ���������� ���� ���� ������ � ���������, ���� ���������� ��������� �������");
				return false;
			}

			return validateBothDates(from, to);
		}
		if (count == 0)
		{
			setMessage("���������� ������� ��������� ���������� ��������");
			return false;
		}
		return true;
	}
}
