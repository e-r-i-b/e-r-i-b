package com.rssl.phizic.business.ermb.sms.inbox;

/**
 * @author Gulov
 * @ created 11.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Exception, ������������� ��� ���������� � ������� SMS_INBOX, � ������ ������������ �������
 */
public class DublicateSmsInBoxException extends Exception
{
	public DublicateSmsInBoxException()
	{
		super("��� � ����� ������� �������� � ������� ��� ����������.");
	}

	public DublicateSmsInBoxException(String message)
	{
		super(message);
	}

	public DublicateSmsInBoxException(Throwable cause)
	{
		super(cause);
	}
}
