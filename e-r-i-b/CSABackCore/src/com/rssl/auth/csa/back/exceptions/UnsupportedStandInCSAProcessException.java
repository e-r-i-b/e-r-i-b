package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Исключение при недоступности операции по причине StandIn
 * @author niculichev
 * @ created 17.04.15
 * @ $Author$
 * @ $Revision$
 */
public class UnsupportedStandInCSAProcessException extends LogicException
{
	public UnsupportedStandInCSAProcessException(String message)
	{
		super(message);
	}
}
