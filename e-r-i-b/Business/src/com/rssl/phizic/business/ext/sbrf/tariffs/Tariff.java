package com.rssl.phizic.business.ext.sbrf.tariffs;

import com.rssl.phizic.gate.commission.CommissionTariff;
import com.rssl.phizic.gate.commission.TransferType;

import java.math.BigDecimal;

/**
 * @author niculichev
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class Tariff implements CommissionTariff
{
	private Long id;
	private TransferType transferType; // тип перевода
	private String currencyCode; // код валюты
	private BigDecimal percent; // процент от суммы
	private BigDecimal minAmount; // минимальная сумма
	private BigDecimal maxAmount; // максимальная сумма

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public BigDecimal getPercent()
	{
		return percent;
	}

	public void setPercent(BigDecimal percent)
	{
		this.percent = percent;
	}


	public TransferType getTransferType()
	{
		return transferType;
	}

	public void setTransferType(TransferType transferType)
	{
		this.transferType = transferType;
	}

	public BigDecimal getMinAmount()
	{
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount)
	{
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount()
	{
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount)
	{
		this.maxAmount = maxAmount;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}
}
