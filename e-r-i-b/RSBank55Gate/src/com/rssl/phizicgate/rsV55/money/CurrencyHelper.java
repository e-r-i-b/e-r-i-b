package com.rssl.phizicgate.rsV55.money;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizicgate.rsV55.data.GateRSV55Executor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.util.List;

/**
 * @author Roshka
 * @ created 11.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyHelper
{
	/**
	 * Получить валюту из ретейла по, id
	 * @param currencyId ид валюты
	 * @return {@link com.rssl.phizic.common.types.Currency}
	 * @throws GateException
	 */
	public CurrencyImpl getCurrencyById(final Long currencyId) throws GateException
	{
		try
		{
			return GateRSV55Executor.getInstance().execute(new HibernateAction<CurrencyImpl>()
			{
				public CurrencyImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.money.CurrencyHelper.getCurrencyById");
					query.setParameter("currencyId", currencyId);
					return (CurrencyImpl) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получить валюту по коду
	 * @param code
	 * @return
	 * @throws GateException
	 */
	public CurrencyImpl getCurrencyByCode(final String code) throws GateException
	{
		try
		{
			return GateRSV55Executor.getInstance().execute(new HibernateAction<CurrencyImpl>()
			{
				public CurrencyImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.money.CurrencyHelper.getCurrencyByCode");
					query.setParameter("code", code);
					return (CurrencyImpl) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получить список валют
	 * @return
	 * @throws GateException
	 */
	public List<CurrencyImpl> getCurrencies() throws GateException
	{
		try
		{
			return GateRSV55Executor.getInstance().execute(new HibernateAction<List<CurrencyImpl>>()
			{
				public List<CurrencyImpl> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.money.CurrencyHelper.GetCurrencies");
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получить ISO-код из ретейла по ид валюты
	 * @param currencyId ид валюты в ретейле
	 * @return ISO-код  валюты
	 * @throws GateException
	 */
	public String getCurrencyCode(Long currencyId) throws GateException
	{
		Currency retailCurrency = getCurrencyById(currencyId);
		if(retailCurrency.getCode().equals("RUR"))
			return "RUB";
		return retailCurrency.getCode();
	}

	/**
	 * Получить ид валюты в ретейле по ISO коду
	 * @param code - ISO-код
	 * @return идентификатор валюты в ретейле
	 */
	public Long getCurrencyId(String code)
	{
		CurrencyImpl retailCurrency;
		try
		{
			retailCurrency = getCurrencyByCode(code);
		}
		catch (GateException e)
		{
			throw new HibernateException(e);
		}

		return retailCurrency.getId();
	}

	/**
	 * являеться ли переданная валюта национальной для retail
	 * @param currency
	 * @return	 
	 */
	public static Boolean isCurrency( Currency currency)
	{
		return currency.getExternalId().equals("0");
	}
}