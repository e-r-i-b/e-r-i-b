package com.rssl.auth.csa.wsclient.requests;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.rssl.auth.csa.wsclient.RequestConstants.OUID_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.PASSWORD_PARAM_NAME;

/**
 * Данные запроса на предварительную проверку пароля в разрезе операции
 * @author niculichev
 * @ created 09.02.14
 * @ $Author$
 * @ $Revision$
 */
public class PrepareCheckPasswordByOperationRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "validatePasswordRq";

	private String ouid;
	private String password;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public PrepareCheckPasswordByOperationRequestData(String ouid, String password)
	{
		this.ouid = ouid;
		this.password = password;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, OUID_PARAM_NAME, ouid));
		root.appendChild(createTag(request, PASSWORD_PARAM_NAME, password));

		return request;
	}
}
