package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;

/**
 * Запрос в WebAPI
 * @author Jatsky
 * @ created 13.11.13
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
