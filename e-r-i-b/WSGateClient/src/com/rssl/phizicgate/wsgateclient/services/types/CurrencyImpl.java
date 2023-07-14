package com.rssl.phizicgate.wsgateclient.services.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyISOComparator;

/**
 * @author Omeliyanchuk
 * @ created 18.06.2009
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyImpl implements Currency
{
	String number;
	String code;
	String name;
	String externalId;

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
		return CurrencyISOComparator.compare(this,c);	
	}
}
