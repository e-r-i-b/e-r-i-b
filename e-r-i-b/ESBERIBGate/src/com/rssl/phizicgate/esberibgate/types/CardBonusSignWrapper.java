package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.bankroll.CardBonusSign;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vagin
 * @ created 20.08.13
 * @ $Author$
 * @ $Revision$
 */
public class CardBonusSignWrapper
{
	private static final Map<String, CardBonusSign> cardBonusSign = new HashMap<String, CardBonusSign>();
	private static final Map<CardBonusSign, String> cardBonusSignForRequest = new HashMap<CardBonusSign, String>();

	static
	{
		cardBonusSign.put("A", CardBonusSign.A);
		cardBonusSign.put("G", CardBonusSign.G);
		cardBonusSign.put("M", CardBonusSign.M);
		cardBonusSign.put("Y", CardBonusSign.Y);
		cardBonusSign.put("Z", CardBonusSign.Z);
		cardBonusSign.put("O", CardBonusSign.O);
		cardBonusSignForRequest.put(CardBonusSign.A, "A");
		cardBonusSignForRequest.put(CardBonusSign.G, "G");
		cardBonusSignForRequest.put(CardBonusSign.M, "M");
		cardBonusSignForRequest.put(CardBonusSign.Y, "Y");
		cardBonusSignForRequest.put(CardBonusSign.Z, "Z");
		cardBonusSignForRequest.put(CardBonusSign.O, "O");
	}

	public static CardBonusSign getCardBonusSign(String way4CardBonusSign)
	{
		if (way4CardBonusSign == null)
			return null;
		return cardBonusSign.get(way4CardBonusSign);
	}

	public static Map<String, CardBonusSign> getCardBonusSign()
	{
		return cardBonusSign;
	}

	/**
	 * ћетод получени€ принадлежности карты к бонусной программе дл€ составлени€ запроса.
	 * @param type принадлежность к бонусной программе карты типа CardBonusSign
	 * @return принимаемое по спецификации занчение типа карты.
	 */
	public static String getCardBonusSingForRequest(CardBonusSign type)
	{
		return cardBonusSignForRequest.get(type);
	}
}
