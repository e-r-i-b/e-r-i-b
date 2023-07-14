package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запрос на работу с расходной категорией АЛФ
 * @author Jatsky
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "message")
public class ALFCategoryEditRequest extends Request
{
	private ALFCategoryEditRequestBody body;

	@XmlElement(name = "body", required = true)
	public ALFCategoryEditRequestBody getBody()
	{
		return body;
	}

	public void setBody(ALFCategoryEditRequestBody body)
	{
		this.body = body;
	}
}
