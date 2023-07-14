package com.rssl.ikfl.crediting;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */
@PlainOldJavaObject
@SuppressWarnings("PackageVisibleField")
public class Offer
{
	/**
	 * Идентификатор предложения [PK]
	 */
	long id;

	/**
	 * Фамилия
	 */
	String surName;

	/**
	 * Имя
	 */
	String firstName;

	/**
	 * Отчество
	 */
	String patrName;

	/**
	 * День рождения
	 */
	Calendar birthDay;

	/**
	 * Серия документа
	 */
	String docSeries;

	/**
	 * Номер документа
	 */
	String docNumber;

	/**
	 * Идентификатор кампании АП [1]
	 */
	String campaignId;

	/**
	 * Наименование кампании АП [0-1]
	 */
	String campaignName;

	/**
	 * Вид продукта [1]
	 */
	OfferProductType productType;

	/**
	 * Код предложения [1]
	 */
	String sourceCode;

	/**
	 * Наименование предложения [1]
	 */
	String sourceName;

	/**
	 * Наименование продукта АП [1]
	 */
	String productASName;

	/**
	 * Код типа продукта TSM [0-1]
	 */
	String productTypeCode;

	/**
	 * Код продукта TSM [0-1]
	 */
	String productCode;

	/**
	 * Код субпродукта TSM [0-1]
	 */
	String productSubCode;

	/**
	 * Код участника [1]
	 */
	String campaignMemberId;

	/**
	 * Идентификатор клиента [1]
	 */
	String clientId;

	/**
	 *
	 */
	String tb;

	/**
	 * Приоритет предложения [1]
	 */
	int priority;

	/**
	 * Текст персонализированного сообщения для клиента [0-1]
	 */
	String personalText;

	/**
	 * Дата окончания действия предложения [1]
	 */
	Calendar expDate;

	/**
	 * Символьный код валюты (RUR, EUR, USD) [1]
	 */
	String currencyCode;

	/**
	 * Минимальная процентная ставка [0-1]
	 */
	BigDecimal rateMin;

	/**
	 * Максимальная процентная ставка [0-1]
	 */
	BigDecimal rateMax;

	/**
	 * Минимальная сумма [0-1]
	 */
	BigDecimal limitMin;

	/**
	 * Максимальная сумма [0-1]
	 */
	BigDecimal limitMax;

	/**
	 * Минимальный период (мес) [0-1]
	 */
	Integer periodMin;

	/**
	 * Максимальный период (мес) [0-1]
	 */
	Integer periodMax;

	/**
	 * Дата загрузки предложения [1]
	 */
	Calendar loadDate;

	/**
	 * По предложению оформлена заявка
	 */
	boolean offerUsed;

	/**
	 * Предложение актуально
	 */
	boolean isActive;

	Set<OfferTopUp> topUps = new HashSet<OfferTopUp>();

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getDocSeries()
	{
		return docSeries;
	}

	public void setDocSeries(String docSeries)
	{
		this.docSeries = docSeries;
	}

	public String getDocNumber()
	{
		return docNumber;
	}

	public void setDocNumber(String docNumber)
	{
		this.docNumber = docNumber;
	}

	public String getCampaignId()
	{
		return campaignId;
	}

	public void setCampaignId(String campaignId)
	{
		this.campaignId = campaignId;
	}

	public String getCampaignName()
	{
		return campaignName;
	}

	public void setCampaignName(String campaignName)
	{
		this.campaignName = campaignName;
	}

	public OfferProductType getProductType()
	{
		return productType;
	}

	public void setProductType(OfferProductType productType)
	{
		this.productType = productType;
	}

	public String getSourceCode()
	{
		return sourceCode;
	}

	public void setSourceCode(String sourceCode)
	{
		this.sourceCode = sourceCode;
	}

	public String getSourceName()
	{
		return sourceName;
	}

	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}

	public String getProductASName()
	{
		return productASName;
	}

	public void setProductASName(String productASName)
	{
		this.productASName = productASName;
	}

	public String getProductTypeCode()
	{
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode)
	{
		this.productTypeCode = productTypeCode;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public String getProductSubCode()
	{
		return productSubCode;
	}

	public void setProductSubCode(String productSubCode)
	{
		this.productSubCode = productSubCode;
	}

	public String getCampaignMemberId()
	{
		return campaignMemberId;
	}

	public void setCampaignMemberId(String campaignMemberId)
	{
		this.campaignMemberId = campaignMemberId;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public String getPersonalText()
	{
		return personalText;
	}

	public void setPersonalText(String personalText)
	{
		this.personalText = personalText;
	}

	public Calendar getExpDate()
	{
		return expDate;
	}

	public void setExpDate(Calendar expDate)
	{
		this.expDate = expDate;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public BigDecimal getRateMin()
	{
		return rateMin;
	}

	public void setRateMin(BigDecimal rateMin)
	{
		this.rateMin = rateMin;
	}

	public BigDecimal getRateMax()
	{
		return rateMax;
	}

	public void setRateMax(BigDecimal rateMax)
	{
		this.rateMax = rateMax;
	}

	public BigDecimal getLimitMin()
	{
		return limitMin;
	}

	public void setLimitMin(BigDecimal limitMin)
	{
		this.limitMin = limitMin;
	}

	public BigDecimal getLimitMax()
	{
		return limitMax;
	}

	public void setLimitMax(BigDecimal limitMax)
	{
		this.limitMax = limitMax;
	}

	public Integer getPeriodMin()
	{
		return periodMin;
	}

	public void setPeriodMin(Integer periodMin)
	{
		this.periodMin = periodMin;
	}

	public Integer getPeriodMax()
	{
		return periodMax;
	}

	public void setPeriodMax(Integer periodMax)
	{
		this.periodMax = periodMax;
	}

	public Calendar getLoadDate()
	{
		return loadDate;
	}

	public void setLoadDate(Calendar loadDate)
	{
		this.loadDate = loadDate;
	}

	public boolean isOfferUsed()
	{
		return offerUsed;
	}

	public void setOfferUsed(boolean offerUsed)
	{
		this.offerUsed = offerUsed;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean active)
	{
		isActive = active;
	}

	public Set<OfferTopUp> getTopUps()
	{
		return topUps;
	}

	public void setTopUps(Set<OfferTopUp> topUps)
	{
		this.topUps = topUps;
	}
}
