package com.rssl.phizic.web.webApi.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * Ошибка "по данной карте выписка недоступна"
 * @author Jatsky
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ExtendedAbstractNotAvailableException extends LogicException
{
	public ExtendedAbstractNotAvailableException(String message)
	{
		super(message);
	}
}
