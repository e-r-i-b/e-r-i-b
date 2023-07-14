package com.rssl.phizic.web.webApi.protocol.jaxb.model.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.UrlXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.XmlCalendarAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Money;

import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Balovtsev
 * @since 12.05.2014
 */
@XmlType(propOrder = {"id", "date", "comment", "categoryId", "categoryName", "cardNumber", "cardAmount", "nationalAmount", "categories", "detailsLink"})
public class AlfOperation
{
	private Long           id;
	private Long           categoryId;
	private Money          cardAmount;
	private Money          nationalAmount;
	private String         comment;
	private String         cardNumber;
	private String         categoryName;
	private Calendar       date;
	private List<Category> categories;
	private String         detailsLink;

	/**
	 * Обязательный элемент
	 * @return Идентификатор операции
	 */
	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	/**
	 * Обязательный элемент
	 * @return Идентификатор категории операции
	 */
	@XmlElement(name = "categoryId", required = true)
	public Long getCategoryId()
	{
		return categoryId;
	}

	/**
	 * Обязательный элемент
	 * @return Сумма в валюте карты. Пустое для трат наличными
	 */
	@XmlElement(name = "cardAmount", required = true)
	public Money getCardAmount()
	{
		return cardAmount;
	}

	/**
	 * Обязательный элемент
	 * @return Сумма в рублях
	 */
	@XmlElement(name = "nationalAmount", required = true)
	public Money getNationalAmount()
	{
		return nationalAmount;
	}

	/**
	 * Обязательный элемент
	 * @return Комментарий
	 */
	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "comment", required = true)
	public String getComment()
	{
		return comment;
	}

	/**
	 * Обязательный элемент
	 * @return Маскированный номер карты. Для трат наличными выводится "Наличные"
	 */
	@XmlElement(name = "cardNumber", required = true)
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * Обязательный элемент
	 * @return Название категории операции
	 */
	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "categoryName", required = true)
	public String getCategoryName()
	{
		return categoryName;
	}

	/**
	 * Обязательный элемент
	 * @return Дата
	 */
	@XmlJavaTypeAdapter(XmlCalendarAdapter.class)
	@XmlElement(name = "date", required = true)
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * Обязательный элемент
	 * @return
	 */
	@XmlElementWrapper(name = "availableCategories", required = true)
	@XmlElement(name = "category", required = true)
	public List<Category> getCategories()
	{
		return categories;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public void setCategoryId(Long categoryId)
	{
		this.categoryId = categoryId;
	}

	public void setCardAmount(Money cardAmount)
	{
		this.cardAmount = cardAmount;
	}

	public void setNationalAmount(Money nationalAmount)
	{
		this.nationalAmount = nationalAmount;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public void setCategories(List<Category> categories)
	{
		this.categories = categories;
	}

	@XmlJavaTypeAdapter(UrlXmlAdapter.class)
	@XmlElement(name = "detailsLink", required = false)
	public String getDetailsLink()
	{
		return detailsLink;
	}

	public void setDetailsLink(String detailsLink)
	{
		this.detailsLink = detailsLink;
	}
}
