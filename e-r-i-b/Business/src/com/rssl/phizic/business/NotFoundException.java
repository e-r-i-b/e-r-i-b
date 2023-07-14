package com.rssl.phizic.business;

/**
 * @author Kosyakov
 * @ created 16.03.2007
 * @ $Author: kosyakov $
 * @ $Revision: 3802 $
 */
public class NotFoundException extends BusinessException
{
	public NotFoundException(String message)
	{
		super(message);
	}

	public NotFoundException(Throwable cause)
	{
		super(cause);
	}

	public NotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
