package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.BusinessException;

/**
 * @author vagin
 * @ created 24.02.14
 * @ $Author$
 * @ $Revision$
 * Исключение в случае неверноего userId в запросе на подтверждение чековым паролем.
 */
public class InvalidUserIdBusinessException extends BusinessException
{
	public InvalidUserIdBusinessException(Throwable cause)
	{
		super(cause);
	}
}
