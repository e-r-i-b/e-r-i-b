package com.rssl.phizic.security.password;

/**
 * @author Evgrafov
 * @ created 23.01.2006
 * @ $Author: emakarov $
 * @ $Revision: 8089 $
 */

public class WrongAttemptException extends PasswordValidationException
{
	private final long attemptsLeft;
	private boolean needMessage;

	/**
	 * @param attemptsLeft количество оставшихся попыток
	 */
	public WrongAttemptException(long attemptsLeft)
	{
		super("Неверный пароль. Осталось попыток:"+attemptsLeft);
		this.attemptsLeft = attemptsLeft;
		this.needMessage  = true;
	}

	/**
	 * @return количество оставшихся попыток
	 */
	public long getAttemptsLeft()
	{
		return attemptsLeft;
	}

	/**
	 * @param attemptsLeft количество оставшихся попыток
	 * @param needMessage выдавать ли сообщение об оставшихся попытках.
	 */
	public WrongAttemptException(long attemptsLeft, boolean needMessage)
	{
		super("Неверный пароль. Осталось попыток:"+attemptsLeft);
		this.attemptsLeft = attemptsLeft;
		this.needMessage = needMessage;
	}

	public boolean isNeedMessage()
	{
		return needMessage;
	}
}