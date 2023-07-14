package com.rssl.phizicgate.mdm.integration.mdm.message;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * процессор онлайн jms сообщений
 */

public interface OnlineMessageProcessor<Rs> extends MessageProcessor<OnlineMessageProcessor<Rs>>
{
	/**
	 * получение селектора ответного сообщения по запросу
	 * @param message запрос
	 * @return селектор
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	String getResponseMessageSelector(Message message) throws GateException;

	/**
	 * получение внутреннего представления ответа
	 * @param message ответ
	 * @return внутреннее представление ответа
	 * @throws GateException
	 */
	Response<Rs> buildResponse(Message message) throws GateException;

	/**
	 * обработать ответ
	 * @param response ответ
	 * @throws GateLogicException
	 * @throws GateException
	 */
	void processResponse(Response<Rs> response) throws GateLogicException, GateException;
}
