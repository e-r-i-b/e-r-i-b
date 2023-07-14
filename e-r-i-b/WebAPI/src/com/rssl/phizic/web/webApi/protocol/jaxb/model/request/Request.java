package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import javax.xml.bind.annotation.XmlElement;

/**
 * ����� ������ ��� �������� �������
 *
 * @author Balovtsev
 * @since 24.04.14
 */
public abstract class Request
{
	private String                   ip;
	private RequestOperationConstant operation;

	/**
	 * @return IP-����� ������ ��������� �������
	 */
	@XmlElement(name = "ip", required = true)
	public String getIp()
	{
		return ip;
	}

	/**
	 * @return ��������
	 */
	@XmlElement(name = "operation", required = true)
	public RequestOperationConstant getOperation()
	{
		return operation;
	}

	/**
	 * @return ���� �������. �������� �������������� ��������� �������
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
