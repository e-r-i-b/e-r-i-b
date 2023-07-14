package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.PASSWORD_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.SID_PARAM_NAME;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * запрос на смену пароля.
 *
 * @author bogdanov
 * @ created 12.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class ChangePasswordRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "changePasswordRq";

	private String sid;
	private String password;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public ChangePasswordRequestData(String sid, String password)
	{
		this.sid = sid;
		this.password = password;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, SID_PARAM_NAME, sid));
		root.appendChild(createTag(request, PASSWORD_PARAM_NAME, password));

		return request;
	}
}
