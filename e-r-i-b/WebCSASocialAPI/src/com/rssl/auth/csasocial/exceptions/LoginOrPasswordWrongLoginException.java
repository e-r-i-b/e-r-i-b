package com.rssl.auth.csasocial.exceptions;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;

/**
 * @author osminin
 * @ created 01.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Ошибка ввода пароля или логина
 */
public class LoginOrPasswordWrongLoginException extends FrontLogicException
{
	private static final String MESSAGE = "Ошибка регистрации. Доступ в систему запрещен. Неправильно введен логин или пароль.";

	public LoginOrPasswordWrongLoginException()
	{
		this(MESSAGE);
	}

	public LoginOrPasswordWrongLoginException(String message)
	{
		super(message);
	}

	public LoginOrPasswordWrongLoginException(Throwable cause)
	{
		super(cause);
	}

	public LoginOrPasswordWrongLoginException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
