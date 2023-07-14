package com.rssl.phizicgate.rsV51.cache;

import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Gainanov
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class ClientDepositInfoQ extends MessagesCache
{
	private static final String KEY_PATH = "/getClientDepositInfo_q/deposit/referenc";
	private static final String CLIENT_KEY = "/getClientDepositInfo_q/clientId";

	private List<Class> classes;
	private static final String SEPARATOR = " | ";

	protected String getCacheName()
	{
		return ClientDepositInfoQ.class.getName();
	}

	public Serializable getKey(Document request)
	{
		try
		{
			Element clientElement = XmlHelper.selectSingleNode(request.getDocumentElement(), CLIENT_KEY);
			NodeList elements = XmlHelper.selectNodeList(request.getDocumentElement(), KEY_PATH);
			if (elements != null)
			{
				String element = clientElement.getTextContent() + SEPARATOR;
				for (int i=0; i<elements.getLength()-1; i++)
							element+=elements.item(i).getTextContent() + SEPARATOR;
				element+=elements.item(elements.getLength()-1).getTextContent();
				return element;
			}
			else return null;
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
			classes.add(Deposit.class);
		}
		return classes;
	}

	/**
	 * очистить кеш по переданному объекту
	 * @param object объект одного из классов, возращаемых getCacheClasses
	 */
	public void clear(Object object) throws GateException
	{
		if(object instanceof Deposit)
		{
			Deposit deposit = (Deposit) object;
			String partKey = deposit.getId();

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
			throw new GateException("Попытка очистки кеша по неизвестному классу");

	}
}
