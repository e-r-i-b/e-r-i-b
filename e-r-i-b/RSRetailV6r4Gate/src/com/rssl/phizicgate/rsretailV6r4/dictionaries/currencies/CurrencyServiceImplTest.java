package com.rssl.phizicgate.rsretailV6r4.dictionaries.currencies;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizicgate.rsretailV6r4.junit.RSRetailV6r4GateTestCaseBase;
import com.rssl.phizicgate.rsretailV6r4.money.CurrencyImpl;

import java.util.List;

import junit.framework.Assert;

/**
 * @author: Pakhomova
 * @created: 12.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyServiceImplTest extends RSRetailV6r4GateTestCaseBase
{
	public void testCurrencyServiceImpl() throws GateException
	{
		CurrencyServiceImpl serviceImpl = new CurrencyServiceImpl(gateFactory);
		Currency cur1 = serviceImpl.findById("0");
		Currency cur2 = serviceImpl.getNationalCurrency();
		Currency cur3 = serviceImpl.findByAlphabeticCode("USD");
		Currency cur4 = serviceImpl.findByNumericCode("5");

		List<Currency> list = serviceImpl.getAll();

		CurrencyImpl currency = (CurrencyImpl)list.get(0);

		Assert.assertNotNull(serviceImpl.findById(currency.getId().toString()));

		for (Currency currency1 : list)
		{
			Assert.assertNotNull(serviceImpl.findById(((CurrencyImpl) currency1).getId().toString()));
		}
		for (Currency currency2 : list)
		{
			Assert.assertNotNull(serviceImpl.findByAlphabeticCode(currency2.getCode()));
	    }
	}
}
