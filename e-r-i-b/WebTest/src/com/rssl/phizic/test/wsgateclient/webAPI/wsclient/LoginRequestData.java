package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос на аутентификацию пользователя
 * @author Jatsky
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class LoginRequestData extends RequestDataBase
{
	private String token;
	private String isAuthenticationCompleted;

	public LoginRequestData(String token, String isAuthenticationCompleted)
	{
		this.token = token;
		this.isAuthenticationCompleted = isAuthenticationCompleted;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.AUTH_TOKEN_TAG, token));
		root.appendChild(createTag(request, RequestConstants.IS_AUTHENTICATION_COMPLETED_TAG, isAuthenticationCompleted));
		return request;
	}
}

