package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * «апрос на редактирование/разбивку операций
 *
 * @author Balovtsev
 * @since 15.05.2014
 */
@XmlRootElement(name = "message")
public class AlfEditOperationRequest extends Request
{
	private AlfEditOperationRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public AlfEditOperationRequestBody getBody()
	{
		return body;
	}

	public void setBody(AlfEditOperationRequestBody body)
	{
		this.body = body;
	}
}
