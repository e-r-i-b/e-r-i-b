package com.rssl.phizic.business.persons.validators;

import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * �������� �� ��, ��� ���� �� �������� �������� � ������ ���/� � ����� ������
 * @author egorova
 * @ created 10.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class LeadingEndingSpacesValidator extends RegexpFieldValidator
{
	private static final String regexp = "^(?=\\S).+(?<=\\S)$";

	public LeadingEndingSpacesValidator()
	{
		super(regexp);
	}
}
