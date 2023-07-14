package com.rssl.phizic.business.ips;

import com.rssl.phizic.business.rates.Rate;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Валютный обменник
 * Конвертирует только по найденным курсам
 * Не умеет считать кросс-курсы
 */
class MoneyExchanger
{
	private final Map<String, MoneyExchangeEntry> entries;

	///////////////////////////////////////////////////////////////////////////

	MoneyExchanger(Map<String, MoneyExchangeEntry> entries)
	{
		this.entries = new HashMap<String, MoneyExchangeEntry>(entries);
	}

	Money convert(Money moneyFrom, Currency currencyTo, Calendar date) throws MoneyExchangeException
	{
		if (moneyFrom.getAsCents() == 0)
			return new Money(BigDecimal.ZERO, currencyTo);

		Currency currencyFrom = moneyFrom.getCurrency();
		String exchangeCode = MoneyExchangeHelper.getExchangeCode(currencyFrom, currencyTo);
		MoneyExchangeEntry entry = entries.get(exchangeCode);
		if (entry == null)
			throw new MoneyExchangeException("Не найден список курсов валюты для обмена " + exchangeCode);

		Rate rate = lookupRate(entry.getRates(), date);
		if (rate == null)
			throw new MoneyExchangeException("Не найден курс валюты для обмена " + exchangeCode);

		return convertMoney(moneyFrom, rate);
	}

	private Money convertMoney(Money money, Rate rate)
	{
		BigDecimal amountFrom = money.getDecimal();
		BigDecimal amountTo = amountFrom
				.multiply(rate.getToValue())
				.divide(rate.getFromValue(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);

		return new Money(amountTo, rate.getToCurrency());
	}

	private Rate lookupRate(List<Rate> rates, Calendar date)
	{
		// 0. Интересует только дата
		// noinspection AssignmentToMethodParameter
		date = DateUtils.truncate(date, Calendar.DATE);

		// 1. Бинарный поиск курса, совпадающего с указанной датой
		int low = 0;
		int high = rates.size()-1;
		while (low <= high)
		{
		    int mid = (low + high) >> 1;
		    Rate rate = rates.get(mid);
		    int cmp = rate.getEffDate().compareTo(date);

		    if (cmp < 0)
			    low = mid + 1;
		    else if (cmp > 0)
			    high = mid - 1;
		    // 1.A Найден курс на искомую дату
		    else return rate;
		}

		// 1.B Все курсы появились позднее указанной даты => курс не известен
		if (low == 0)
			return null;

		// 1.C Все курсы появились раньше указанной даты => берём последний курс
		// 1.D Дата находится "между" датами курсов => берём курс, ближайший по дате снизу
		return rates.get(Math.min(low, rates.size()-1));
	}
}
