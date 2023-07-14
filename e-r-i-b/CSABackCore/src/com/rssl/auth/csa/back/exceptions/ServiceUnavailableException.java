package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 * Исключение, сигнализирующе о недоступности какого-либо сервиса
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
