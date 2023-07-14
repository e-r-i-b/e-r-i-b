package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * «апрос на получение списка продуктов
 * @author Jatsky
 * @ created 20.11.13
 * @ $Author$
 * @ $Revision$
 */

public class GetProductListRequestData extends RequestDataBase
{
	private List<String> productTypes;

	public GetProductListRequestData(List<String> productTypes)
	{
		this.productTypes = productTypes;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		if (productTypes != null)
		{
			for (String productType : productTypes)
			{
				root.appendChild(createTag(request, RequestConstants.PRODUCT_TYPE_TAG, productType));
			}
		}
		return request;
	}
}
