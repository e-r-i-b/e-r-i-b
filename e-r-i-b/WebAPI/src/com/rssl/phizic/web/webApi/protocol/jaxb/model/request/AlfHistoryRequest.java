package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Запрос на получение истории операций АЛФ
 *
 * @author Balovtsev
 * @since 12.05.2014
 */
@XmlRootElement(name = "message")
public class AlfHistoryRequest extends Request
{
	private AlfHistoryRequestBody body;

	@Override
	@XmlElement(name = "body", required = true)
	public AlfHistoryRequestBody getBody()
	{
		return body;
	}

	public void setBody(AlfHistoryRequestBody body)
	{
		this.body = body;
	}
}
