package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Исключение о ошибке входа клиента
 *
 * @author khudyakov
 * @ created 29.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class AcceptedAttemptsLoginException extends BusinessLogicException
{
	public AcceptedAttemptsLoginException(String message)
	{
		super(message);
	}

	public AcceptedAttemptsLoginException(Throwable cause)
	{
		super(cause);
	}

	public AcceptedAttemptsLoginException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
