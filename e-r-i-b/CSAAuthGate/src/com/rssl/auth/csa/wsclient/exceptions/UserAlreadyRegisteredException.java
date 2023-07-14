package com.rssl.auth.csa.wsclient.exceptions;

/**
 * ����������, ��������������� � ���, ��� ������������ ��� ���������������� � �������,
 * � ���������� ����������� ��������� ����������
 * @author niculichev
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */
public class UserAlreadyRegisteredException extends RestrictionViolatedException
{
	public UserAlreadyRegisteredException()
	{
		super();
	}

	public UserAlreadyRegisteredException(String message)
	{
		super(message);
	}

	public UserAlreadyRegisteredException(Throwable cause)
	{
		super(cause);
	}

	public UserAlreadyRegisteredException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
