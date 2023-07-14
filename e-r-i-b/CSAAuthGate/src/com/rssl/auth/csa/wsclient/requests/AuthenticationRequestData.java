package com.rssl.auth.csa.wsclient.requests;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static com.rssl.auth.csa.wsclient.RequestConstants.LOGIN_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.PASSWORD_PARAM_NAME;

/**
 * ƒанные дл€ запроса на аутентификацию клиента
 *
 * @author akrenev
 * @ created 04.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class AuthenticationRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "authenticationRq";

	private String login;
	private String password;

	public String getName()
	{
		return REQUEST_NAME;
	}

	/**
	 * конструктор
	 * @param login логин клиента
	 * @param password пароль клиента
	 */
	public AuthenticationRequestData(String login, String password)
	{
		this.login = login;
		this.password = password;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, LOGIN_PARAM_NAME, login));
		root.appendChild(createTag(request, PASSWORD_PARAM_NAME, password));

		return request;
	}
}
