package com.rssl.auth.csa.back.servises.operations.confirmations;

/**
 * @author akrenev
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * детали подтверждения смс-паролем
 */

public class SMSConfirmationInfo implements ConfirmationInfo
{
	private long lastAtempts;
	private long confirmTimeOut;

	/**
	 * конструктор
	 * @param lastAtempts оставшееся количество попыток
	 * @param confirmTimeOut оставшееся время жизни пароля
	 */
	public SMSConfirmationInfo(long lastAtempts, long confirmTimeOut)
	{
		this.lastAtempts = lastAtempts;
		this.confirmTimeOut = confirmTimeOut;
	}

	/**
	 * @return оставшееся количество попыток
	 */
	public long getLastAtempts()
	{
		return lastAtempts;
	}

	/**
	 * @return оставшееся время жизни пароля
	 */
	public long getConfirmTimeOut()
	{
		return confirmTimeOut;
	}
}
