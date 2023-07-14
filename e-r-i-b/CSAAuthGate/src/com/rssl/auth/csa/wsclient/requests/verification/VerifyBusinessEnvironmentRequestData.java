package com.rssl.auth.csa.wsclient.requests.verification;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на верификацию данных в деловой среде
 */

public class VerifyBusinessEnvironmentRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "verifyBusinessEnvironmentRq";

	private String ouid;

	/**
	 * конструктор
	 * @param ouid идентификатор операции
	 */
	public VerifyBusinessEnvironmentRequestData(String ouid)
	{
		this.ouid = ouid;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		root.appendChild(createTag(request, RequestConstants.OUID_PARAM_NAME, ouid));
		return request;
	}
}
