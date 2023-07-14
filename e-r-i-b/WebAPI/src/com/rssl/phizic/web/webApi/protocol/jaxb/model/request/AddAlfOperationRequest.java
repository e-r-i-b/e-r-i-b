package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запрос на добавление операции АЛФ
 *
 * @author Balovtsev
 * @since 14.05.2014
 */
@XmlRootElement(name = "message")
public class AddAlfOperationRequest extends Request
{
	private AddAlfOperationRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public AddAlfOperationRequestBody getBody()
	{
		return body;
	}

	public void setBody(AddAlfOperationRequestBody body)
	{
		this.body = body;
	}
}
