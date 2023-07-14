package com.rssl.phizic.business.resources.external;

/**
 * Фильтр для получения активных основных дебетовых/овердрафтных карт
 * @author Rtischeva
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 */
public class ActiveDebitOrOverdraftMainCardFilter extends CardFilterConjunction
{
	public ActiveDebitOrOverdraftMainCardFilter()
	{
		addFilter(new ActiveCardFilter());
		addFilter(new DebitOrOverdraftCardFilter());
		addFilter(new MainCardFilter());
	}
}
