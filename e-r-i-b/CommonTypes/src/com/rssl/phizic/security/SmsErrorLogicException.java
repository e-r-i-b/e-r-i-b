package com.rssl.phizic.security;

/**
 * Исключение кидаемое при неверном вводе смс пароля. Создано для реализации особого отображения этого вида ошибок на некоторых формах.
 * @author basharin
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public class SmsErrorLogicException extends SecurityLogicException
{
	private long lastAttemptsCount;

	public SmsErrorLogicException(String message, long lastAttemptsCount)
	{
		super(message);
		this.lastAttemptsCount = lastAttemptsCount;
	}

	public long getLastAttemptsCount()
	{
		return lastAttemptsCount;
	}
}
