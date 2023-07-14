package com.rssl.phizic.shoplistener.generated.registration;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Исключение при превышении лимита динамической загрузки КПУ
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ExceededFacilitatorLimitException extends GateException
{
	public ExceededFacilitatorLimitException()
	{
		super();
	}

	public ExceededFacilitatorLimitException(Throwable cause)
	{
		super(cause);
	}

	public ExceededFacilitatorLimitException(String message)
	{
		super(message);
	}

	public ExceededFacilitatorLimitException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
