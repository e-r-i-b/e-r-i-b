package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.w3c.dom.Document;

/**
 * @author Evgrafov
 * @ created 09.03.2007
 * @ $Author: niculichev $
 * @ $Revision: 38254 $
 */

public interface OnlineServiceFacade extends Service
{
	/**
	 * Отправка online сообщения во внешнюю систему
	 * @param message XML сообщение (только существенная часть без тега message и др.)
	 * @param messageHead поля в заголовке сообщения
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException ошибка для отображения ползователю
	 * @throws com.rssl.phizic.gate.exceptions.GateException ошибка транспорта или еще что-то в этом духе
	 * @return XML документ - ответ внешней системы
	 */
	Document sendOnlineMessage(Document message, MessageHead messageHead) throws GateLogicException, GateException;

	/**
	 * Создание запроса
	 * @param nameRequest имя запрса
	 * @return запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	GateMessage createRequest(String nameRequest) throws GateException;

	/**
	 * Отправка online сообщения во внешнюю систему
	 *
	 * @param request запрос
	 * @param messageHead поля в заголовке сообщения
	 * @return XML документ - ответ внешней системы
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException ошибка для отображения ползователю
	 * @throws com.rssl.phizic.gate.exceptions.GateException      ошибка транспорта или еще что-то в этом духе
	 */
	Document sendOnlineMessage(GateMessage request, MessageHead messageHead) throws GateLogicException, GateException;
}
