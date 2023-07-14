package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * «апрос на получение выписки по продутку
 * @author Jatsky
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */

public class GetProductAbstractRequestData extends RequestDataBase
{
	private String id;
	private String from;
	private String to;
	private String count;

	public GetProductAbstractRequestData(String id, String from, String to, String count)
	{
		this.id = id;
		this.from = from;
		this.to = to;
		this.count = count;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.ID_TAG, id));
		root.appendChild(createTag(request, RequestConstants.FROM_TAG, from));
		root.appendChild(createTag(request, RequestConstants.TO_TAG, to));
		root.appendChild(createTag(request, RequestConstants.COUNT_TAG, count));
		return request;
	}
}
