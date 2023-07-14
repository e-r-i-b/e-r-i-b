package com.rssl.phizic.web.log;

import com.rssl.phizic.business.BusinessException;

/**
 * @author eMakarov
 * @ created 19.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class NoLogEntriesException extends BusinessException
{

	public NoLogEntriesException(Throwable cause)
	{
		super(cause);
	}

	public NoLogEntriesException(String message)
	{
		super(message);
	}

	public NoLogEntriesException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
