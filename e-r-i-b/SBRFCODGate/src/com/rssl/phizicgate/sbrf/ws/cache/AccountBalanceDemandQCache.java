package com.rssl.phizicgate.sbrf.ws.cache;

import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Document;

/**
 * @author Kosyakov
 * @ created 30.01.2007
 * @ $Author: kosyakov $
 * @ $Revision: 3337 $
 */
public class AccountBalanceDemandQCache extends MessagesCache
{
	private static final String ACCOUNT_NUMBER_PATH = "/accountBalanceDemand_q/account";
	private List<Class> classes;

	protected String getCacheName ()
	{
		return AccountBalanceDemandQCache.class.getName();
	}

	public Serializable getKey(Document request)
	{
		try
		{
			return XmlHelper.selectSingleNode(request.getDocumentElement(), ACCOUNT_NUMBER_PATH).getTextContent();
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении ключа для сообщения:", e);
			return null;
		}
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
			classes.add(Account.class);
		}
		return classes;
	}

	/**
	 * очистить кеш по переданному объекту
	 * @param object объект одного из классов, возращаемых getCacheClasses
	 */
	public void clear(Object object) throws GateException
	{
		Account account;
		if(object instanceof Account)
		{
			account = (Account)object;
			remove(account.getNumber());
		}
		else
		{
			throw new GateException("Попытка очистки кеша по неизвестному классу");
		}
	}
}
