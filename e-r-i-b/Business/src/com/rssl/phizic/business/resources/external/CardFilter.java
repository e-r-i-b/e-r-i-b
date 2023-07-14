package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.gate.bankroll.Card;
import org.apache.commons.collections.Predicate;

/**
 * @author hudyakov
 * @ created 18.02.2008
 * @ $Author$
 * @ $Revision$
 */
public interface CardFilter extends Restriction, Predicate
{
	    boolean accept(Card card);
}
