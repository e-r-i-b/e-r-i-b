package com.rssl.phizic.business.resources.external;

/**
 * @author basharin
 * @ created 05.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveOrArrestedNotVirtualNotCreditNotAdditionalCardFilter extends CardFilterConjunction
{
	public ActiveOrArrestedNotVirtualNotCreditNotAdditionalCardFilter()
	{
		addFilter(new ActiveCardWithArrestedAccountFilter());
		addFilter(new NotVirtualCardsFilter());
		addFilter(new NotCreditCardFilter());
		addFilter(new ClientOwnCardFilter());
	}
}