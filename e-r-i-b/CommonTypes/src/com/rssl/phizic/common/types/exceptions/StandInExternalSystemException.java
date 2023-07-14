package com.rssl.phizic.common.types.exceptions;

/**
 * Исключение при неактивности внешней системы (в режиме Stand-In)
 * @author Puzikov
 * @ created 26.09.14
 * @ $Author$
 * @ $Revision$
 */

public class StandInExternalSystemException extends InactiveExternalSystemException
{
	public StandInExternalSystemException(String message)
	{
		super(message);
	}

	public StandInExternalSystemException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public StandInExternalSystemException(Throwable cause)
	{
		super(cause);
	}
}
