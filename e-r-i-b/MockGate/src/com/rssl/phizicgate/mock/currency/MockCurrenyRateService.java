package com.rssl.phizicgate.mock.currency;

import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import java.math.BigDecimal;

/**
 * @author hudyakov
 * @ created 30.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockCurrenyRateService extends AbstractService implements CurrencyRateService
{
	private static final String CURRENCY_FILE_NAME = "com/rssl/phizicgate/mock/currency/xml/currency.xml";
	private static final String DELIMITER = "|";

	private static final Map<String, Map<String, String>> currencyRates = new HashMap<String, Map<String, String>>();
	private static final Map<String, CurrencyRateType>  stringToCurrencyRate = new HashMap<String, CurrencyRateType>();

	static
	{
		//соответствие вида операции
		stringToCurrencyRate.put("CB",   CurrencyRateType.CB);
		stringToCurrencyRate.put("BUY",  CurrencyRateType.BUY_REMOTE);
		stringToCurrencyRate.put("SALE", CurrencyRateType.SALE_REMOTE);
	}

	/**
	 * ctor
	 * @param factory фабрика
	 * @throws GateException
	 */
	public MockCurrenyRateService(GateFactory factory) throws GateException
	{
		super(factory);

		try
		{
			Document document = XmlHelper.loadDocumentFromResource(CURRENCY_FILE_NAME);
			currencyRates.putAll(parseRates(document));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public CurrencyRate getRate(Currency from, Currency to, CurrencyRateType type, Office office,
	                            String tarifPlanCodeType) throws GateException, GateLogicException
	{
		String key  = (from.getCode().equals(to.getCode())) ? from.getCode() : from.getCode() + DELIMITER + to.getCode();
		String course   = getCourse(currencyRates.get(key), type);
		return new CurrencyRate(from, BigDecimal.ONE, to, BigDecimal.ONE.multiply(getBigDecimalValue(course)),
				CurrencyRateType.CB, tarifPlanCodeType);
	}

	public CurrencyRate convert(Money from, Currency to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		String fromCode = from.getCurrency().getCode();
		String key  = (fromCode.equals(to.getCode())) ? fromCode : fromCode + DELIMITER + to.getCode();
		String course   = getCourse(currencyRates.get(key), CurrencyRateType.BUY_REMOTE);

		String checkedTariffPlanCodeType = TariffPlanHelper.getTariffPlanCode(tarifPlanCodeType);
		return new CurrencyRate(to, from.getDecimal().multiply(getBigDecimalValue(course)), from.getCurrency(),
				from.getDecimal() , CurrencyRateType.BUY_REMOTE, checkedTariffPlanCodeType);
	}

	public CurrencyRate convert(Currency from, Money to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		String toCode   = to.getCurrency().getCode();
		String key  = (toCode.equals(from.getCode())) ? toCode : toCode + DELIMITER + from.getCode();
		String course   = getCourse(currencyRates.get(key), CurrencyRateType.SALE_REMOTE);

		String checkedTariffPlanCodeType = TariffPlanHelper.getTariffPlanCode(tarifPlanCodeType);
		return new CurrencyRate(to.getCurrency(), to.getDecimal(), from, to.getDecimal().multiply(getBigDecimalValue(course)),
				CurrencyRateType.SALE_REMOTE, checkedTariffPlanCodeType);
	}

	private Map<String, Map<String, String>> parseRates(Document document) throws Exception
	{
		final Map<String, Map<String, String>> rates = new HashMap<String, Map<String, String>>();
		XmlHelper.foreach(document.getDocumentElement(), "entity", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				rates.put(element.getAttribute("key"), parseRate(element));
			}
		});
		return rates;
	}

	private Map<String, String> parseRate(Element element) throws Exception
	{
		final Map<String, String> rate = new HashMap<String, String>();
		XmlHelper.foreach(element, "field", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				rate.put(element.getAttribute("name"), XmlHelper.getElementText(element));
			}
		});
		return rate;
	}

	private String getCourse(Map<String, String> map, CurrencyRateType type) throws GateException
	{
		for (Map.Entry<String, String> entry : map.entrySet())
		{
			if (stringToCurrencyRate.get(entry.getKey()).equals(type))
				return entry.getValue();
		}
		throw new GateException("Не найден курс валюты");
	}

	private BigDecimal getBigDecimalValue(String value)
	{
		return BigDecimal.valueOf(Double.valueOf(value));
	}
}
