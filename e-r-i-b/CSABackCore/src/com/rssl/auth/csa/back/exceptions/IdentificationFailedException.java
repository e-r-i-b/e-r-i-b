package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author krenev
 * @ created 12.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class IdentificationFailedException extends LogicException
{
	public IdentificationFailedException(Throwable cause)
	{
		super(cause);
	}

	public IdentificationFailedException(String message)
	{
		super(message);
	}
}
