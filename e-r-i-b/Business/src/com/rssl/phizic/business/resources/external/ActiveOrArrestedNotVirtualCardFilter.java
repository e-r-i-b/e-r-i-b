package com.rssl.phizic.business.resources.external;

/**
 * ‘ильтр, провер€ющий что карта активна или арестована и не виртуальна
 * @author basharin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveOrArrestedNotVirtualCardFilter extends CardFilterConjunction
{
	public ActiveOrArrestedNotVirtualCardFilter()
	{
		addFilter(new NotVirtualCardsFilter());
		addFilter(new ActiveCardWithArrestedAccountFilter());
	}
}
