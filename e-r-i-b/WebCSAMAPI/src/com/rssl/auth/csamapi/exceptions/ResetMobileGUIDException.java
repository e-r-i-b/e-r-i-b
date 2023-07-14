package com.rssl.auth.csamapi.exceptions;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;

/**
 * @author osminin
 * @ created 25.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Исключение, сигнализирующее о необходимости сбросить регистрацию мобильного приложения
 */
public class ResetMobileGUIDException extends FrontLogicException
{
	public ResetMobileGUIDException(String message)
	{
		super(message);
	}

	public ResetMobileGUIDException(Throwable cause)
	{
		super(cause);
	}

	public ResetMobileGUIDException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
