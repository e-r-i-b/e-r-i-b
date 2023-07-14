package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * «апрос структуры расходов по категори€м
 * @author Jatsky
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "message")
public class ALFCategoryFilterRequest extends Request
{
	private ALFCategoryFilterRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public ALFCategoryFilterRequestBody getBody()
	{
		return body;
	}

	public void setBody(ALFCategoryFilterRequestBody body)
	{
		this.body = body;
	}
}
