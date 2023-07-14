package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;

/**
 * Фильтр, отбирающий активные карты без учета счета карты (в отличие от ActiveCardFilter)
 * @author basharin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveCardWithArrestedAccountFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return card.getCardState() == CardState.active;
	}

	public boolean accept(CardLink link)
	{
		return accept(link.getCard());
	}
}