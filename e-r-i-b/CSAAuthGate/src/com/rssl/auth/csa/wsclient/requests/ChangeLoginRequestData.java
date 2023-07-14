package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.LOGIN_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.SID_PARAM_NAME;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * запрос на смену логина
 * @author basharin
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class ChangeLoginRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "changeLoginRq";

	private String sid;
	private String login;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public ChangeLoginRequestData(String sid, String login)
	{
		this.sid = sid;
		this.login = login;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, SID_PARAM_NAME, sid));
		root.appendChild(createTag(request, LOGIN_PARAM_NAME, login));

		return request;
	}
}
