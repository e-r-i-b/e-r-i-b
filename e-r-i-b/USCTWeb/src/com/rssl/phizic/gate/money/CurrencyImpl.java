package com.rssl.phizic.gate.money;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyISOComparator;

/**
 * Валюта
 *
 * @author khudyakov
 * @ created 12.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyImpl implements Currency
{
	private String code;


	public CurrencyImpl()
	{}

	public CurrencyImpl(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getExternalId()
	{
		return null;
	}

	public boolean compare(Currency c)
	{
		return CurrencyISOComparator.compare(c, this);
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
