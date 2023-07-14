package com.rssl.auth.csa.front.exceptions;

/**
 * Front'���� ������������� ����������, ���������������� � ������������� �������� ��� ��-�� ������� SIM-�����
 * @author Jatsky
 * @ created 02.12.14
 * @ $Author$
 * @ $Revision$
 */

public class SendSmsMessageFrontException extends FrontLogicException
{
	private static final String messagePrefix = "����������� ���������� �� ����� ���� ���������, ��� ��� �� �������� SIM-����� ��� ������ �������� ";
	private static final String messagePostfix = ". � ����� ������������ ���������� � ���������� ����� ��������� �� ��������8 800 555-55-50 ��� ������������� ������ SIM-�����.";
	private String phones;

	public SendSmsMessageFrontException(String phones)
	{
		super(messagePrefix + phones + messagePostfix);
		this.phones = phones;
	}

	public String getPhones()
	{
		return phones;
	}
}
