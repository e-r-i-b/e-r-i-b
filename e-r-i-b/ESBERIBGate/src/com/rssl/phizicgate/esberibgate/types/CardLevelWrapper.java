package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.bankroll.CardLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vagin
 * @ created 20.08.13
 * @ $Author$
 * @ $Revision$
 */
public class CardLevelWrapper
{
	private static final Map<String, CardLevel> cardLevel = new HashMap<String, CardLevel>();
	private static final Map<CardLevel, String> cardLevelForRequest = new HashMap<CardLevel, String>();

	static
	{
		for (CardLevel level : CardLevel.values())
		{
			cardLevel.put(level.name(), level);
			cardLevelForRequest.put(level, level.name());
		}
	}

	public static CardLevel getCardLevel(String way4CardLevel)
	{
		if (way4CardLevel == null)
			return null;
		return cardLevel.get(way4CardLevel);
	}

	public static Map<String, CardLevel> getCardLevel()
	{
		return cardLevel;
	}

	/**
	 * Метод получения вида карты для составления запроса.
	 * @param level вид карты типа CardLevel
	 * @return принимаемое по спецификации занчение типа карты.
	 */
	public static String getCardLevelForRequest(CardLevel level)
	{
		return cardLevelForRequest.get(level);
	}
}
