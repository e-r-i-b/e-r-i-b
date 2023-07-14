package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Описывает сообщение запроса информации о содержании визуального интерфейса. (операция "menucontainer.content")
 *
 * @author Balovtsev
 * @since 24.04.14
 */
@XmlRootElement(name = "message")
public class MenuRequest extends Request
{
	private MenuRequestBody body;

	@Override
	@XmlElement(name="body", required = false)
	public MenuRequestBody getBody()
	{
		return body;
	}

	public void setBody(MenuRequestBody body)
	{
		this.body = body;
	}
}
