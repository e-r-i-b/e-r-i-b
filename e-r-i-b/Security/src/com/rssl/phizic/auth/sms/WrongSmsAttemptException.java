package com.rssl.phizic.auth.sms;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author eMakarov
 * @ created 27.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class WrongSmsAttemptException extends SecurityLogicException
{
	private long attemptsLeft;

	public WrongSmsAttemptException(long attemptsLeft)
	{
		super("Неверный пароль. Осталось попыток: "+ attemptsLeft);
		this.attemptsLeft = attemptsLeft;
	}

	public long getAttemptsLeft()
	{
		return attemptsLeft;
	}
}
