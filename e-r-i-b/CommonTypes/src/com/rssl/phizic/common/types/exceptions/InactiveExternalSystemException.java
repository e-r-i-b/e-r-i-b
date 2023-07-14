package com.rssl.phizic.common.types.exceptions;

import java.util.Calendar;

/**
 * Исключение при неактивности внешней системы
 *
 * @author khudyakov
 * @ created 23.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class InactiveExternalSystemException extends RuntimeException
{
	Calendar toDate;

	public InactiveExternalSystemException(String message)
	{
		super(message);
	}

	public InactiveExternalSystemException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public InactiveExternalSystemException(Throwable cause)
	{
		super(cause);
	}

	public InactiveExternalSystemException(String message, Calendar toDate)
	{
		super(message);
		this.toDate = toDate;
	}

	public Calendar getToDate()
	{
		return toDate;
	}
}
