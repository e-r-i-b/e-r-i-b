package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.PASSWORD_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.SID_PARAM_NAME;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Данные запроса на валидацию нового пароля в рамках сессии
 * @author niculichev
 * @ created 27.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ValidatePasswordRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "validatePasswordRq";
	private String sid;
	private String password;

	public ValidatePasswordRequestData(String sid, String password)
	{
		this.sid = sid;
		this.password = password;
	}

	public String getName()
	{
		return REQUEST_NAME;
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
