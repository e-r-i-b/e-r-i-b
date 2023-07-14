package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: krenev $
 * @ $Revision: 4239 $
 */

public class PasswordCardWrongAttemptException extends SecurityLogicException
{
	private final long attemptsLeft;

	/**
	 * @param attemptsLeft количество оставшихся попыток
	 */
	public PasswordCardWrongAttemptException(long attemptsLeft)
	{
		super("Неверный пароль. Осталось попыток:"+attemptsLeft);
		this.attemptsLeft = attemptsLeft;
	}

	/**
	 * @return количество оставшихся попыток
	 */
	public long getAttemptsLeft()
	{
		return attemptsLeft;
	}
}