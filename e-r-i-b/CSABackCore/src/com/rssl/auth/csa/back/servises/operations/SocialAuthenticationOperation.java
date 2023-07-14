package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.ConnectorType;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */
public class SocialAuthenticationOperation extends MobileAuthenticationOperation
{

	public SocialAuthenticationOperation()
	{
	}

	public SocialAuthenticationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

    protected ConnectorType getConnectorType()
    {
        return ConnectorType.SOCIAL;
    }
}
