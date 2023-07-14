package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author krenev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 * Базовый класс исключений cвязанных с ограничениями.
 */
public class RestrictionException extends LogicException
{
	public RestrictionException(String message)
	{
		super(message);
	}

	public RestrictionException(Exception cause)
	{
		super(cause.getMessage(), cause);
	}

	public RestrictionException(String message, Exception cause)
	{
		super(message, cause);
	}
}
