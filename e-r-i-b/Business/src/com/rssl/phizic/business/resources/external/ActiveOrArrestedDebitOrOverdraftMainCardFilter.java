package com.rssl.phizic.business.resources.external;

/**
 * Фильтр для получения активных или арестованных основных дебетовых/овердрафтных карт
 * @author gladishev
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ActiveOrArrestedDebitOrOverdraftMainCardFilter extends CardFilterConjunction
{
	public ActiveOrArrestedDebitOrOverdraftMainCardFilter()
	{
		addFilter(new ActiveCardWithArrestedAccountFilter());
		addFilter(new DebitOrOverdraftCardFilter());
		addFilter(new MainCardFilter());
	}
}
