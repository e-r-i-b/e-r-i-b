package com.rssl.phizicgate.rsretailV6r20.money;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizicgate.rsretailV6r20.junit.RSRetailV6r20GateTestCaseBase;

import java.util.List;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyHelperTest extends RSRetailV6r20GateTestCaseBase
{
	public void testCurrencyHelperGetCurrency() throws GateException, GateLogicException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		List<Currency> list = currencyService.getAll();

		CurrencyImpl currency = (CurrencyImpl)list.get(0);

		assertNotNull(currencyService.findById(currency.getId().toString()));

		for (Currency currency1 : list)
		{
			assertNotNull(currencyService.findById(((CurrencyImpl) currency1).getId().toString()));
		}
		for (Currency currency2 : list)
		{
			assertNotNull(currencyService.findByAlphabeticCode(currency2.getCode()));
		}
	}
}
