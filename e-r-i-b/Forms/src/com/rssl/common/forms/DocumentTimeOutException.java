package com.rssl.common.forms;

/**
 * Исключение при наступлении таймаута
 * @author niculichev
 * @ created 09.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class DocumentTimeOutException extends DocumentLogicException
{
	public DocumentTimeOutException(String message)
	{
		super(message);
	}

	public DocumentTimeOutException(Throwable cause)
	{
		super(cause.getMessage(), cause);
	}
}
