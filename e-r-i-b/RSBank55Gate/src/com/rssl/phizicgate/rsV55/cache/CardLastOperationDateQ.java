package com.rssl.phizicgate.rsV55.cache;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizicgate.rsV55.bankroll.CardImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gainanov
 * @ created 29.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class CardLastOperationDateQ extends MessagesCache
{
	private static final String ACC_REF_PATH = "/getCardLastOperationDate_q/accountReferenc";
	private static final String CARD_ID_PATH = "/getCardLastOperationDate_q/cardId";
	private static final String SEPARATOR = "|";
	
	private List<Class> classes;

	protected String getCacheName()
	{
		return CardLastOperationDateQ.class.getName();
	}

	public Serializable getKey(Document request)
	{
		try
		{
			Element accRef = XmlHelper.selectSingleNode(request.getDocumentElement(), ACC_REF_PATH);
			Element cardId = XmlHelper.selectSingleNode(request.getDocumentElement(), CARD_ID_PATH);

			return accRef.getTextContent() + SEPARATOR + cardId.getTextContent();
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
		}
		return classes;
	}

	/**
	 * очистить кеш по переданному объекту
	 * @param object объект одного из классов, возращаемых getCacheClasses
	 */
	public void clear(Object object) throws GateException, GateLogicException
	{
		if(object instanceof Card)
		{
			Card card = (Card) object;
			String accountId = null;
			BankrollService bankrollService    = GateSingleton.getFactory().service(BankrollService.class);
			try
			{
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
			if (!StringHelper.isEmpty(accountId))
				remove(accountId+ SEPARATOR + CardImpl.parseCardId(card.getId()));
		}
		else
			throw new GateException("Попытка очистки кеша по неизвестному классу");
	}
}
