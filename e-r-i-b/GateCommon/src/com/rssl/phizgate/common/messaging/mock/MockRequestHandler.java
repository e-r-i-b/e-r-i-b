package com.rssl.phizgate.common.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 02.02.2010
 * @ $Author$
 * @ $Revision$
 * Интерфейс для пустышек RequestHandler
 */
public interface MockRequestHandler
{

	/**
	 * Обработать запрос
	 * @param request запрос
	 * @return респонс
	 * @throws GateException
	 */
	public Document proccessRequest(Document request) throws GateException;
}
