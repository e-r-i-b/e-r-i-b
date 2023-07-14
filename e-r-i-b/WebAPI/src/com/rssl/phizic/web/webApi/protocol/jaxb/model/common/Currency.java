package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Balovtsev
 * @since 12.05.2014
 */
@XmlType(propOrder = {"code", "name"})
public class Currency
{
	private String code;
	private String name;

	/**
	 *
	 */
	public Currency()
	{
	}

	/**
	 *
	 * @param code ��������� ��� ������ ISO
	 * @param name ������ ������. ��� ����������� ������������.
	 */
	public Currency(String code, String name)
	{
		this.code = code;
		this.name = name;
	}

	/**
	 * @param currency com.rssl.phizic.common.types.Currency
	 */
	public Currency(com.rssl.phizic.common.types.Currency currency)
	{
		this.code = currency.getCode();
		this.name = currency.getName();
	}

	/**
	 * ������������ �������
	 * @return ��������� ��� ������ ISO
	 */
	@XmlElement(name = "code", required = true)
	public String getCode()
	{
		return code;
	}

	/**
	 * ������������ �������
	 * @return ������ ������. ��� ����������� ������������.
	 */
	@XmlElement(name = "name", required = true)
	public String getName()
	{
		return name;
	}
}
