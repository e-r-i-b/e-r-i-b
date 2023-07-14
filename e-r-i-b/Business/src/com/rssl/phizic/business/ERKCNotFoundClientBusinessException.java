package com.rssl.phizic.business;

/**
 * Исключение для обозначения ошибок доступа к информации о клиенте, с которым работает сотрудник ЕРКЦ
 * @author lepihina
 * @ created 22.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ERKCNotFoundClientBusinessException extends BusinessException
{
	public ERKCNotFoundClientBusinessException(String message)
	{
		super(message);
	}

	public ERKCNotFoundClientBusinessException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ERKCNotFoundClientBusinessException(Throwable cause)
	{
		super(cause);
	}
}
