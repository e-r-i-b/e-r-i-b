package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.w3c.dom.Document;

/**
 * @author Evgrafov
 * @ created 09.03.2007
 * @ $Author: niculichev $
 * @ $Revision: 38254 $
 */

public interface OfflineServiceFacade
{
	/**
	 * ќтправка offline сообщени€ во внешнюю систему
	 * @param message XML сообщение (только существенна€ часть без тега message  и др.)
	 * @param messageHead пол€ в заголовке сообщени€
	 * @return XML документ - ответ внешней системы
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException внешн€€ система вернула ошибку дл€ отображени€ клиенту
	 * @throws com.rssl.phizic.gate.exceptions.GateException ошибка транспорта или еще что-то в этом духе
	 */
	Document sendOfflineMessage(Document message, MessageHead messageHead) throws GateLogicException, GateException;

	/**
	 * ќтправка offline сообщени€ во внешнюю систему
	 *
	 * @param request запрос
	 * @param messageHead пол€ в заголовке сообщени€
	 * @return XML документ - ответ внешней системы
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException внешн€€ система вернула ошибку дл€ отображени€ клиенту
	 * @throws com.rssl.phizic.gate.exceptions.GateException      ошибка транспорта или еще что-то в этом духе
	 */
	Document sendOfflineMessage(GateMessage request, MessageHead messageHead) throws GateLogicException, GateException;

	/**
	 * —оздание запроса
	 * @param nameRequest
	 * @return запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	GateMessage createRequest(String nameRequest) throws GateException;
}
