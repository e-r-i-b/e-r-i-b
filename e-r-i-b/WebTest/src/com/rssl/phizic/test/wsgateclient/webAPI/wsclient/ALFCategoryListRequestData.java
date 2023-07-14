package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос на получение справочника категорий АЛФ
 * @author Jatsky
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ALFCategoryListRequestData extends RequestDataBase
{
	private String incomeType;
	private String paginationSize;
	private String paginationOffset;

	public ALFCategoryListRequestData(String incomeType, String paginationSize, String paginationOffset)
	{
		this.incomeType = incomeType;
		this.paginationSize = paginationSize;
		this.paginationOffset = paginationOffset;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.INCOME_TYPE_TAG, incomeType));
		root.appendChild(createTag(request, RequestConstants.PAGINATION_SIZE_TAG, paginationSize));
		root.appendChild(createTag(request, RequestConstants.PAGINATION_OFFSET_TAG, paginationOffset));
		return request;
	}
}
