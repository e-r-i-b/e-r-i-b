package com.rssl.phizgate.messaging.internalws.client;

import org.w3c.dom.Document;

/**
 * Запрос
 * @author niculichev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface RequestData
{
	/**
	 * @return Имя запроса
	 */
	String getName();

	/**
	 * @return тело запроса ввиде строки
	 */
	Document getBody() throws Exception;
}
