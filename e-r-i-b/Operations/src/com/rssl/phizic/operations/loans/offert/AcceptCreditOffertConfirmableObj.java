package com.rssl.phizic.operations.loans.offert;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;

import java.math.BigDecimal;

/**
 * @author Moshenko
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AcceptCreditOffertConfirmableObj implements ConfirmableObject
{
	private Long id;
	private String parameters;
	private BigDecimal loanAmount;
	private String currency;
	private Long loanPeriod;
	private String loanRate;

	AcceptCreditOffertConfirmableObj(String url)
	{
		parameters = "pageUrl = " + url;
	}

	public Long getId()
	{
		hashParameters();
		return id;
	}

	private void hashParameters()
	{
		Integer intId = parameters.hashCode();
		id = intId.longValue();
	}

	public byte[] getSignableObject()
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}

	public String getCurrency()
	{
		return currency;
	}

	public BigDecimal getLoanAmount()
	{
		return loanAmount;
	}

	public Long getLoanPeriod()
	{
		return loanPeriod;
	}

	public String getLoanRate()
	{
		return loanRate;
	}

	public void setLoanRate(String loanRate)
	{
		this.loanRate = loanRate;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public void setLoanAmount(BigDecimal loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public void setLoanPeriod(Long loanPeriod)
	{
		this.loanPeriod = loanPeriod;
	}


}
