package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс описывает простой запрос, не содержащий дополнительных параметров
 *
 * @author Balovtsev
 * @since 24.04.14
 */
@XmlRootElement(name = "message")
public class SimpleRequest extends Request
{
	private SimpleRequestBody body;

	public void setBody(SimpleRequestBody body)
	{
		this.body = body;
	}

	@Override
	@XmlElement(name="body", required = false)
	public SimpleRequestBody getBody()
	{
		return body;
	}
}
