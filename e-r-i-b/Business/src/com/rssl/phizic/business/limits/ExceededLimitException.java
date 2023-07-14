package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * исключение первышения лимита
 *
 * @author akrenev
 * @ created 05.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class ExceededLimitException extends BusinessLogicException
{
	private Limit limit;

	public ExceededLimitException(String message, Limit limit)
	{
		super(message);
		this.limit = limit;
	}

	public ExceededLimitException(String message)
	{
		super(message);
	}

	public ExceededLimitException(Throwable cause)
	{
		super(cause);
	}

	public ExceededLimitException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public Limit getLimit()
	{
		return limit;
	}
}
