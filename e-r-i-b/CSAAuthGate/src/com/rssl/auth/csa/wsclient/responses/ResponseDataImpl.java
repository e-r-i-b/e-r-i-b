package com.rssl.auth.csa.wsclient.responses;

import org.w3c.dom.Document;

/**
 * @author niculichev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ResponseDataImpl implements ResponseData
{
	private Document body;

	public ResponseDataImpl(Document body)
	{
		this.body = body;
	}

	public Document getBody()
	{
		return body;
	}
}
