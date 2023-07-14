package com.rssl.phizicgate.sbrf.ws.cache;

import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import org.w3c.dom.Document;

/**
 * @author egorova
 * @ created 08.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class BillingDemandQCache  extends MessagesCache
{
	private static final String ACCOUNT_NUMBER_PATH = "/billingDemand_q/account";
	private static final String DEMAND_TYPE_PATH = "/billingDemand_q/copying";
	private static final String DEMAND_START_DATE_PATH = "/billingDemand_q/startDate";
	private static final String DEMAND_END_DATE_PATH = "/billingDemand_q/endDate";

	private static final String SEPARATOR = " | ";

	private List<Class> classes;

	public List<Class> getCacheClasses()
	{
		if(classes==null)
		{
			classes = new ArrayList<Class>();
			classes.add(Account.class);
		}
		return classes;
	}

	public void clear(Object object) throws GateException
	{
		if(object instanceof Account)
		{
			Account account = (Account)object;
			String partKey = account.getNumber();

			List<Object> list = getAllKeys();
			for (Object o : list)
			{
				String key = (String)o;
				if(key.indexOf(partKey) != -1)
				{
					remove(key);
				}
			}
		}
		else
		{
			throw new GateException("Попытка очистки кеша по неизвестному классу");
		}
	}

	public Serializable getKey(Document request)
	{
		try
		{
			return XmlHelper.selectSingleNode(request.getDocumentElement(), DEMAND_TYPE_PATH).getTextContent() + SEPARATOR
						+ XmlHelper.selectSingleNode(request.getDocumentElement(), ACCOUNT_NUMBER_PATH).getTextContent() + SEPARATOR
						+ XmlHelper.selectSingleNode(request.getDocumentElement(), DEMAND_START_DATE_PATH).getTextContent() + SEPARATOR
						+ XmlHelper.selectSingleNode(request.getDocumentElement(), DEMAND_END_DATE_PATH).getTextContent() + SEPARATOR;
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении ключа для сообщения:", e);
			return null;
		}
	}


	protected String getCacheName ()
	{
		return BillingDemandQCache.class.getName();
	}
}
