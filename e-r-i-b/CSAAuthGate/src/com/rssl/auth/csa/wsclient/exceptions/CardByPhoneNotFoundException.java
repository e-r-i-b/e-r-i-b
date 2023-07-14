package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� - ����� �� ������ �������� �� �������
 */
public class CardByPhoneNotFoundException extends BackLogicException
{
	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public CardByPhoneNotFoundException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause ������
	 */
	public CardByPhoneNotFoundException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param cause ������
	 */
	public CardByPhoneNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
