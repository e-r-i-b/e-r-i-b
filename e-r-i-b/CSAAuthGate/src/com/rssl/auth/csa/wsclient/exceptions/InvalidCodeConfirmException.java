package com.rssl.auth.csa.wsclient.exceptions;

import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * »сключение при неверном коде подверждени€
 * @author niculichev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class InvalidCodeConfirmException extends BackLogicException
{
	private final ConfirmStrategyType confirmStrategyType;
	private final Long attempts;
	private final Long time;

	/**
	 * конструктор
	 * @param confirmStrategyType тип подтверждени€
	 * @param attempts оставшеес€ количество попыток
	 * @param time оставшеес€ врем€ подтверждени€
	 */
	public InvalidCodeConfirmException(ConfirmStrategyType confirmStrategyType, Long attempts, Long time)
	{
		this.confirmStrategyType = confirmStrategyType;
		this.attempts = attempts;
		this.time = time;
	}

	protected InvalidCodeConfirmException(InvalidCodeConfirmException cause)
	{
		super(cause);
		this.confirmStrategyType = cause.getConfirmStrategyType();
		this.attempts = cause.getAttempts();
		this.time = cause.getTime();
	}

	/**
	 * @return тип подтверждени€
	 */
	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	/**
	 * @return ќставшиес€ попытки
	 */
	public Long getAttempts()
	{
		return attempts;
	}

	/**
	 * @return ќставшеес€ врем€
	 */
	public Long getTime()
	{
		return time;
	}
}
