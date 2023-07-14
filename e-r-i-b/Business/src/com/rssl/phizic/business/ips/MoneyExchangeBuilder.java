package com.rssl.phizic.business.ips;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.rates.Rate;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Erkin
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Билдер для конструирования валютного обменника
 */
class MoneyExchangeBuilder
{
	private static final String QUERY_PREFIX = MoneyExchanger.class.getName() + ".";

	private final Map<String, MoneyExchangeEntry> entries = new HashMap<String, MoneyExchangeEntry>();

	///////////////////////////////////////////////////////////////////////////

	void addExchange(Currency currencyFrom, Currency currencyTo, Calendar date)
	{
		Calendar start = DateUtils.truncate(date, Calendar.DATE);
		Calendar until = DateUtils.ceiling(date, Calendar.DATE);

		MoneyExchangeEntry entry = getEntry(currencyFrom, currencyTo);
		if (entry.getStart() == null || entry.getStart().after(start))
			entry.setStart(start);
		if (entry.getUntil() == null || entry.getUntil().before(until))
			entry.setUntil(until);
	}

	private MoneyExchangeEntry getEntry(Currency currencyFrom, Currency currencyTo)
	{
		String entryCode = MoneyExchangeHelper.getExchangeCode(currencyFrom, currencyTo);
		MoneyExchangeEntry entry = entries.get(entryCode);
		if (entry == null) {
			entry = new MoneyExchangeEntry();
			entry.setCurrencyFrom(currencyFrom);
			entry.setCurrencyTo(currencyTo);
			entries.put(entryCode, entry);
		}
		return entry;
	}

	///////////////////////////////////////////////////////////////////////////

	MoneyExchanger build(final Department currencyRateOffice, final CurrencyRateType currencyRateType) throws BusinessException
	{
		for (MoneyExchangeEntry entry : entries.values())
			entry.setRates(Collections.<Rate>emptyList());

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getRates");
					query.setLong("departmentId", currencyRateOffice.getId());
					query.setInteger("rateType", currencyRateType.getId());

					for (MoneyExchangeEntry entry : entries.values())
					{
						query.setString("currencyFrom", entry.getCurrencyFrom().getNumber());
						query.setString("currencyTo", entry.getCurrencyTo().getNumber());
						query.setCalendar("startDate", entry.getStart());
						query.setCalendar("untilDate", entry.getUntil());

						@SuppressWarnings({"unchecked"})
						List<Rate> rates = query.list();
						entry.setRates(rates);
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		MoneyExchanger exchanger = new MoneyExchanger(entries);
		entries.clear();
		return exchanger;
	}
}
