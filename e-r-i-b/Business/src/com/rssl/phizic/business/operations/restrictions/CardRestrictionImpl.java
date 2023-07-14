package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.gate.bankroll.Card;

import java.util.Set;

/**
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class CardRestrictionImpl implements CardRestriction
{
	Set<String> cards;

	public CardRestrictionImpl(Set<String> cards)
	{
		this.cards = cards;
	}

	public boolean accept(Card card)
	{
		return cards.contains(card.getId());
	}

}