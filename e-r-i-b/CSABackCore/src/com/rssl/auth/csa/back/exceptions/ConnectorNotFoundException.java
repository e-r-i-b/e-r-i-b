package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author krenev
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConnectorNotFoundException extends LogicException
{
	public ConnectorNotFoundException(String message)
	{
		super(message);
	}
}
