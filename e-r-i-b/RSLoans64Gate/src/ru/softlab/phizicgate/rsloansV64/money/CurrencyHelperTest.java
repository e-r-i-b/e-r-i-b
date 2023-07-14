package ru.softlab.phizicgate.rsloansV64.money;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.Currency;

import java.util.List;

import junit.framework.Assert;
import ru.softlab.phizicgate.rsloansV64.junit.RSLoans64GateTestCaseBase;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyHelperTest extends RSLoans64GateTestCaseBase
{
	public void testCurrencyHelperGetCurrency() throws GateException
	{
		CurrencyHelper helper = new CurrencyHelper();

		List<CurrencyImpl> list = helper.getCurrencies();

		CurrencyImpl currency = list.get(0);

		Assert.assertNotNull(helper.getCurrencyById(currency.getId()));

		for (Currency currency1 : list)
		{
			Assert.assertNotNull(helper.getCurrencyById(((CurrencyImpl) currency1).getId()));
		}
		for (Currency currency2 : list)
		{
			Assert.assertNotNull(helper.getCurrencyByCode(currency2.getCode()));
		}
	}
}
