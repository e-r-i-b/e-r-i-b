package com.rssl.auth.csasocial.exceptions;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;

/**
 * @author osminin
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 *
 * Ошибка регистрации
 */
public class RegistrationErrorException extends FrontLogicException
{
	private static final String MESSAGE = "К сожалению, сейчас регистрация не может быть завершена. Пожалуйста, повторите попытку позже.";

	public RegistrationErrorException()
	{
		super(MESSAGE);
	}

	public RegistrationErrorException(String message)
	{
		super(message);
	}

	public RegistrationErrorException(Throwable cause)
	{
		super(cause);
	}

	public RegistrationErrorException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
