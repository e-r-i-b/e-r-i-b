package com.rssl.phizic.business.resources.external;

/**
 * @author Balovtsev
 * @created 26.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class ActiveRurNotCreditNotVirtualCardFilter extends ActiveRurNotVirtualCardFilter
{
	public ActiveRurNotCreditNotVirtualCardFilter()
	{
		super();
		addFilter(new NotCreditCardFilter());
	}
}
