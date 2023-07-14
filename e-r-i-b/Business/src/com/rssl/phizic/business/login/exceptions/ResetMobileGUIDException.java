package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Исключение, сигнализирующее о необходимости сбросить регистрацию мобильного приложения
 * @author Pankin
 * @ created 03.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class ResetMobileGUIDException extends BusinessLogicException
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
