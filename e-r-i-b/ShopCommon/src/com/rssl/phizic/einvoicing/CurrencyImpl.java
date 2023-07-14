package com.rssl.phizic.einvoicing;

import com.rssl.phizic.common.types.Currency;

/**
 * @author bogdanov
 * @ created 20.02.14
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyImpl implements Currency
{
	private String code;

	public CurrencyImpl(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public String getExternalId()
	{
		return null;
	}

	public boolean compare(Currency c)
	{
		return code.equals(c.getCode());
	}

	public String getName()
	{
		return null;
	}

	public String getNumber()
	{
		return null;
	}
}
