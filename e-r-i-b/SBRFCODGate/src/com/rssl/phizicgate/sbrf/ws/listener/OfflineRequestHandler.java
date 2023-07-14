package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 09.01.2007
 * Time: 12:39:53
 * To change this template use File | Settings | File Templates.
 */

/**
 * Обработчик оффлайновых сообщений
 */
public interface OfflineRequestHandler
{
	/**
	 * Обработать сообщение
	 * @param messageInfoContainer информация о сообщении
	 * @param object объект связанный с сообщением
	 * @return результат обработки, true- сообщение удачно обработано
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public boolean handleMessage(InternalMessageInfoContainer messageInfoContainer, Object object) throws GateException, GateLogicException;

	/**
	 * @return Возвращает состояние в которое требуется перевести документ
	 */
	public DocumentCommand getUpdateCommand(InternalMessageInfoContainer messageInfoContainer);
}
