package com.rssl.phizic.business.hibernate;

import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * “ип валюты по Alphabetic ISO коду
 * @author komarov
 * @ created 28.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class IsoCurrencyType extends CurrencyType
{
	protected Object getCurrency(CurrencyService currencyService, String currencyCode) throws GateException
	{
		return currencyService.findByAlphabeticCode(currencyCode);
	}
}
