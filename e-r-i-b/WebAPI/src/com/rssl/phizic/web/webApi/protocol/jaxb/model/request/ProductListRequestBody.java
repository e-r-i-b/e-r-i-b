package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * “ело запроса на получение списка продуктов
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "body")
public class ProductListRequestBody extends SimpleRequestBody
{
	private List<String> productType;

	@XmlElement(name = "productType", required = true)

	public List<String> getProductType()
	{
		return productType;
	}

	public void setProductType(List<String> productType)
	{
		this.productType = productType;
	}
}


