package com.rssl.phizgate.messaging.internalws.client;

import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 13.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ResponseData
{
	private Document body;

	public ResponseData(Document body)
	{
		this.body = body;
	}

	public Document getBody()
	{
		return body;
	}
}
