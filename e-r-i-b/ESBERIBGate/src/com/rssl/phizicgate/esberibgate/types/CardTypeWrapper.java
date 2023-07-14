package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.bankroll.CardType;

import java.util.Map;
import java.util.HashMap;

/**
 * @author egorova
 * @ created 11.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardTypeWrapper
{
	private static final Map<String, CardType> cardType = new HashMap<String, CardType>();
	private static final Map<CardType, String> cardTypeForRequest = new HashMap<CardType, String>();

	static
	{
		cardType.put("CC", CardType.credit);
		cardType.put("DC", CardType.debit);
		cardType.put("OV", CardType.overdraft);
		cardTypeForRequest.put(CardType.credit, "CC");
		cardTypeForRequest.put(CardType.debit, "DC");
		cardTypeForRequest.put(CardType.overdraft, "OV");
	}

	public static CardType getCardType(String way4CardType)
	{
		if (way4CardType == null)
			return null;
		return cardType.get(way4CardType);
	}

	public static Map<String, CardType> getCardType()
	{
		return cardType;
	}

	/**
	 * Метод получения типа карты для составления запроса.
	 * @param type тип карты типа CardType
	 * @return принимаемое по спецификации занчение типа карты.
	 */
	public static String getCardTypeForRequest(CardType type)
	{
		return cardTypeForRequest.get(type);
	}
}