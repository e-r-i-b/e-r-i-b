package com.rssl.auth.csa.wsclient.requests.guest;

import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static com.rssl.auth.csa.wsclient.RequestConstants.OUID_PARAM_NAME;

/**
 * Данные для запроса на завершение процесса аутентификации гостя
 * @author niculichev
 * @ created 08.01.15
 * @ $Author$
 * @ $Revision$
 */
public class FinishCreateGuestSessionRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "finishCreateGuestSessionRq";

	private String ouid;

	public FinishCreateGuestSessionRequestData(String ouid)
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
		root.appendChild(createTag(request, OUID_PARAM_NAME, ouid));
		return request;
	}
}
