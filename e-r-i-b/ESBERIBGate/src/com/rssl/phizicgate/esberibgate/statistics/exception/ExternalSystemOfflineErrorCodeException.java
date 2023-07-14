package com.rssl.phizicgate.esberibgate.statistics.exception;

import com.rssl.phizic.gate.utils.OfflineExternalSystemException;

/**
 * @author akrenev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 *
 * исключение ошибки обработки запроса во врешней системе (оффлайн)
 */

public class ExternalSystemOfflineErrorCodeException extends OfflineExternalSystemException
{
	/**
	 * конструктор
	 * @param message сообщение
	 */
	public ExternalSystemOfflineErrorCodeException(String message)
	{
		super(message);
	}
}
