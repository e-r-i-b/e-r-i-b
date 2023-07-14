package com.rssl.phizic.business.creditcards.products;


import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Карточный кредитный продукт + Условие в разрезе валюты для Карточного кредитного продукта
 * @author komarov
 * @ created 27.05.2015
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("UnusedDeclaration")
public class PartCreditCardProduct
{

	private Long id;
	private BigDecimal minCreditLimit;      //мин. кредитный лимит
	private Currency minCreditLimitCurrency;      //мин. кредитный лимит

	private BigDecimal maxCreditLimit;      //макс. кредитный лимит
	private Currency maxCreditLimitCurrency;      //мин. кредитный лимит

	private Boolean isMaxCreditLimitInclude;    //"включительно" для макс. кредитного лимита
	private BigDecimal offerInterestRate;       //%  ставка  по предодобренной карте

	private BigDecimal firstYearPayment;             //плата за годовое обслуживание: первый год
	private Currency   firstYearPaymentCurrency;             //плата за годовое обслуживание: первый год
	private BigDecimal nextYearPayment;              //плата за годовое обслуживание: последующие годы
	private Currency   nextYearPaymentCurrency;              //плата за годовое обслуживание: последующие годы

	private Calendar changeDate;
	private String name;                        //наименование
	private Boolean allowGracePeriod;           //есть льготный период
	private Integer gracePeriodDuration;        //льготный период до
	private BigDecimal gracePeriodInterestRate; //% ставка в льготный период
	private String additionalTerms;             //доп. условия

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return кредитный лимит
	 */
	public Money getMinCreditLimit()
	{
		if(minCreditLimit == null || minCreditLimitCurrency == null)
			return new Money();
		return new Money(minCreditLimit, minCreditLimitCurrency);
	}

	/**
	 * @param minCreditLimit кредитный лимит
	 */
	public void setMinCreditLimit(Money minCreditLimit)
	{

	}

	/**
	 * @return кредитный лимит
	 */
	public Money getMaxCreditLimit()
	{
		if(maxCreditLimit == null || maxCreditLimitCurrency == null)
			return new Money();
		return new Money(maxCreditLimit, maxCreditLimitCurrency);
	}

	/**
	 * @param maxCreditLimit кредитный лимит
	 */
	public void setMaxCreditLimit(Money maxCreditLimit)
	{

	}

	/**
	 * @return максимвльный лимит
	 */
	public Boolean getIsMaxCreditLimitInclude()
	{
		return isMaxCreditLimitInclude;
	}

	/**
	 * @param maxCreditLimitInclude максимвльный лимит
	 */
	public void setIsMaxCreditLimitInclude(Boolean maxCreditLimitInclude)
	{
		isMaxCreditLimitInclude = maxCreditLimitInclude;
	}

	/**
	 * @return %  ставка  по предодобренной карте
	 */
	public BigDecimal getOfferInterestRate()
	{
		return offerInterestRate;
	}

	/**
	 * @param offerInterestRate %  ставка  по предодобренной карте
	 */
	public void setOfferInterestRate(BigDecimal offerInterestRate)
	{
		this.offerInterestRate = offerInterestRate;
	}

	/**
	 * @return плата за годовое обслуживание: первый год
	 */
	public Money getFirstYearPayment()
	{
		if(firstYearPayment == null || firstYearPaymentCurrency == null)
			return new Money();
		return new Money(firstYearPayment, firstYearPaymentCurrency);
	}

	/**
	 * @return плата за годовое обслуживание: первый год
	 */
	public Money getNextYearPayment()
	{
		if(nextYearPayment == null || nextYearPaymentCurrency == null)
			return new Money();
		return new Money(nextYearPayment, nextYearPaymentCurrency);
	}

	/**
	 * @return дата изменения
	 */
	public Calendar getChangeDate()
	{
		return changeDate;
	}

	/**
	 * @param changeDate дата изменения
	 */
	public void setChangeDate(Calendar changeDate)
	{
		this.changeDate = changeDate;
	}

	/**
	 * @return наименование
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name наименование
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return есть льготный период
	 */
	public Boolean getAllowGracePeriod()
	{
		return allowGracePeriod;
	}

	/**
	 * @param allowGracePeriod есть льготный период
	 */
	public void setAllowGracePeriod(Boolean allowGracePeriod)
	{
		this.allowGracePeriod = allowGracePeriod;
	}

	/**
	 * @return льготный период до
	 */
	public Integer getGracePeriodDuration()
	{
		return gracePeriodDuration;
	}

	/**
	 * @param gracePeriodDuration льготный период до
	 */
	public void setGracePeriodDuration(Integer gracePeriodDuration)
	{
		this.gracePeriodDuration = gracePeriodDuration;
	}

	/**
	 * @return % ставка в льготный период
	 */
	public BigDecimal getGracePeriodInterestRate()
	{
		return gracePeriodInterestRate;
	}

	/**
	 * @param gracePeriodInterestRate % ставка в льготный период
	 */
	public void setGracePeriodInterestRate(BigDecimal gracePeriodInterestRate)
	{
		this.gracePeriodInterestRate = gracePeriodInterestRate;
	}

	/**
	 * @return доп. условия
	 */
	public String getAdditionalTerms()
	{
		return additionalTerms;
	}

	/**
	 * @param additionalTerms доп. условия
	 */
	public void setAdditionalTerms(String additionalTerms)
	{
		this.additionalTerms = additionalTerms;
	}

	/**
	 * @return мин. кредитный лимит
	 */
	public Currency getMinCreditLimitCurrency()
	{
		return minCreditLimitCurrency;
	}

	/**
	 * @param minCreditLimitCurrency мин. кредитный лимит
	 */
	public void setMinCreditLimitCurrency(Currency minCreditLimitCurrency)
	{
		this.minCreditLimitCurrency = minCreditLimitCurrency;
	}

	/**
	 * @return мин. кредитный лимит
	 */
	public Currency getMaxCreditLimitCurrency()
	{
		return maxCreditLimitCurrency;
	}

	/**
	 * @param maxCreditLimitCurrency мин. кредитный лимит
	 */
	public void setMaxCreditLimitCurrency(Currency maxCreditLimitCurrency)
	{
		this.maxCreditLimitCurrency = maxCreditLimitCurrency;
	}

	/**
	 * @return  плата за годовое обслуживание: первый год
	 */
	public Currency getFirstYearPaymentCurrency()
	{
		return firstYearPaymentCurrency;
	}

	/**
	 * @param firstYearPaymentCurrency плата за годовое обслуживание: первый год
	 */
	public void setFirstYearPaymentCurrency(Currency firstYearPaymentCurrency)
	{
		this.firstYearPaymentCurrency = firstYearPaymentCurrency;
	}

	/**
	 * @return плата за годовое обслуживание: последующие годы
	 */
	public Currency getNextYearPaymentCurrency()
	{
		return nextYearPaymentCurrency;
	}

	/**
	 * @param nextYearPaymentCurrency  плата за годовое обслуживание: последующие годы
	 */
	public void setNextYearPaymentCurrency(Currency nextYearPaymentCurrency)
	{
		this.nextYearPaymentCurrency = nextYearPaymentCurrency;
	}
}
