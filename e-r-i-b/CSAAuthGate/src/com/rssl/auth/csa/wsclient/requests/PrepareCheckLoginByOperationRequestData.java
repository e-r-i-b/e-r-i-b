package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.LOGIN_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.OUID_PARAM_NAME;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Данные запроса на предварительную проверку логина в разрезе операции
 * @author tisov
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */

public class PrepareCheckLoginByOperationRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "validateLoginRq";

	private String ouid;
	private String login;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public PrepareCheckLoginByOperationRequestData(String ouid, String login)
	{
		this.ouid = ouid;
		this.login = login;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, OUID_PARAM_NAME, ouid));
		root.appendChild(createTag(request, LOGIN_PARAM_NAME, login));

		return request;
	}
}
