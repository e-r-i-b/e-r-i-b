package com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author komarov
 * @ created 06.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * интерфейс билдера запросов
 */
public interface RequestBuilder<R, D>
{
	/**
	 * Формирует запрос по документу
	 * @param document документ
	 * @return запрос
	 */
	R makeRequest(D document) throws GateException, GateLogicException;

	/**
	 * Возвращает идентификатор запроса
	 * @param request запрос
	 * @return идентификатор запроса
	 */
	String getRequestId(R request);

	/**
	 * Возвращает тип сообщения
	 * @return тип сообщения
	 */
	String getRequestMessageType();
}
