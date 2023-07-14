package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Erkin
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class BankrollHelper
{
	private GateFactory factory;

	/**
	 * ctor
	 * @param factory
	 */
	public BankrollHelper(GateFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * Поиск карт по номерaм.
	 * все карты должны быть у клиента
	 * поиск происходит по идентфикаторам из БД.
	 * @param loginId id логина клиента в СБОЛ
	 * @param cardNumbers номера карт
	 * @return карта
	 */
	public GroupResult<String, Card> getCardsByNumber(Long loginId, String ... cardNumbers)
	{
		BackRefBankrollService backRefBankrollService = factory.service(BackRefBankrollService.class);
		BankrollService bankrollService = factory.service(BankrollService.class);
		List<String> cardIDs = new ArrayList<String>();
		GroupResult<String, Card> result = new GroupResult<String, Card>();
		for (String cardNumber : cardNumbers)
		{
			try
			{
				cardIDs.add(backRefBankrollService.findCardExternalId(loginId, cardNumber));
			}
			catch (GateLogicException e)
			{
				result.putException(cardNumber, e);
			}
			catch (GateException e)
			{
				result.putException(cardNumber, e);
			}
		}
		result.add(bankrollService.getCard(cardIDs.toArray(new String[cardIDs.size()])));
		return result;
	}

	/**
	 * Получить карту по ее номеру (из шины)
	 * (!!доп информация для поиска карты(оффис) берем из клиента!!)
	 * @param client клиент, который или в контексте которого запрашивается информация
	 * @param cardNumber номер карты
	 * @return
	 */
	public Card getCardByNumber(Client client, String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			GroupResult<Pair<String,Office>,Card> result = getCardsByNumber(client, cardNumber);
			return GroupResultHelper.getOneResult(result);
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
	}

	/**
	 * Получить карту по ее номеру (из шины)
	 * (!!доп информация для поиска карты(оффис) берем из клиента!!)
	 * @param client клиент, который или в контексте которого запрашивается информация
	 * @param cardNumbers номер карты
	 * @return
	 */
	public GroupResult<Pair<String,Office>,Card> getCardsByNumber(Client client, String ... cardNumbers)
	{
		BankrollService bankrollService = factory.service(BankrollService.class);
		//используем Array.newInstance потому как не поянтно как подругому получить массив с generic типом
		Pair<String,Office> arroyInfo[] = (Pair<String,Office>[]) Array.newInstance(Pair.class,Array.getLength(cardNumbers));
		int i = 0;
		for (String cardNumber: cardNumbers)
		{
			arroyInfo[i] = new Pair<String,Office>(cardNumber,client.getOffice());
			i++;
		}
		return bankrollService.getCardByNumber(client,arroyInfo);
	}

	/**
	 * Создание составляющих для сообщения для заблокированных продуктов
	 * @param names - список названий закрвтых продуктов
	 * @return мап с частями сообщения (ключ заголовка сообщения, строка имён продуктов, ключ окончания сообщения)
	 */
	public Map<String,String> createBlockedLinkMessage(List<String> names)
	{
	    StringBuilder nameString = new StringBuilder("«");
		nameString.append(StringUtils.join(names, "», «"));
		nameString.append("» ");
		Map<String,String> message = new HashMap<String, String>();
	    if (names.size() == 1)
	    {
		    message.put("captionKey", "message.blocked.caption.single");
		    message.put("bodyKey", "message.blocked.body.single");
	    }
	    else
	    {
		    message.put("captionKey", "message.blocked.caption.multiple");
	        message.put("bodyKey", "message.blocked.body.multiple");
	    }

		message.put("bodyText", nameString.toString());
		return message;
	}
}
