package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * исключение ошибки регистрации
 *
 * @author khudyakov
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationErrorException extends BusinessLogicException
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
