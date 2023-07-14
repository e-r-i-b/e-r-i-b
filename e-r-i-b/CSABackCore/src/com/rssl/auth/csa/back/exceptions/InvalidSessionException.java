package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author krenev
 * @ created 27.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class InvalidSessionException extends LogicException
{
	public InvalidSessionException(String message)
	{
		super(message);
	}
}
