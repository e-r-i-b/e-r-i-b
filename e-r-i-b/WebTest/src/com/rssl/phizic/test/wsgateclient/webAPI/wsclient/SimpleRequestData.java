package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;

/**
 * @author Balovtsev
 * @since 25.04.14
 */
public class SimpleRequestData extends RequestDataBase
{
	public Document getBody()
	{
		return createRequest();
	}
}
