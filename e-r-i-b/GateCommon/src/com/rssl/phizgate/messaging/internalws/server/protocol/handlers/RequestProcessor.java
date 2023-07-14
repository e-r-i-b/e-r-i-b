package com.rssl.phizgate.messaging.internalws.server.protocol.handlers;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса
 */

public interface RequestProcessor
{
	/**
	 * Обработать запрос и вернуть результат
	 * @param requestInfo информация о запросе
	 * @return информация об ответе
	 */
	ResponseInfo process(RequestInfo requestInfo) throws Exception;
}
