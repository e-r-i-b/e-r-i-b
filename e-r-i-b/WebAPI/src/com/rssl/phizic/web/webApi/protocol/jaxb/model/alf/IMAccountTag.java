package com.rssl.phizic.web.webApi.protocol.jaxb.model.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Balovtsev
 * @since 16.05.2014
 */
@XmlType(name = "imaccount", propOrder = {"id", "rurbalance", "name", "number", "balance", "url"})
public class IMAccountTag
{
	private Long id;
	private String rurbalance;
	private String name;
	private String number;
	private MoneyTag balance;
	private String url;

	/**
	 */
	public IMAccountTag()
	{
	}

	/**
	 *
	 * @param id Идентификатор ОМС
	 * @param rurbalance Сумма остатка в рублях
	 * @param name Наименование ОМС
	 * @param number Номер счета
	 */
	public IMAccountTag(Long id, String rurbalance, String name, String number)
	{
		this.id = id;
		this.rurbalance = rurbalance;
		this.name = name;
		this.number = number;
	}

	/**
	 * Обязательный элемент
	 * @return Идентификатор ОМС
	 */
	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	/**
	 * Обязательный элемент
	 * @return Сумма остатка в рублях
	 */
	@XmlElement(name = "rurbalance", required = true)
	public String getRurbalance()
	{
		return rurbalance;
	}

	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "name", required = true)
	public String getName()
	{
		return name;
	}

	@XmlElement(name = "number", required = false)
	public String getNumber()
	{
		return number;
	}

	@XmlElement(name = "balance", required = true)
	public MoneyTag getBalance()
	{
		return balance;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setRurbalance(String rurbalance)
	{
		this.rurbalance = rurbalance;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public void setBalance(MoneyTag balance)
	{
		this.balance = balance;
	}

	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "url", required = true)
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
