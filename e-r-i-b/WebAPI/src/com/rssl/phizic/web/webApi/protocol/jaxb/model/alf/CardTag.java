package com.rssl.phizic.web.webApi.protocol.jaxb.model.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.CardTypeWebAPI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Balovtsev
 * @since 16.05.2014
 */
@XmlType(name = "card", propOrder = {"id", "rurbalance", "type", "name", "number", "availableLimit", "url"})
public class CardTag
{
	private Long id;
	private String rurbalance;
	private CardTypeWebAPI type;
	private String name;
	private String number;
	private MoneyTag availableLimit;
	private String url;

	/**
	 */
	public CardTag()
	{
	}

	/**
	 *
	 * @param id Идентификатор карты
	 * @param rurbalance Сумма остатка в рублях
	 * @param type Тип карты (дебетовая, кредитная, овердрафтная)
	 * @param name Имя (алиас) карты
	 * @param number Маскированный номер карты
	 */
	public CardTag(Long id, String rurbalance, CardTypeWebAPI type, String name, String number)
	{
		this.id = id;
		this.rurbalance = rurbalance;
		this.type = type;
		this.name = name;
		this.number = number;
	}

	/**
	 * Обязательный элемент
	 * @return Идентификатор карты
	 */
	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	/**
	 * Обязательный элемент
	 * @return Сумма остатка карты в рублях
	 */
	@XmlElement(name = "rurbalance", required = true)
	public String getRurbalance()
	{
		return rurbalance;
	}

	@XmlElement(name = "type", required = true)
	public CardTypeWebAPI getType()
	{
		return type;
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

	@XmlElement(name = "availableLimit", required = false)
	public MoneyTag getAvailableLimit()
	{
		return availableLimit;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setRurbalance(String rurbalance)
	{
		this.rurbalance = rurbalance;
	}

	public void setType(CardTypeWebAPI type)
	{
		this.type = type;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public void setAvailableLimit(MoneyTag availableLimit)
	{
		this.availableLimit = availableLimit;
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
