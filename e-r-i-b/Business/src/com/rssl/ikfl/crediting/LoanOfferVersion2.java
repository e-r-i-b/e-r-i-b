package com.rssl.ikfl.crediting;

import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.offers.OfferType;
import com.rssl.phizic.common.types.Money;

import java.util.*;

/**
 * @author Rtischeva
 * @ created 20.01.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanOfferVersion2 implements LoanOffer
{
	/**
	 * Идентификатор предложения
	 */
	private OfferId offerId;

	/**
	 * Имя
	 */
	private String   firstName;
	/**
	 * фамилия
	 */
	private String   surName;
	/**
	 * Отчество
	 */
	private String   patrName;
	/**
	 * Дата рождения
	 */
	private Calendar birthDay;

	/**
	 * Номер паспорта
	 */
	private String passportNumber;

	/**
	 * Серия паспорта
	 */
	private String passportSeries;

	/**
	 * Условия по предложению
	 */
	private List<OfferCondition> offerConditions;

	/**
	 * Код предложения [1]
	 */
	private String sourceCode;

	/**
	 * Наименование предложения [1]
	 */
	private String sourceName;

	/**
	 * Наименование продукта АП [1]
	 */
	private String productASName;

	/**
	 * Код типа продукта TSM [0-1]
	 */
	private String productTypeCode;

	/**
	 * Код продукта TSM [0-1]
	 */
	private String productCode;

	/**
	 * Код субпродукта TSM [0-1]
	 */
	private String productSubCode;

	/**
	 * Номер тербанка
	 */
	private Long terbank;

	/**
	 * Приоритет предложения [1]
	 */
	private int priority;

	/**
	 * Текст персонализированного сообщения для клиента [0-1]
	 */
	private String personalText;

	/**
	 * Дата окончания действия предложения [1]
	 */
	private Calendar expDate;

	/**
	 * Максимальная сумма [0-1]
	 */
	private Money limitMax;

	/**
	 * Процентрная ставка
	 */
	private Double percentRate;

	/**
	 *  Срок кредита
	 */
	private Long duration;

	/**
	 * код валюты
	 */
	private String currencyCode;

	/**
	 * Дата загрузки предложения [1]
	 */
	private Calendar loadDate;

	/**
	 * Идентификатор участника компании
 	 */
	private String campaignMemberId;

	private Set<OfferTopUp> topUps = new HashSet<OfferTopUp>();

	public LoanOfferVersion2(Long id)
	{
		this.offerId = new OfferId(id, OfferType.OFFER_VERSION_2);
	}

	public OfferId getOfferId()
	{
		return offerId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
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

	public String getPasportNumber()
	{
		return passportNumber;
	}

	public void setPasportNumber(String pasportNumber)
	{
		this.passportNumber = pasportNumber;
	}

	public String getPasportSeries()
	{
		return passportSeries;
	}

	public void setPasportSeries(String pasportSeries)
	{
		this.passportSeries = pasportSeries;
	}

	public Money getMaxLimit()
	{
		return limitMax;
	}

	public void setMaxLimit(Money maxLimit)
	{
		this.limitMax = maxLimit;
	}

	public Long getTerbank()
	{
		return terbank;
	}

	public void setTerbank(Long terbank)
	{
		this.terbank = terbank;
	}

	public Calendar getEndDate()
	{
		return expDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.expDate = endDate;
	}

	public Long getDuration()
	{
		return duration;
	}

	public void setDuration(Long duration)
	{
	   this.duration = duration;
	}

	public String getProductName()
	{
		return productASName;
	}

	public void setProductName(String productName)
	{
		this.productASName = productName;
	}

	public Double getPercentRate()
	{
		return percentRate;
	}

	public void setPercentRate(Double percentRate)
	{
		this.percentRate = percentRate;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public String getSubProductCode()
	{
		return productSubCode;
	}

	public void setSubProductCode(String subProductCode)
	{
		this.productSubCode = subProductCode;
	}

	public String getProductTypeCode()
	{
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode)
	{
		this.productTypeCode = productTypeCode;
	}

	public List<OfferCondition> getConditions()
	{
		return offerConditions;
	}

	public void setOfferConditions(List<OfferCondition> offerConditions)
	{
		this.offerConditions = offerConditions;
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

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public Set<OfferTopUp> getTopUps()
	{
		return Collections.unmodifiableSet(topUps);
	}

	public void setTopUps(Set<OfferTopUp> topUps)
	{
		this.topUps.addAll(topUps);
	}

	public String getCampaignMemberId()
	{
		return campaignMemberId;
	}

	public void setCampaignMemberId(String campaignMemberId)
	{
		this.campaignMemberId = campaignMemberId;
	}
}
