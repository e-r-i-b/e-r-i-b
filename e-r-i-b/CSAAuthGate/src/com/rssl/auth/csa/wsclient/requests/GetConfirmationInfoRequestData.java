package com.rssl.auth.csa.wsclient.requests;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.auth.csa.wsclient.RequestConstants;

/**
 * @author akrenev
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на получение данных о способах подтверждения операций клиентом
 */

public class GetConfirmationInfoRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "getConfirmationInfoRq";

	private final String authToken;

	/**
	 * конструктор
	 * @param authToken токен аутнтификации
	 */
	public GetConfirmationInfoRequestData(String authToken)
	{
		this.authToken = authToken;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.AUTH_TOKEN_TAG, authToken));

		return request;
	}
}
