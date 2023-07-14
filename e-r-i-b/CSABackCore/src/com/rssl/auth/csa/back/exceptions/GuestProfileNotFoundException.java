package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Исключение, говорящее о том, что гостеовой профиль не найден.
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestProfileNotFoundException extends LogicException
{
	public GuestProfileNotFoundException(String message)
	{
		super(message);
	}
}
