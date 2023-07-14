package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;

/**
 * @author basharin
 * @ created 11.09.14
 * @ $Author$
 * @ $Revision$
 */

public class NotArrestedCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return card.getCardAccountState() != AccountState.ARRESTED;
	}
}