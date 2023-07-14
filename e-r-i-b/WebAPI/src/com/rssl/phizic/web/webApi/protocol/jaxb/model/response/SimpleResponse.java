package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Простой ответ не содержащий ничего кроме статуса
 *
 * @author Balovtsev
 * @since 23.04.14
 */
@XmlRootElement(name = "message")
public class SimpleResponse extends Response
{
	/**
	 */
	public SimpleResponse()
	{
		super();
	}

	/**
	 * @param status статус ответа
	 */
	public SimpleResponse(Status status)
	{
		super(status);
	}
}
