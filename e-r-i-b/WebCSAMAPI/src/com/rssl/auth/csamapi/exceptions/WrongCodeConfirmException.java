package com.rssl.auth.csamapi.exceptions;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;

/**
 * @author osminin
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 *
 * Ошибка подтверждения
 */
public class WrongCodeConfirmException extends FrontLogicException
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
