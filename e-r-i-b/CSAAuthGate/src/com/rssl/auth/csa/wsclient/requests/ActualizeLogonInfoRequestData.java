package com.rssl.auth.csa.wsclient.requests;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.auth.csa.wsclient.RequestConstants;

/**
 * Данные для запроса на актуализацию информации о входе
 * @author niculichev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ActualizeLogonInfoRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "actualizeLogonInfoRq";
	private String ouid;
	private String guid;

	public ActualizeLogonInfoRequestData(String ouid, String guid)
	{
		this.ouid = ouid;
		this.guid = guid;
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
		root.appendChild(createTag(request, RequestConstants.GUID_PARAM_NAME, guid));

		return request;
	}
}
