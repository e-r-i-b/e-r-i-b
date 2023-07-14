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
@XmlType(name = "account", propOrder = {"id", "rurbalance", "maxSumWrite", "name", "number", "balance", "url"})
public class AccountTag
{
	private Long id;
	private String rurbalance;
	private String maxSumWrite;
	private String name;
	private String number;
	private MoneyTag balance;
	private String url;

	/**
	 */
	public AccountTag()
	{
	}

	/**
	 *
	 * @param id Идентификатор счета\вклада
	 * @param rurbalance Сумма остатка вклада в рублях
	 * @param name Название
	 * @param number Номер вклада
	 */
	public AccountTag(Long id, String rurbalance, String name, String number)
	{
		this.id = id;
		this.rurbalance = rurbalance;
		this.name = name;
		this.number = number;
	}

	/**
	 * Обязательный элемент
	 * @return Идентификатор счета\вклада
	 */
	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	/**
	 * Обязательный элемент
	 * @return Сумма остатка вклада в рублях
	 */
	@XmlElement(name = "rurbalance", required = true)
	public String getRurbalance()
	{
		return rurbalance;
	}

	/**
	 * @return Максимальная сумма снятия в рублях
	 * (не возвращается, если сумма снятия не ограничена)
	 */
	@XmlElement(name = "maxSumWrite", required = false)
	public String getMaxSumWrite()
	{
		return maxSumWrite;
	}

	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "name", required = true)
	public String getName()
	{
		return name;
	}

	@XmlElement(name = "number", required = true)
	public String getNumber()
	{
		return number;
	}

	@XmlElement(name = "balance", required = false)
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

	public void setMaxSumWrite(String maxSumWrite)
	{
		this.maxSumWrite = maxSumWrite;
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
