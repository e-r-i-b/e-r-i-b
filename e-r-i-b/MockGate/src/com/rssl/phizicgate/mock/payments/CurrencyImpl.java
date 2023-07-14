package com.rssl.phizicgate.mock.payments;

import com.rssl.phizic.common.types.Currency;

/**
 * @author akrenev
 * @ created 04.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyImpl implements Currency
{
	private String number;
	private String code;
	private String name;
	private String externalId;

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public boolean compare(Currency c)
	{
		return true;
	}
}
