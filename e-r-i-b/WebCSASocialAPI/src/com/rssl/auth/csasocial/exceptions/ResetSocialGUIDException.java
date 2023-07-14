package com.rssl.auth.csasocial.exceptions;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;

/**
 * @author osminin
 * @ created 25.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Исключение, сигнализирующее о необходимости сбросить регистрацию мобильного приложения
 */
public class ResetSocialGUIDException extends FrontLogicException
{
	public ResetSocialGUIDException(String message)
	{
		super(message);
	}

	public ResetSocialGUIDException(Throwable cause)
	{
		super(cause);
	}

	public ResetSocialGUIDException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
