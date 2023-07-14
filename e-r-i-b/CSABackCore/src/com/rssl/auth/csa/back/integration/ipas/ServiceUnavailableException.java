package com.rssl.auth.csa.back.integration.ipas;

/**
 * @author krenev
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 * Исключение, сигнализующее о недоступноси сервиса iPas.
 * Выброшенное исключение сигналзирует о неполученном ответе от ipas
 */
public class ServiceUnavailableException extends Exception
{
	public ServiceUnavailableException(Exception e)
	{
		super(e);
	}

	public ServiceUnavailableException(String message)
	{
		super(message);
	}
}
