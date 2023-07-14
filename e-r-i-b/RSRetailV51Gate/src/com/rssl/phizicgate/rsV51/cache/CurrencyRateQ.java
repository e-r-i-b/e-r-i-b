package com.rssl.phizicgate.rsV51.cache;

import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.common.types.CurrencyRate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Gainanov
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateQ extends MessagesCache
{
	private static final String BUY_CURRENCY_KEY = "/getRateCurrency_q/buyCurrency";
	private static final String SALE_CURRENCY_KEY = "/getRateCurrency_q/saleCurrency";
	private static final String SALE_SUM_KEY = "/getRateCurrency_q/saleSum";
	private static final String BUY_SUM_KEY = "/getRateCurrency_q/buySum";
	private static final String DATE_KEY = "/getRateCurrency_q/date";

	private List<Class> classes;
	private static final String SEPARATOR = "|";


	protected String getCacheName()
	{
		return CurrencyRateQ.class.getName();
	}

	public Serializable getKey(Document request)
	{
		try
		{
			Element dateElement = XmlHelper.selectSingleNode(request.getDocumentElement(), DATE_KEY);
			String dateElementTextContent;
			if (dateElement == null) {
				dateElementTextContent = String.format("%1$te.%1$tm.%1$tY", DateHelper.toDate(DateHelper.getCurrentDate()));
			} else {
				dateElementTextContent = dateElement.getTextContent();
			}

			Element buyCurrencyElement = XmlHelper.selectSingleNode(request.getDocumentElement(), BUY_CURRENCY_KEY);
			Element saleCurrencyElement = XmlHelper.selectSingleNode(request.getDocumentElement(), SALE_CURRENCY_KEY);
			Element saleSumElement = XmlHelper.selectSingleNode(request.getDocumentElement(), SALE_SUM_KEY);
			Element buySumElement = XmlHelper.selectSingleNode(request.getDocumentElement(), BUY_SUM_KEY);

			String saleSumElemText = saleSumElement == null ? "x" : saleSumElement.getTextContent();
			String buySumElemText = buySumElement == null ? "x" : buySumElement.getTextContent();

			String element = dateElementTextContent + SEPARATOR;
			if (buyCurrencyElement != null && saleCurrencyElement != null)
			{
				element += buyCurrencyElement.getTextContent() + SEPARATOR +
						   saleCurrencyElement.getTextContent() + SEPARATOR +
						   buySumElemText + SEPARATOR + saleSumElemText + SEPARATOR;
			}
			else
			{
				return null;
			}

			return element;
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении ключа для сообщения:", e);
		}
		return null;
	}

	/**
	 * список классов по которым кешируются данные
	 * @return список классов.
	 */
	public List<Class> getCacheClasses()
	{
		if(classes==null)
		{
			classes = new ArrayList<Class>();
			classes.add(CurrencyRate.class);
		}
		return classes;
	}

	/**
	 * очистить кеш по переданному объекту
	 * @param object объект одного из классов, возращаемых getCacheClasses
	 */
	public void clear(Object object) throws GateException
	{
		if(object instanceof CurrencyRate)
		{
			CurrencyRate rate = (CurrencyRate) object;
			String currOne = rate.getToCurrency().getExternalId();
			String currTwo = rate.getFromCurrency().getExternalId();
			String partKeyOne = currOne + "|" + currTwo;
			String partKeyTwo = currTwo + "|" + currOne;

			List<Object> list = getAllKeys();
			for (Object obj : list)
			{
				String key = (String)obj;
				if(key.indexOf(partKeyOne) != -1 || key.indexOf(partKeyTwo) != -1)
				{
					remove(key);
				}
			}
		}
		else
			throw new GateException("Попытка очистки кеша по неизвестному классу");
	}
}
