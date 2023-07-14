package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;
import java.util.List;

/**
 * @author osminin
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 *  композер для методов, получающих в качестве параметров лист карт
 */
public class ListCardCacheKeyComposer extends AbstractCacheKeyComposer
{
	/**
	 * Сформировать ключ
	 * @param args аргументы вызова метода
	 * @param params параметры для работа композера
	 * @return ключ
	 */
	public String getKey(Object[] args, String params)
	{
		int paramNum = 0;
		if (!StringHelper.isEmpty(params))
			paramNum = Integer.parseInt(params);

		StringBuilder cardsKey = new StringBuilder();

		List<Card> cards = (List<Card>) args[paramNum];
		for (Card card : cards)
		{
			cardsKey.append(card.getNumber());
		}
		return cardsKey.toString();
	}

	/**
	 * Формирует ключ аналогично getKey, но из результата выполнения запроса.
	 * @param result результат выполнения метода
	 * @param params дополнительные параметры
	 * @return ключ, или null если преобразование не может быть выполнено.
	 */
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if (result == null || !(result instanceof List))
			return null;

		StringBuilder cardsKey = new StringBuilder();

		List<Card> cards = (List<Card>) result;
		for (Card card : cards)
		{
			cardsKey.append(card.getNumber());
		}
		return cardsKey.toString();
	}
}
