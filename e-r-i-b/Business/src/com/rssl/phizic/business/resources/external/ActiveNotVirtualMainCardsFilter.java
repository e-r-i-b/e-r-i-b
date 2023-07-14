package com.rssl.phizic.business.resources.external;

/**
 * @author Balovtsev
 * @created 08.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class ActiveNotVirtualMainCardsFilter extends ActiveNotVirtualCardsFilter
{
	public ActiveNotVirtualMainCardsFilter()
	{
		super();
		addFilter(new MainCardFilter());
	}
}
