package com.rssl.auth.csa.back.exceptions;

import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author krenev
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class AuthenticationFailedException extends LogicException
{
	private final Connector connector;

	public AuthenticationFailedException(Connector connector) {

		this.connector = connector;
	}

	public Connector getConnector()
	{
		return connector;
	}
}
