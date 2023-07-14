package com.rssl.phizicgate.esberibgate.ws.jms.common;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author akrenev
 * @ created 28.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * билдер запроса
 */

public interface MessageBuilder
{
	/**
	 * Сформировать запрос
	 * @param requestData информация, необходимая для запроса
	 * @return запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public <RqClass> String buildMessage(RqClass requestData) throws GateException;

}
