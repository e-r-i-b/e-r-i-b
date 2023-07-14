package com.rssl.phizic.business.ips;

import com.rssl.phizic.common.types.Currency;

/**
 * @author Erkin
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class MoneyExchangeHelper
{
	static String getExchangeCode(Currency currencyFrom, Currency currencyTo)
	{
		return currencyFrom.getCode() + "->" + currencyTo.getCode();
	}
}
