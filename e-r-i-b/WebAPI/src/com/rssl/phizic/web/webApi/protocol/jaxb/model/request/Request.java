package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;

/**
 *  ласс служит дл€ описани€ запроса
 *
 * @author Balovtsev
 * @since 24.04.14
 */
public abstract class Request
{
	private String                   ip;
	private RequestOperationConstant operation;

	/**
	 * @return IP-адрес машины конечного клиента
	 */
	@XmlElement(name = "ip", required = true)
	public String getIp()
	{
		return ip;
	}

	/**
	 * @return операци€
	 */
	@XmlElement(name = "operation", required = true)
	public RequestOperationConstant getOperation()
	{
		return operation;
	}

	/**
	 * @return “ело запроса. —одержит дополнительные параметры запроса
	 */
	public abstract SimpleRequestBody getBody();

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public void setOperation(RequestOperationConstant operation)
	{
		this.operation = operation;
	}
}
