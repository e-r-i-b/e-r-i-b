package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.operations.restrictions.CardRestriction;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;

/**
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author$
 * @ $Revision$
 */
@PublicDefaultCreatable
public class NullCardRestriction implements CardRestriction
{
	public boolean accept(Card card)
	{
		return true;
	}
}