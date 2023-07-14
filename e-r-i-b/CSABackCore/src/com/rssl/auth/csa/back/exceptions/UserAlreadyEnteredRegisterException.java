package com.rssl.auth.csa.back.exceptions;

/**
 * @ author: Vagin
 * @ created: 19.08.13
 * @ $Author
 * @ $Revision
 * Исключение, клиен ранее входил в ЕРИБ. Повторная регисрация в таком случае(и если задана настройка) запрещена.
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
