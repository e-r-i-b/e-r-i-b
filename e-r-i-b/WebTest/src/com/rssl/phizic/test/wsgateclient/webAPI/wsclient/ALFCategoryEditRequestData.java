package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос на работу с расходной категорией АЛФ
 * @author Jatsky
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ALFCategoryEditRequestData extends RequestDataBase
{
	private String id;
	private String name;
	private String operationType;

	public ALFCategoryEditRequestData(String id, String name, String operationType)
	{
		this.id = id;
		this.name = name;
		this.operationType = operationType;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.ID_TAG, id));
		root.appendChild(createTag(request, RequestConstants.NAME_TAG, name));
		root.appendChild(createTag(request, RequestConstants.OPERATION_TYPE_TAG, operationType));
		return request;
	}
}
