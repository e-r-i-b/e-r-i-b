package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;

/**
 * @author hudyakov
 * @ created 19.02.2008
 * @ $Author$
 * @ $Revision$
 */
public class NullCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return true;
	}
}
