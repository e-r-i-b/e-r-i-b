package com.rssl.auth.csa.wsclient.requests;

import org.w3c.dom.Document;

/**
 * Запрос в CSABack
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
	Document getBody();
}
