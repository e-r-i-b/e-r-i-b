package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запрос на получение информации для диаграммы доступных средств
 *
 * @author Balovtsev
 * @since 16.05.2014
 */
@XmlRootElement(name = "message")
public class AlfGraphicDataRequest extends Request
{
	private AlfGraphicDataRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public AlfGraphicDataRequestBody getBody()
	{
		return body;
	}

	public void setBody(AlfGraphicDataRequestBody body)
	{
		this.body = body;
	}
}
