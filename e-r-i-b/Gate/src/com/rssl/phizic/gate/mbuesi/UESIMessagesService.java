package com.rssl.phizic.gate.mbuesi;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Сервис для работы с сообщениями UESI
 * @author Pankin
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 */
public interface UESIMessagesService extends Service
{
	/**
	 * Записать результат обработки сообщения
	 * @param externalId идентификатор сообщения
	 * @param success признак успешности обработки
	 */
	void processMessage(String externalId, boolean success) throws GateException;
}
