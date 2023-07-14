package com.rssl.phizic.operations.validators;

import com.rssl.phizic.business.BusinessException;

/**
 * @author Dorzhinov
 * @ created 26.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class NotOwnLinkException extends BusinessException
{
	public NotOwnLinkException(String message)
	{
		super(message);
	}

	public NotOwnLinkException(Throwable cause)
	{
		super(cause);
	}

	public NotOwnLinkException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
