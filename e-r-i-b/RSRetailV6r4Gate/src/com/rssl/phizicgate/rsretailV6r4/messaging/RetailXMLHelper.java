package com.rssl.phizicgate.rsretailV6r4.messaging;

import org.w3c.dom.Element;

import java.util.Calendar;
import java.text.ParseException;
import java.math.BigDecimal;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.Currency;

/**
 * @author Omeliyanchuk
 * @ created 15.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class RetailXMLHelper
{
	/**
	 *
	 * @param root родительский элемент
	 * @param tagName  имя считываемого
	 * @return значение тега
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public static Calendar getElementDate(Element root, String tagName) throws GateException
	{
		String date = getElementValue(root, tagName);
		Calendar resultDate = null;
		if (date != null && !"".equals(date))
		{
			try
			{
				resultDate = DateHelper.parseCalendar(date);
			}
			catch (ParseException e)
			{
				throw new GateException(e);
			}
		}
		return resultDate;
	}

	public static Money getElementMoney(Element root, String tagName, Currency curr)
	{
		Money resultMoney = null;
		String valueParam = getElementValue(root, tagName);
		if (valueParam != null)
		{
			valueParam = valueParam.replace(",", ".");
			if ((curr != null) && !"".equals(valueParam))
			{
				resultMoney = new Money( new BigDecimal(valueParam), curr);
			}
		}
		return resultMoney;
	}

	/**
	 *
	 * @param root родительский элемент
	 * @param tagName имя тэга, значение которого получается
	 * @return  строковое значение элемента, null, если элемент пуст
	 */
	public static String getElementValue(Element root, String tagName)
	{
		String value = XmlHelper.getSimpleElementValue(root, tagName);

		return (value == null || value.length()==0? null : value);
	}

	public static Long getElementLong(Element root, String tagName)
	{
		String value = getElementValue(root, tagName);

		if(value == null || value.length()==0)
			return null;

		return Long.parseLong(value);
	}	
}
