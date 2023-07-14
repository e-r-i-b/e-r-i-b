package com.rssl.phizic.business.resources.external;

/**
 * @author Balovtsev
 * @created 26.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class ActiveRurNotVirtualCardFilter extends ActiveNotVirtualCardsFilter
{
	public ActiveRurNotVirtualCardFilter()
	{
		super();
		addFilter(new RURCardFilter());
	}
}
