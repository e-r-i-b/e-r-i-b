package com.rssl.phizicgate.rsretailV6r4.dictionaries.currencies;

import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper;
import com.rssl.phizicgate.rsretailV6r4.money.CurrencyImpl;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author: Pakhomova
 * @created: 12.05.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Работает именно с базой ретейла. Т.е. вся информация из БД ретейла.
 */
public class CurrencyServiceImpl  extends AbstractService implements CurrencyService
{
	private static Map<String,Currency> currencyById = new ConcurrentHashMap<String,Currency>();
	private static Map<String,Currency> currencyByAlphabeticCode = new ConcurrentHashMap<String,Currency>();
	private static Map<String,Currency> currencyByNumericCode = new ConcurrentHashMap<String,Currency>();
	private static final AtomicReference<Currency> nationalCurrency = new AtomicReference<Currency>();
	
	public CurrencyServiceImpl(GateFactory factory)
	{
		super(factory);	
	}

	public List<Currency> getAll() throws GateException
	{
		try
		{
			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<Currency>>()
			{
				public List<Currency> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper.GetCurrencies");
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Currency findById(final String currencyId) throws GateException
	{
		Currency result = null;

		if((result=currencyById.get(currencyId))!=null)
			return result;

		try
		{
			result = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<CurrencyImpl>()
			{
				public CurrencyImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper.getCurrencyById");
					query.setParameter("currencyId", Long.decode(currencyId));
					return (CurrencyImpl) query.uniqueResult();
				}
			});
			if(result!=null)
				currencyById.put(currencyId,result);
			return result;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Currency findByAlphabeticCode(final String currencyCode) throws GateException
	{
		Currency result = null;

		if((result=currencyByAlphabeticCode.get(currencyCode))!=null)
			return result;

		try
		{
			result = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<CurrencyImpl>()
			{
				public CurrencyImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper.getCurrencyByCode");
					query.setParameter("code", currencyCode);
					return (CurrencyImpl) query.uniqueResult();
				}
			});
			if(result!=null)
				currencyByAlphabeticCode.put(currencyCode,result);
			return result;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Currency findByNumericCode(final String currencyCode) throws GateException
	{
		Currency result = null;

		if((result=currencyByNumericCode.get(currencyCode))!=null)
			return result;

		try
		{
			result = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<CurrencyImpl>()
			{
				public CurrencyImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsretailV6r4.money.CurrencyHelper.getCurrencyByNumericCode");
					query.setParameter("id", currencyCode);
					return (CurrencyImpl) query.uniqueResult();
				}
			});
			if(result!=null)
				currencyByNumericCode.put(currencyCode,result);
			return result;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Currency getNationalCurrency() throws GateException
	{
		/**
		 * контекст не используем, в ритейле нац. валюта с кодом 0
 		 */
		if(nationalCurrency.get()==null)
			nationalCurrency.compareAndSet(null,findById("0"));

		return nationalCurrency.get();
	}
}
