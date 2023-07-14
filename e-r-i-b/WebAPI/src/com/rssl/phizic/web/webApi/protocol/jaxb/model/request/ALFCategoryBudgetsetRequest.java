package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запрос на установку бюджета для расходной категории
 * @author Jatsky
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "message")
public class ALFCategoryBudgetsetRequest extends Request
{
	private ALFCategoryBudgetsetRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public ALFCategoryBudgetsetRequestBody getBody()
	{
		return body;
	}

	public void setBody(ALFCategoryBudgetsetRequestBody body)
	{
		this.body = body;
	}
}
