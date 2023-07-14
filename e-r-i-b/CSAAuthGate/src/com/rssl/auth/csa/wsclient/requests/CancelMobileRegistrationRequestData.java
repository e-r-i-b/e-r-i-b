package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос отмены регистрации мобильного приложения
 * @author Pankin
 * @ created 04.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class CancelMobileRegistrationRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "cancelMobileRegistrationRq";

	private String guid;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public CancelMobileRegistrationRequestData(String guid)
	{
		this.guid = guid;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.GUID_PARAM_NAME, guid));

		return request;
	}
}
