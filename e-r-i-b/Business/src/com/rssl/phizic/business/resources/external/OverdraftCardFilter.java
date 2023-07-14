package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;

/**
 * Фильтр овердрафтных карт
 * @author komarov
 * @ created 22.04.2013 
 * @ $Author$
 * @ $Revision$
 */

public class OverdraftCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return CardType.overdraft == card.getCardType();
	}
}
