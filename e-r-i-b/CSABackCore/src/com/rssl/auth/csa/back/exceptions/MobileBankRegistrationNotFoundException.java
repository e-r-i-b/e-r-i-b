package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Исключение при отсутствии регистраций в мобильном банке
 * @author niculichev
 * @ created 02.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankRegistrationNotFoundException extends LogicException
{
	public MobileBankRegistrationNotFoundException(String message)
	{
		super(message);
	}
}
