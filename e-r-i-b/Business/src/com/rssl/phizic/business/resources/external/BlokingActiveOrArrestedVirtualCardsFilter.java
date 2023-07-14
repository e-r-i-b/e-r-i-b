package com.rssl.phizic.business.resources.external;

/**
 * @author basharin
 * @ created 06.09.14
 * @ $Author$
 * @ $Revision$
 */

public class BlokingActiveOrArrestedVirtualCardsFilter extends CardFilterConjunction
{
	public BlokingActiveOrArrestedVirtualCardsFilter()
	{
		addFilter(new ActiveCardWithArrestedAccountFilter());
		addFilter(new BlokingVirtualCardFilter());
	}
}