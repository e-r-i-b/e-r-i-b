package com.rssl.phizic.business.resources.external;

/**
 * @author Balovtsev
 * @created 26.01.2011
 * @ $Author$
 * @ $Revision$
 * 
 * Фильтр применяется когда необходимо получить карты с статусом активная, исключая ситуации,
 * когда карта является кредитной или виртуальной.
 */

public class ActiveNotVirtualNotCreditCardFilter extends ActiveNotVirtualCardsFilter
{

	public ActiveNotVirtualNotCreditCardFilter()
	{
		super();
		addFilter(new NotCreditCardFilter());
	}
}
