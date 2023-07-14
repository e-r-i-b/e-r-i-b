package com.rssl.ikfl.crediting;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Одно из условий предложения
 */
@PlainOldJavaObject
@SuppressWarnings("PackageVisibleField")
public class OfferCondition implements Serializable
{
	/**
	 * ID в таблице OFFERS [1]
	 */
	long offerId;

	/**
	 * Ставка [1]
	 */
	BigDecimal rate;

	/**
	 * Период (мес) [1]
	 */
	int period;

	/**
	 * Сумма [1]
	 */
	BigDecimal amount;

	public OfferCondition()
	{
	}

	public OfferCondition(long offerId, BigDecimal rate, int period, BigDecimal amount)
	{
		this.offerId = offerId;
		this.rate = rate;
		this.period = period;
		this.amount = amount;
	}

	public OfferCondition(BigDecimal rate, int period, BigDecimal amount)
	{
		this.rate = rate;
		this.period = period;
		this.amount = amount;
	}
	
	public long getOfferId()
	{
		return offerId;
	}

	public void setOfferId(long offerId)
	{
		this.offerId = offerId;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public int getPeriod()
	{
		return period;
	}

	public void setPeriod(int period)
	{
		this.period = period;
	}

	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}
}
