package com.rssl.phizicgate.ips;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;

import java.util.*;

/**
 * @author Erkin
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */
class TestCurrencyService extends AbstractService implements CurrencyService
{
	private final List<Currency> currencies = new LinkedList<Currency>();

	private final Map<String, Currency> currenciesById = new HashMap<String, Currency>();

	private final Map<String, Currency> currenciesByCode = new HashMap<String, Currency>();

	private final Map<String, Currency> currenciesByNumber = new HashMap<String, Currency>();

	private final Currency nationalCurrency;

	///////////////////////////////////////////////////////////////////////////

	TestCurrencyService(GateFactory factory)
	{
		super(factory);

		nationalCurrency = buildCurrency("Рубль", "810", "RUR");
		buildCurrency("Рубль", "643", "RUB");
		buildCurrency("Доллар", "840", "USD");
		buildCurrency("Евро", "978", "EUR");
	}

	private Currency buildCurrency(String name, String number, String code)
	{
		TestCurrency currency = new TestCurrency();
		currency.setName(name);
		currency.setCode(code);
		currency.setNumber(number);
		currency.setExternalId(code);

		currencies.add(currency);
		currenciesById.put(currency.getExternalId(), currency);
		currenciesByCode.put(currency.getCode(), currency);
		currenciesByNumber.put(currency.getNumber(), currency);

		return currency;
	}

	///////////////////////////////////////////////////////////////////////////

	public List<Currency> getAll() throws GateException, GateLogicException
	{
		return Collections.unmodifiableList(currencies);
	}

	public Currency findById(String currencyId) throws GateException
	{
		return currenciesById.get(currencyId);
	}

	public Currency findByAlphabeticCode(String currencyCode) throws GateException
	{
		return currenciesByCode.get(currencyCode.toUpperCase());
	}

	public Currency findByNumericCode(String currencyCode) throws GateException
	{
		return currenciesByNumber.get(currencyCode.toUpperCase());
	}

	public Currency getNationalCurrency() throws GateException
	{
		return nationalCurrency;
	}
}
