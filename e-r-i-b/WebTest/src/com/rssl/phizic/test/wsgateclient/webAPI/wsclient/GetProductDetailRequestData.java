package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос на получение детальной информации по продукту
 * @author Jatsky
 * @ created 28.11.13
 * @ $Author$
 * @ $Revision$
 */

public class GetProductDetailRequestData extends RequestDataBase
{
	private String id;

	public GetProductDetailRequestData(String id)
	{
		this.id = id;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.ID_TAG, id));
		return request;
	}
}