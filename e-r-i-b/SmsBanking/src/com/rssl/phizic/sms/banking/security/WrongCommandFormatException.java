package com.rssl.phizic.sms.banking.security;

/**
 * @author emakarov
 * @ created 10.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class WrongCommandFormatException extends UserSendException
{
	/**
	 * �� ����� � ��� ����
	 */
	public WrongCommandFormatException()
	{
		super("�������� ������ �������");
	}
}
