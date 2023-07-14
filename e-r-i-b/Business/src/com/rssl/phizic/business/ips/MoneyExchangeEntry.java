package com.rssl.phizic.business.ips;

import com.rssl.phizic.business.rates.Rate;
import com.rssl.phizic.common.types.Currency;

import java.util.Calendar;
import java.util.List;
import java.util.Collections;

/**
 * @author Erkin
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */
class MoneyExchangeEntry
{
	private Currency currencyFrom;

	private Currency currencyTo;

	private Calendar start;

	private Calendar until;

	/**
	 * Курсы
	 * Список отсортирован по возрастанию Rate.effDate!
	 */
	private List<Rate> rates = Collections.emptyList();

	///////////////////////////////////////////////////////////////////////////

	Currency getCurrencyFrom()
	{
		return currencyFrom;
	}

	void setCurrencyFrom(Currency currencyFrom)
	{
		this.currencyFrom = currencyFrom;
	}

	Currency getCurrencyTo()
	{
		return currencyTo;
	}

	void setCurrencyTo(Currency currencyTo)
	{
		this.currencyTo = currencyTo;
	}

	Calendar getStart()
	{
		return start;
	}

	void setStart(Calendar start)
	{
		this.start = start;
	}

	Calendar getUntil()
	{
		return until;
	}

	void setUntil(Calendar until)
	{
		this.until = until;
	}

	List<Rate> getRates()
	{
		return Collections.unmodifiableList(rates);
	}

	void setRates(List<Rate> rates)
	{
		this.rates = rates;
	}
}
