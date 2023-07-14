package com.rssl.phizicgate.rsV51.cache;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gainanov
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class OverdraftInfoQCache extends MessagesCache
{
	private static final String KEY_PATH = "/getOvInfo_q/referenc";

	private List<Class> classes;
	
	protected String getCacheName()
	{
		return OverdraftInfoQCache.class.getName();
	}

	public Serializable getKey(Document request)
	{
		try
		{
			Element element = XmlHelper
					.selectSingleNode(request.getDocumentElement(), KEY_PATH);
			return element.getTextContent();
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
			classes.add(Card.class);
			classes.add(Account.class);
		}
		return classes;
	}

	/**
	 * очистить кеш по переданному объекту
	 * @param object объект одного из классов, возращаемых getCacheClasses
	 */
	public void clear(Object object) throws GateException, GateLogicException
	{
		String accountId = null;
		if(object instanceof Card)
		{
			Card card = (Card) object;
			try {
				BankrollService bankrollService    = GateSingleton.getFactory().service(BankrollService.class);
				Account account = GroupResultHelper.getOneResult(bankrollService.getCardPrimaryAccount(card));
				accountId = account.getId();
			}
			catch (LogicException e)
			{
				throw new GateLogicException(e);
			}
			catch (SystemException e)
			{
				throw new GateException(e);
			}
		}
		else
		{
			if(object instanceof Account)
			{
				Account account = (Account)object;
				accountId = account.getId();
			}
			else
			{
				throw new GateException("Попытка очистки кеша по неизвестному классу");
			}
		}
		if (!StringHelper.isEmpty(accountId))
			remove(accountId);
	}
}
