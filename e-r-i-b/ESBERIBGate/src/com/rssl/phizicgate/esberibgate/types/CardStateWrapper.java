package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.bankroll.CardState;

import java.util.Map;
import java.util.HashMap;

/**
 * @author egorova
 * @ created 15.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardStateWrapper
{
	private static final Map<String, CardState> cardState = new HashMap<String, CardState>();

	static
	{
		cardState.put("Blocked", CardState.blocked);
		cardState.put("Active", CardState.active);
		cardState.put("Stoped", CardState.delivery);
	}

	public static CardState getCardType(String way4CardState)
	{
		if (way4CardState == null)
			return null;
		CardState state = cardState.get(way4CardState);
//Временный заглушка. до написания адаптера товарищами из Крок.
// Сейчас все остальные статусы делаем заблокированными, потом необходимо их сделать CardState.unknown
		return (state!=null)?state:CardState.blocked;
	}
}