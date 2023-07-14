package com.rssl.phizic.business.rates.event;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.events.Event;

import java.math.BigDecimal;
import java.util.List;

/**
 * Событие очистки кеша курсов валют
 * @author gladishev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateCacheEvent implements Event
{
	private List<Pair<CurrencyRate, BigDecimal>> rates;
	private Office office;

	public CurrencyRateCacheEvent(List<Pair<CurrencyRate, BigDecimal>> rates, Office office)
	{
		this.rates = rates;
		this.office = office;
	}

	public List<Pair<CurrencyRate, BigDecimal>> getRates()
	{
		return rates;
	}

	public Office getOffice()
	{
		return office;
	}

	public String getStringForLog()
	{
		return "CurrencyRateCacheEvent, офис " + office.toString();
	}
}
