package com.rssl.phizic.security.password;

/**
 * @ author: Vagin
 * @ created: 26.03.2013
 * @ $Author
 * @ $Revision
 * ���������� � ������ ���������� userId � ������� �� ������������� ������� �������.
 */
public class InvalidUserIdException extends SecurityException
{
	public InvalidUserIdException(String message, Throwable e)
	{
		super(message, e);
	}
}
