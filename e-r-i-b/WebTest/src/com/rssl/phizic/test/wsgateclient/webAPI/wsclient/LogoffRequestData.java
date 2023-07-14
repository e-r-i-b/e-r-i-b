package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;

/**
 * Запрос на выход
 * @author Jatsky
 * @ created 11.12.13
 * @ $Author$
 * @ $Revision$
 */

public class LogoffRequestData extends RequestDataBase
{
	public Document getBody()
	{
		return createRequest();
	}
}

