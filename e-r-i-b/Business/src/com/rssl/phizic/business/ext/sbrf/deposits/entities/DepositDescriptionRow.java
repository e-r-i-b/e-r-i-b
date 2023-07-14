package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import java.math.BigDecimal;

/**
 * Сущность с параметрами для отображения строки описания вклада на странице продукта
 *
 * @author EgorovaA
 * @ created 01.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositDescriptionRow
{
	private String currency;
	private BigDecimal sumBegin;
	private BigDecimal minRate;
	private BigDecimal maxRate;
	private Boolean isPromo;

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public BigDecimal getSumBegin()
	{
		return sumBegin;
	}

	public void setSumBegin(BigDecimal sumBegin)
	{
		this.sumBegin = sumBegin;
	}

	public BigDecimal getMinRate()
	{
		return minRate;
	}

	public void setMinRate(BigDecimal minRate)
	{
		this.minRate = minRate;
	}

	public BigDecimal getMaxRate()
	{
		return maxRate;
	}

	public void setMaxRate(BigDecimal maxRate)
	{
		this.maxRate = maxRate;
	}

	public Boolean getPromo()
	{
		return isPromo;
	}

	public void setPromo(Boolean promo)
	{
		isPromo = promo;
	}
}
