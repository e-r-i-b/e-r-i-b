package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запрос на получение справочника категорий АЛФ
 * @author Jatsky
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "message")
public class ALFCategoryListRequest extends Request
{
	private ALFCategoryListRequestBody body;

	@XmlElement(name = "body", required = true)
	public ALFCategoryListRequestBody getBody()
	{
		return body;
	}

	public void setBody(ALFCategoryListRequestBody body)
	{
		this.body = body;
	}
}
