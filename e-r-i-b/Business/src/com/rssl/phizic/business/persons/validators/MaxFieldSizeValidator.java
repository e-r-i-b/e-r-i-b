package com.rssl.phizic.business.persons.validators;

import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * ��������� �� �������� ������������ ����� � 255 ��������.
 * ������ ���� ��������� ����� ����� �������� �� ���� �����.
 * todo. � ����� �� ����������, ��� ���������� ��������� ���������, ������� � ���� ���������� ������ �� �����.
 * @author egorova
 * @ created 25.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class MaxFieldSizeValidator extends RegexpFieldValidator
{
	private static final String MAX_FIELD_SIZE_VALUE = "255";
	private static final String regexp = ".{0,"+MAX_FIELD_SIZE_VALUE+"}";

	public MaxFieldSizeValidator()
	{
		super(regexp);
	}

}
