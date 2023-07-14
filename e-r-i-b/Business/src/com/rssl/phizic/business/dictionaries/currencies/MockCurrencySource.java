package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kosyakov
 * @ created 27.11.2006
 * @ $Author: gulov $
 * @ $Revision: 20192 $
 */
public class MockCurrencySource implements ReplicaSource
{
	List<CurrencyImpl> currencyList = null;

	public MockCurrencySource()
	{
		CurrencyImpl currency1 = new CurrencyImpl();
		currency1.setCode("RUB");
		currency1.setExternalId("643");
		currency1.setNumber("643");
		currency1.setName("–Œ——»…— »… –”¡À‹");
		CurrencyImpl currency2 = new CurrencyImpl();
		currency2.setCode("RUR");
		currency1.setExternalId("810");
		currency2.setNumber("810");
		currency2.setName("œ–»«Õ¿  –Œ——»…— Œ√Œ –”¡Àﬂ");
		CurrencyImpl currency3 = new CurrencyImpl();
		currency3.setCode("USD");
		currency1.setExternalId("840");
		currency3.setNumber("840");
		currency3.setName("ƒŒÀÀ¿– —ÿ¿");
		CurrencyImpl currency4 = new CurrencyImpl();
		currency4.setCode("EUR");
		currency1.setExternalId("978");
		currency4.setNumber("978");
		currency4.setName("≈¬–Œ");
		currencyList = new ArrayList<CurrencyImpl>();
		currencyList.add(currency1);
		currencyList.add(currency2);
		currencyList.add(currency3);
		currencyList.add(currency4);
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException
	{
		if (!currencyList.isEmpty())
		{
			CurrencyComparator comparator = new CurrencyComparator();
			Collections.sort(currencyList, comparator);
		}
		return currencyList.iterator();
	}

	public void close()
	{
		return;
	}
}
