package com.rssl.auth.csa.front.exceptions;

/**
 * Исключение, сигнализирующее о том, что пользователь уже имеет регистрацию и запрещающее продолжение операции
 * @author niculichev
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */
public class UserAlreadyRegisteredInterruptStageException extends InterruptStageException
{
	public UserAlreadyRegisteredInterruptStageException()
	{
		super();
	}

	public UserAlreadyRegisteredInterruptStageException(String message)
	{
		super(message);
	}

	public UserAlreadyRegisteredInterruptStageException(Throwable cause)
	{
		super(cause);
	}

	public UserAlreadyRegisteredInterruptStageException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
