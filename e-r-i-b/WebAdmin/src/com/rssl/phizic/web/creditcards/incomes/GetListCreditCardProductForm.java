package com.rssl.phizic.web.creditcards.incomes;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Dorzhinov
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class GetListCreditCardProductForm extends ListFormBase
{
	private String currency;
	private String creditLimit;
	private String include;

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getCreditLimit()
	{
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit)
	{
		this.creditLimit = creditLimit;
	}

	public String getInclude()
	{
		return include;
	}

	public void setInclude(String include)
	{
		this.include = include;
	}
}
