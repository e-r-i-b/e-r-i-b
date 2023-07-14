package com.rssl.phizicgate.sbrf.currency;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.impl.GateProperties;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.NumericUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kosyakov
 * @ created 17.10.2006
 * @ $Author: egorovaav $
 * @ $Revision: 66619 $
 */
public class CurrencyRateServiceFromHTML extends AbstractService implements CurrencyRateService
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final Map<String, String> currencyNamesMap = new HashMap<String, String>();

	static
	{
		currencyNamesMap.put("ДОЛЛАР США", "USD");
		currencyNamesMap.put("ЕВРО", "EUR");
	}

	private static final Object RATES_CACHE_LOCKER = new Object();
	private static List<CurrencyRate> ratesCache = new ArrayList<CurrencyRate>();
	private static long lastUpdateTime = 0;
	private static final String UNKNOWN_CODE = "0";

	private Reader htmlReader = null;

	void setHtmlReader(Reader htmlReader)
	{
		this.htmlReader = htmlReader;
	}

	public CurrencyRateServiceFromHTML(GateFactory factory)
	{
		super(factory);
	}

	private List<CurrencyRate> getRates() throws GateException, GateLogicException
	{
		long entryTime = new Date().getTime();
		if ((ratesCache.isEmpty() && ((entryTime - lastUpdateTime) > (1000 * 60 * 60))) || htmlReader != null)
		{
			synchronized (RATES_CACHE_LOCKER)
			{
				updateRates(entryTime);
			}
		}
		return Collections.unmodifiableList(ratesCache);
	}

	private void updateRates(long entryTime) throws GateException, GateLogicException
	{
		if ((ratesCache.isEmpty() && ((entryTime - lastUpdateTime) > (1000 * 60 * 60))) || htmlReader != null)
		{
			String html = null;
			String url = GateProperties.getProperties().getProperty("com.rssl.phizic.gate.sbrf.currencyrates.page.url");
			try
			{
				if (htmlReader == null)
					htmlReader = new InputStreamReader(new URL(url).openStream());
				StringBuffer stringBuffer = new StringBuffer();
				char[] buffer = new char[4096];
				for (int n; (n = htmlReader.read(buffer)) >= 0;)
				{
					stringBuffer.append(buffer, 0, n);
				}
				html = stringBuffer.toString();
				htmlReader.close();
			}
			catch (IOException e)
			{
				log.error("Can't load URL: " + url, e);
			}
			finally
			{
				htmlReader = null;
			}
			if (html != null)
			{
				String ratesTablePatternString = "<TABLE[^>]+ID=\"?AUTONUMBER1\"?.*?>(.*?)</TABLE>";
				String ratesValPatternString = "<TD.*?>.*?<FONT.*?>(.*?)</FONT>.*?</TD>\\s*";
				String ratesRowPatternString = ratesValPatternString + ratesValPatternString + ratesValPatternString;
				Pattern ratesTablePattern = Pattern.compile(ratesTablePatternString, Pattern.DOTALL);
				Matcher ratesTableMatcher = ratesTablePattern.matcher(html.toUpperCase());
				if (ratesTableMatcher.find())
				{
					Pattern ratesItemsPattern = Pattern.compile(ratesRowPatternString, Pattern.DOTALL);
					Matcher ratesItemsMatcher = ratesItemsPattern.matcher(ratesTableMatcher.group(1));
					if (ratesItemsMatcher.find())  // пропускаем заголовок
					{
						CurrencyService currencyService = getFactory().service(CurrencyService.class);
						List<CurrencyRate> rates = new ArrayList<CurrencyRate>();
						Currency national = currencyService.getNationalCurrency();
						if (national != null)
						{
							while (ratesItemsMatcher.find())
							{
								String currencyCode = currencyNamesMap
										.get(ratesItemsMatcher.group(1).trim().replaceAll("\\s+", " "));
								Currency cur = currencyService.findByAlphabeticCode(currencyCode);
								BigDecimal buyRate = null, saleRate = null;
								try
								{
									buyRate = NumericUtil.parseBigDecimal(ratesItemsMatcher.group(2));
									saleRate = NumericUtil.parseBigDecimal(ratesItemsMatcher.group(3));
								}
								catch (ParseException e)
								{
									throw new GateException(e);
								}
								//TODO думаю что, что-то тут напутал
								rates.add(new CurrencyRate(national, BigDecimal.ONE, cur, buyRate, CurrencyRateType.CB, UNKNOWN_CODE));
								rates.add(new CurrencyRate(national, BigDecimal.ONE, cur, saleRate, CurrencyRateType.SALE_REMOTE, UNKNOWN_CODE));
								rates.add(new CurrencyRate(national, BigDecimal.ONE, cur, buyRate, CurrencyRateType.BUY_REMOTE, UNKNOWN_CODE));

								// Составляем обратные курсы
								rates.add(new CurrencyRate(cur, saleRate, national, BigDecimal.ONE, CurrencyRateType.BUY_REMOTE, UNKNOWN_CODE));
								rates.add(new CurrencyRate(cur, buyRate, national, BigDecimal.ONE, CurrencyRateType.SALE_REMOTE, UNKNOWN_CODE));
								rates.add(new CurrencyRate(cur, buyRate, national, BigDecimal.ONE, CurrencyRateType.CB, UNKNOWN_CODE));
							}
							rates.add(new CurrencyRate(national, BigDecimal.ONE, national, BigDecimal.ONE, CurrencyRateType.CB, UNKNOWN_CODE));
							rates.add(new CurrencyRate(national, BigDecimal.ONE, national, BigDecimal.ONE, CurrencyRateType.BUY_REMOTE, UNKNOWN_CODE));
							rates.add(new CurrencyRate(national, BigDecimal.ONE, national, BigDecimal.ONE, CurrencyRateType.SALE_REMOTE, UNKNOWN_CODE));
						}
						ratesCache = rates;
						lastUpdateTime = entryTime;
					}
				}
			}
		}
	}

	public CurrencyRate getRate(Currency from, Currency to, CurrencyRateType type, Office office,
	                            String tarifPlanCodeType) throws GateException, GateLogicException
	{
		for (CurrencyRate rate : getRates())
		{
			if (rate.equals(from, to, type))
			{
				return rate;
			}
		}
		return null;
	}

	public CurrencyRate convert(Money from, Currency to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		// Тип BUY_REMOTE - клиент продает сумму from за to
		CurrencyRate rate = getRate(from.getCurrency(), to, CurrencyRateType.BUY_REMOTE, office,
				tarifPlanCodeType);
		if (rate != null)
			// адаптируем полученный курс к входной сумме
			rate = CurrencyUtils.getFromCurrencyRate(from.getDecimal(), rate);
		return rate;
	}

	public CurrencyRate convert(Currency from, Money to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		// Тип BUY_REMOTE - клиент продает валюту from за сумму to
		CurrencyRate rate = getRate(from, to.getCurrency(), CurrencyRateType.BUY_REMOTE, office, tarifPlanCodeType);
		if (rate != null)
			// адаптируем полученный курс к входной сумме
			rate = CurrencyUtils.getToCurrencyRate(to.getDecimal(), rate);
		return rate;
	}
}
