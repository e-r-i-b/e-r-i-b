package com.rssl.phizic.business.documents.exceptions;

import com.rssl.phizic.business.BusinessException;

/**
 * @author Gainanov
 * @ created 12.04.2007
 * @ $Author$
 * @ $Revision$
 */
public class NotOwnDocumentException extends BusinessException
{
	public NotOwnDocumentException(String message)
	{
		super(message);
	}

	public NotOwnDocumentException(Throwable cause)
	{
		super(cause);
	}

	public NotOwnDocumentException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
