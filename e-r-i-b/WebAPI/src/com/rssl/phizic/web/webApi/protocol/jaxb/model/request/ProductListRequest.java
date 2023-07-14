package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * «апрос на получение списка продуктов
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "message")
public class ProductListRequest extends Request
{
	private ProductListRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public ProductListRequestBody getBody()
	{
		return body;
	}

	public void setBody(ProductListRequestBody body)
	{
		this.body = body;
	}
}

