package com.rssl.phizic.operations.security;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 15.09.2006
 * Time: 12:29:57
 * To change this template use File | Settings | File Templates.
 */
public class UnknownPINEnvelopeException extends BusinessLogicException
{
	public UnknownPINEnvelopeException(String message)
	{
		super(message);
	}
}
