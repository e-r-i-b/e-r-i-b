package com.rssl.common.forms;

/**
 * @author mihaylov
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Исключение о необходимости перевода документа в офлайн режим
 */
public class DocumentOfflineException extends DocumentLogicException
{
	public DocumentOfflineException(String message)
	{
		super(message);
	}

	public DocumentOfflineException(Throwable cause)
	{
		super(cause);
	}

	public DocumentOfflineException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
