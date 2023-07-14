package com.rssl.phizic.business.commission;

/**
 * @author Evgrafov
 * @ created 11.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4951 $
 */

public abstract class CommissionRule
{
	private Long id;
	private CommissionType type;
	private String         currencyCode;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CommissionType getType()
	{
		return type;
	}

	void setType(CommissionType type)
	{
		this.type = type;
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