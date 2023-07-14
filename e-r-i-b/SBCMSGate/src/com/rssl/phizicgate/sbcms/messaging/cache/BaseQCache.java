package com.rssl.phizicgate.sbcms.messaging.cache;

import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Document;

/**
 * @author Egorova
 * @ created 19.12.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class BaseQCache extends MessagesCache
{
	private static final String CARD_NUMBER_PATH = "/WAY4_REQUEST/PAN";
	private List<Class> classes;

	public Serializable getKey(Document request)
	{
		try
		{
			return XmlHelper.selectSingleNode(request.getDocumentElement(), CARD_NUMBER_PATH).getTextContent();
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
			classes.add(Card.class);
		}
		return classes;
	}

	/**
	 * очистить кеш по переданному объекту
	 * @param object объект одного из классов, возращаемых getCacheClasses
	 */
	public void clear(Object object) throws GateException
	{
		Card card;
		if(object instanceof Card)
		{
			card = (Card)object;
			remove(card.getNumber());
		}
		else
		{
			throw new GateException("Попытка очистки кеша по неизвестному классу");
		}
	}
}
