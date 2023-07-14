package com.rssl.phizicgate.ips;

import com.rssl.phizic.common.types.Currency;

/**
 * @author Erkin
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */
class TestCurrency implements Currency
{
	private String number;

	private String code;

	private String name;

	private String externalId;

	///////////////////////////////////////////////////////////////////////////

	public String getNumber()
	{
		return number;
	}

	void setNumber(String number)
	{
		this.number = number;
	}

	public String getCode()
	{
		return code;
	}

	void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	void setName(String name)
	{
		this.name = name;
	}

	public String getExternalId()
	{
		return externalId;
	}

	void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public boolean compare(Currency c)
	{
		return true;
	}
}
