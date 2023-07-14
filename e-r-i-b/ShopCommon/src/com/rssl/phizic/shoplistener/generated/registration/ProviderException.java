package com.rssl.phizic.shoplistener.generated.registration;

/**
 * @author gladishev
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ProviderException extends RegistrationException
{
	private static final Long ERROR_CODE = -2L;
	private static final String ERROR_DESCRIPTION = "Service Provider not found";

	public ProviderException()
	{
		super(ERROR_CODE, ERROR_DESCRIPTION);
	}
}
