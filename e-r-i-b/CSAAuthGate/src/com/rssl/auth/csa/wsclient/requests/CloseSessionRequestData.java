package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.SID_PARAM_NAME;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * ƒанные дл€ запроса на закрытие сессии.
 * 
 * @author bogdanov
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class CloseSessionRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "closeSessionRq";

	private String sid;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public CloseSessionRequestData(String sid)
	{
		this.sid = sid;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, SID_PARAM_NAME, sid));

		return request;
	}
}
