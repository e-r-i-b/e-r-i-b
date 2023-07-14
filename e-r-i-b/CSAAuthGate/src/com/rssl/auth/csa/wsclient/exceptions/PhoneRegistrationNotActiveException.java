package com.rssl.auth.csa.wsclient.exceptions;

/**
 * @author osminin
 * @ created 19.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ����������, �������������� � ���� �������� ������ �� ��������� ���������� (�� ��������� ��������)
 */
public class PhoneRegistrationNotActiveException extends BackLogicException
{
	/**
	 * ctor
	 * @param cause exception
	 */
	public PhoneRegistrationNotActiveException(Exception cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public PhoneRegistrationNotActiveException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param cause exception
	 */
	public PhoneRegistrationNotActiveException(String message, Exception cause)
	{
		super(message, cause);
	}
}
