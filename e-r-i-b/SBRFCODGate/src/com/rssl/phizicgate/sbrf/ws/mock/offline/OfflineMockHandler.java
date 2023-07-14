package com.rssl.phizicgate.sbrf.ws.mock.offline;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import org.w3c.dom.Document;

/**
 * @author Omeliyanchuk
 * @ created 07.05.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Изображает из себя бэк-офис. Обрабатывает поступившие платежи и не только.
 */
public interface OfflineMockHandler
{
	/**
	 * обработать и сформировать ответ для отправки
	 * @param entry
	 * @return
	 */
	Document handle(Object object) throws GateException;
}
