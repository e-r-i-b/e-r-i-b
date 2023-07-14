package com.rssl.phizic.web.wsclient.webApi;

import org.w3c.dom.Document;

/**
 * Запрос в WebAPI
 * @author Jatsky
 * @ created 22.04.14
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
	Document getBody();
}
