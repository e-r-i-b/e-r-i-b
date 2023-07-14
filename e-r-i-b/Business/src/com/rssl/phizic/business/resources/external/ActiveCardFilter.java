package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;

/**
 * Фильтр, отбирающий активные карты с неарестованным карточным счетом
 * @author hudyakov
 * @ created 18.02.2008
 * @ $Author$
 * @ $Revision$
 */
public class ActiveCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return CardState.active.equals(card.getCardState()) && card.getCardAccountState() != AccountState.ARRESTED;
	}

	public boolean accept(CardLink link)
	{
		return accept(link.getCard());
	}
}
