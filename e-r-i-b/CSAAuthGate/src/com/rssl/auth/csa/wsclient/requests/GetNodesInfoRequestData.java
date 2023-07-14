package com.rssl.auth.csa.wsclient.requests;

import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на получение данных о блоках системы
 */
public class GetNodesInfoRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "getNodesInfoRq";

	/**
	 * ctor
	 */
	public GetNodesInfoRequestData()
	{
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		return createRequest();
	}
}
