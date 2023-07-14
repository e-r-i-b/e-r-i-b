package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 18.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyServiceImpl extends AbstractService implements CurrencyService
{
	private static final String DELIMITER = "|";
	private static final SimpleService service = new SimpleService();

	private static final Cache currenciesByNumericCodecache;
	private static final Cache currenciesByAlphabeticCodecache;
	private static final Cache currenciesByIdCodecache;

	static
	{
		currenciesByIdCodecache = CacheProvider.getCache("currencies-by-id-cache");

		if ( currenciesByIdCodecache == null )
			throw new RuntimeException("Ќе найден кеш валют по id [currencies-by-id-cache]");

		currenciesByNumericCodecache = CacheProvider.getCache("currencies-by-numeric-code-cache");

		if ( currenciesByNumericCodecache == null )
			throw new RuntimeException("Ќе найден кеш валют по числовому коду [currencies-by-numeric-code-cache]");

		currenciesByAlphabeticCodecache = CacheProvider.getCache("currencies-by-alphabetic-code-cache");

		if ( currenciesByAlphabeticCodecache == null )
			throw new RuntimeException("Ќе найден кеш валют символьному коду [currencies-by-alphabetic-code-cache]");
	}

	/**
	 * ctor
	 * @param factory шлюзна€ фабрика
	 */
	public CurrencyServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	 /*—писок всех валют */
	public List<Currency> getAll() throws GateException
	{
		try
		{
			List<Currency> currencies = service.getAll(Currency.class);

			for ( Currency currency : currencies )
			{
				add2Cache(currency);
			}

			return currencies;

		}
		catch (BusinessException e)
		{
		   throw new GateException(e);
		}
	}

	/**
	 * ¬озвращает вылюту по id*/
	@Deprecated
	public Currency findById(final String currencyId) throws GateException
	{
       Element  element  = currenciesByIdCodecache.get(currencyId + DELIMITER + MultiLocaleContext.getLocaleId());

		if ( element != null )
			return (Currency) element.getObjectValue();

		try
		{
			Currency currency = HibernateExecutor.getInstance().execute(new HibernateAction<Currency>()
			{
				public Currency run(Session session) throws Exception
				{
					BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.dictionaries.currencies.getCurrencyById");
					query.setParameter("id", currencyId);
					return query.executeUnique();
				}
			});

			if ( currency != null )
				add2Cache(currency);

			return currency;
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	/*¬озвращает вылюту по ее Alphabetic ISO коду (RUB, USD etc) */
	public Currency findByAlphabeticCode(final String currCode) throws GateException
	{
		//поскольку валюта приходит из ритейла - то в случае рублей она может быть (RUR, 810)
		// а не (RUB, 643). ќбойдем возможные преп€тстви€:
		final String currencyCode = "RUR".equals(currCode) ? "RUB" : currCode;

		Element  element  = currenciesByAlphabeticCodecache.get(currencyCode + DELIMITER + MultiLocaleContext.getLocaleId());

		if ( element != null )
			return (Currency) element.getObjectValue();

		try
		{
			Currency currency = HibernateExecutor.getInstance().execute(new HibernateAction<Currency>()
			{
				public Currency run(Session session) throws Exception
				{
					BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.dictionaries.currencies.getCurrencyByCode");
					query.setParameter("code", currencyCode);
					return query.executeUnique();
				}
			});

			if ( currency != null )
				add2Cache(currency);

			return currency;
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	/*¬озвращает вылюту по ее Numeric ISO коду (643, 840 etc)*/
	public Currency findByNumericCode(final String currCode) throws GateException
	{
		//поскольку валюта приходит из ритейла - то в случае рублей она может быть (RUR, 810)
		// а не (RUB, 643). ќбойдем возможные преп€тстви€:
		final String currencyCode = "810".equals(currCode) ? "643" : currCode;

		Element element = currenciesByNumericCodecache.get(currencyCode + DELIMITER + MultiLocaleContext.getLocaleId());

		if ( element != null )
			return (Currency) element.getObjectValue();

		try
		{
			Currency currency = HibernateExecutor.getInstance().execute(new HibernateAction<Currency>()
			{
				public Currency run(Session session) throws Exception
				{
					BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.dictionaries.currencies.getCurrencyByNumericCode");
					query.setParameter("code", currencyCode);
					return query.executeUnique();
				}
			});

			if ( currency != null )
				add2Cache(currency);
			
			return currency;
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	public Currency getNationalCurrency() throws GateException
	{
		/**
		 * ≈сли в контексте указан код валюту, то ищем по нему, если нет, то считаем что национальна€ валюта с кодом 0 
 		 */
		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		if(bankContext.getNationalCurrencyCode()!=null)
		{
			return findByAlphabeticCode(bankContext.getNationalCurrencyCode());
		}
		else
		{
			return findById("0");
		}

	}

	private void add2Cache(Currency currency)
	{
		currenciesByNumericCodecache.put(new Element(currency.getNumber() + DELIMITER + MultiLocaleContext.getLocaleId(), currency));
		currenciesByAlphabeticCodecache.put(new Element(currency.getCode() + DELIMITER + MultiLocaleContext.getLocaleId(), currency));
		currenciesByIdCodecache.put(new Element(currency.getExternalId() + DELIMITER + MultiLocaleContext.getLocaleId(), currency));
	}
}
