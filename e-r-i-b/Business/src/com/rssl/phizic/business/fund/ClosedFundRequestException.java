package com.rssl.phizic.business.fund;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author osminin
 * @ created 24.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Исключение информирует о том, что запрос на сбор средств закрыт.
 */
public class ClosedFundRequestException extends BusinessLogicException
{
	/**
	 * ctor
	 * @param message сообщение об ошибке
	 */
	public ClosedFundRequestException(String message)
	{
		super(message);
	}
}
