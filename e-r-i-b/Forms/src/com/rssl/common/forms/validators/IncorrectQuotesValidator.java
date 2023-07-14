package com.rssl.common.forms.validators;

/**
 * �������� �� ������� �� ���������� ������� (��) ANSI 147(93), 148(94)
 * @author basharin
 * @ created 23.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class IncorrectQuotesValidator extends RegexpFieldValidator
{
	private static final String regexp = "[^��]*";
	private static final String message = "� ����� ��������� ������������ ����������� �������: � �. ����������, ������� ������� ����: � �";

	public IncorrectQuotesValidator()
	{
		super(regexp, message);
	}
}