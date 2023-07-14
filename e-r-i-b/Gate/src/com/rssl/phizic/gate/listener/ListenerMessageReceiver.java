package com.rssl.phizic.gate.listener;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Roshka
 * @ created 18.10.2006
 * @ $Author$
 * @ $Revision$
 */

public interface ListenerMessageReceiver extends Service
{
	/**
	 * Вход в обработку запросов внешних систем.
	 * @param message xml-строка
	 * @return xml-строка
	 */
	String handleMessage(String message) throws GateException;
}
