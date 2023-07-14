package com.rssl.phizic.web.wsclient.webApi;

import org.w3c.dom.Document;

/**
 * Запрос на выход
 * @author Jatsky
 * @ created 22.04.14
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

