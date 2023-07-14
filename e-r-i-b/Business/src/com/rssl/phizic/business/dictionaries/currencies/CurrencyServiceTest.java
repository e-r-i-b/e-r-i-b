package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 18.09.2006
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyServiceTest extends BusinessTestCaseBase
{
	public CurrencyServiceTest() throws GateException
	{
		super();
	}

	public void testGetAll() throws GateException, GateLogicException
	{
		CurrencyService service = GateSingleton.getFactory().service(CurrencyService.class);

		List<Currency> all = service.getAll();

		assertNotNull(all);
		assertTrue(all.size() > 0);
	}

	public void testFind() throws GateException, GateLogicException
	{
		CurrencyService service = GateSingleton.getFactory().service(CurrencyService.class);

		List<Currency> all = service.getAll();
		Currency curr1 = all.get(0);
		Currency curr2 = service.findByNumericCode(curr1.getNumber());

		assertNotNull(curr2);
		assertEquals(curr1.getCode(), curr2.getCode());

		Currency curr3 = service.findByAlphabeticCode("RUB");

		assertNotNull(curr3);

		Currency curr4 = service.findByAlphabeticCode("ßßß");

		assertNull(curr4);

	}
}
