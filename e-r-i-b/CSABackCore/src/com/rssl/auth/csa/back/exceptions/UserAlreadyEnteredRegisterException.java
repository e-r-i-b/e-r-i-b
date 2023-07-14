package com.rssl.auth.csa.back.exceptions;

/**
 * @ author: Vagin
 * @ created: 19.08.13
 * @ $Author
 * @ $Revision
 * ����������, ����� ����� ������ � ����. ��������� ���������� � ����� ������(� ���� ������ ���������) ���������.
 */
public class UserAlreadyEnteredRegisterException extends UserAlreadyRegisteredException
{
	public UserAlreadyEnteredRegisterException(Exception cause)
	{
		super(cause);
	}

	public UserAlreadyEnteredRegisterException(String message, Exception cause)
	{
		super(message, cause);
	}

	public UserAlreadyEnteredRegisterException(String message)
	{
		super(message);
	}
}
