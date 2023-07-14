package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Исключение, говорящее о том, что гость уже зарегистрирован в системе
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestAlreadyRegistrationException extends LogicException
{
	public GuestAlreadyRegistrationException(String message)
	{
		super(message);
	}
}
