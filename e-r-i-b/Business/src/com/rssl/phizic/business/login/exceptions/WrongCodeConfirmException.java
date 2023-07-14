package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Исключение неверного ввода кода подтверждения
 *
 * @author khudyakov
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class WrongCodeConfirmException extends BusinessLogicException
{
	private Long attempts;
	private Long time;

	public WrongCodeConfirmException(String message)
	{
		super(message);
	}

	public WrongCodeConfirmException(Throwable cause)
	{
		super(cause);
	}

	public WrongCodeConfirmException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public WrongCodeConfirmException(String message, Long time, Long attempts, Throwable cause)
	{
		super(message, cause);

		this.attempts = attempts;
		this.time = time;
	}

	public Long getAttempts()
	{
		return attempts;
	}

	public Long getTime()
	{
		return time;
	}
}
