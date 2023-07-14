package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Исключение невозможности входа в систему
 *
 * @author khudyakov
 * @ created 10.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class CanNotLoginException extends BusinessLogicException
{
	private static final String MESSAGE = "Вы не можете войти в систему «Сбербанк Онлайн». Пожалуйста, обратитесь в банк.";

	public CanNotLoginException()
	{
		this(MESSAGE);
	}

	public CanNotLoginException(String message)
	{
		super(message);
	}

	public CanNotLoginException(Throwable cause)
	{
		super(cause);
	}

	public CanNotLoginException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
