package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запрос выписки по продукту
 * @author Jatsky
 * @ created 07.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "message")
public class ProductAbstractRequest extends Request
{
	private ProductAbstractRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public ProductAbstractRequestBody getBody()
	{
		return body;
	}

	public void setBody(ProductAbstractRequestBody body)
	{
		this.body = body;
	}
}
