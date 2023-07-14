package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Базовый класс описания объектов для десериализации запросов
 *
 * @author Balovtsev
 * @since 23.04.14
 */
@XmlTransient
public abstract class Response
{
	private Status status;

	/**
	 */
	Response()
	{
		status = new Status();
	}

	/**
	 * @param status статус ответа
	 */
	Response(Status status)
	{
		this.status = status;
	}

	/**
	 * @return Статус ответа
	 */
	@XmlElement(name = "status", required = true)
	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}
}
