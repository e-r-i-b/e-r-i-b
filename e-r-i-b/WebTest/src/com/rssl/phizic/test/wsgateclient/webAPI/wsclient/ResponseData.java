package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;

/**
 * Ответ из WebAPI
 * @author Jatsky
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public interface ResponseData
{
	/**
	 * @return тело ответа
	 */
	Document getBody();
}
