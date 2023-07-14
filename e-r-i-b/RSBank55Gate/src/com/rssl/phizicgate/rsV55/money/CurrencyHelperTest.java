package com.rssl.phizicgate.rsV55.money;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.rsV55.test.RSRetaileGateTestCaselBase;

import java.util.List;

/**
 * @author Roshka
 * @ created 11.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyHelperTest extends RSRetaileGateTestCaselBase
{
	public CurrencyHelperTest() throws GateException
	{
	}

	public void testCurrencyHelperGetCurrency() throws GateException
	{
		CurrencyHelper helper = new CurrencyHelper();

		List<CurrencyImpl> list = helper.getCurrencies();

		CurrencyImpl currency = list.get(0);

		assertNotNull(helper.getCurrencyById(currency.getId()));

		for (Currency currency1 : list)
		{
			assertNotNull(helper.getCurrencyById(((CurrencyImpl) currency1).getId()));
		}
		for (Currency currency2 : list)
		{
			assertNotNull(helper.getCurrencyByCode(currency2.getCode()));
		}
	}
}