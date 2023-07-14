package com.rssl.phizic.business.loanCardOffer;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author gulov
 * @ created 05.09.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Класс-сущность который содержит продукт, завку и условие по предложению на кредитную карту
 */
public class ConditionProductByOffer
{
	private Long conditionId;
	private Long productId;
	private OfferId offerId;
	private BigDecimal gracePeriodInterestRate;
	private Integer gracePeriodDuration;
	private Boolean allowGracePeriod;
	private String terms;
	private BigDecimal interestRate;
	private BigDecimal offerInterestRate;
	private Money firstYearPayment;
	private Money nextYearPayment;
	private LoanCardOfferType offerType;
	private Money maxLimit;
	private String seriesAndNumber;
	private Long idWay;
	private Long terbank;
	private Long osb;
	private Long vsp;
	private String   firstName;
    private String   surName;
    private String   patrName;
    private Calendar birthDay;
	private String name;
	private Integer cardTypeCode;

	public Long getConditionId()
	{
		return conditionId;
	}

	public void setConditionId(Long conditionId)
	{
		this.conditionId = conditionId;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public BigDecimal getInterestRate()
	{
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate)
	{
		this.interestRate = interestRate;
	}

	public Money getFirstYearPayment()
	{
		return firstYearPayment;
	}

	public void setFirstYearPayment(Money firstYearPayment)
	{
		this.firstYearPayment = firstYearPayment;
	}

	public Money getNextYearPayment()
	{
		return nextYearPayment;
	}

	public void setNextYearPayment(Money nextYearPayment)
	{
		this.nextYearPayment = nextYearPayment;
	}

	public OfferId getOfferId()
	{
		return offerId;
	}

	public void setOfferId(OfferId offerId)
	{
		this.offerId = offerId;
	}

	public LoanCardOfferType getOfferType()
	{
		return offerType;
	}

	public void setOfferType(LoanCardOfferType offerType)
	{
		this.offerType = offerType;
	}

	public Money getMaxLimit()
	{
		return maxLimit;
	}

	public void setMaxLimit(Money maxLimit)
	{
		this.maxLimit = maxLimit;
	}

	public String getSeriesAndNumber()
	{
		return seriesAndNumber;
	}

	public void setSeriesAndNumber(String seriesAndNumber)
	{
		this.seriesAndNumber = seriesAndNumber;
	}

	public Long getIdWay()
	{
		return idWay;
	}

	public void setIdWay(Long idWay)
	{
		this.idWay = idWay;
	}

	public Long getTerbank()
	{
		return terbank;
	}

	public void setTerbank(Long terbank)
	{
		this.terbank = terbank;
	}

	public Long getOsb()
	{
		return osb;
	}

	public void setOsb(Long osb)
	{
		this.osb = osb;
	}

	public Long getVsp()
	{
		return vsp;
	}

	public void setVsp(Long vsp)
	{
		this.vsp = vsp;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
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

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public Integer getCardTypeCode()
	{
		return cardTypeCode;
	}

	public void setCardTypeCode(Integer cardTypeCode)
	{
		this.cardTypeCode = cardTypeCode;
	}

	public BigDecimal getGracePeriodInterestRate()
	{
		return gracePeriodInterestRate;
	}

	public void setGracePeriodInterestRate(BigDecimal gracePeriodInterestRate)
	{
		this.gracePeriodInterestRate = gracePeriodInterestRate;
	}

	public Integer getGracePeriodDuration()
	{
		return gracePeriodDuration;
	}

	public void setGracePeriodDuration(Integer gracePeriodDuration)
	{
		this.gracePeriodDuration = gracePeriodDuration;
	}

	public String getTerms()
	{
		return terms;
	}

	public void setTerms(String terms)
	{
		this.terms = terms;
	}

	public Boolean getAllowGracePeriod()
	{
		return allowGracePeriod;
	}

	public void setAllowGracePeriod(Boolean allowGracePeriod)
	{
		this.allowGracePeriod = allowGracePeriod;
	}

	public BigDecimal getOfferInterestRate()
	{
		return offerInterestRate;
	}

	public void setOfferInterestRate(BigDecimal offerInterestRate)
	{
		this.offerInterestRate = offerInterestRate;
	}
}
