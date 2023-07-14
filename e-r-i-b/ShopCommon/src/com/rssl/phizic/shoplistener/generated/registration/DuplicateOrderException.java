package com.rssl.phizic.shoplistener.generated.registration;

/**
 * @author gladishev
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 */
public class DuplicateOrderException extends RegistrationException
{
	public DuplicateOrderException()
	{
		super(-3L, "Order duplicate");
	}
}
