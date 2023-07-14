package com.rssl.phizic.messaging;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.module.Module;

/**
 * @author Erkin
 * @ created 09.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обработчик входящих сообщений
 */
@ThreadSafe
public interface MessageProcessor<T extends XmlMessage>
{
	/**
	 * Обработать сообщение
	 * @param xmlRequest - запрос
	 */
	public void processMessage(T xmlRequest);

	/**
	 * @return Писать ли в лог.
	 */
	public boolean writeToLog();

	/**
	 * возвращает модуль
	 * @return
	 */
	public Module getModule();
}
