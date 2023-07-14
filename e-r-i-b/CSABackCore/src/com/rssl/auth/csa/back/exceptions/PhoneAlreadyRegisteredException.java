package com.rssl.auth.csa.back.exceptions;

/**
 * @author osminin
 * @ created 17.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ����������, ������� ��� ���������������
 */
public class PhoneAlreadyRegisteredException extends RestrictionException
{
	/**
	 * ctor
	 * @param message ��������� �� ������
	 */
	public PhoneAlreadyRegisteredException(String message)
	{
		super(message);
	}

	/**
	 * ctor
	 * @param cause �������
	 */
	public PhoneAlreadyRegisteredException(Exception cause)
	{
		super(cause);
	}

	/**
	 * ctor
	 * @param message ��������� �� ������
	 * @param cause �������
	 */
	public PhoneAlreadyRegisteredException(String message, Exception cause)
	{
		super(message, cause);
	}
}
