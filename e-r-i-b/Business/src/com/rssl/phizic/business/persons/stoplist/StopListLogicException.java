package com.rssl.phizic.business.persons.stoplist;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Roshka
 * @ created 15.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class StopListLogicException extends BusinessLogicException
{
	public StopListLogicException(Throwable cause)
	{
		super(cause);
	}

	public StopListLogicException(String message)
	{
		super(message);
	}

	public StopListLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}