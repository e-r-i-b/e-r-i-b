package com.rssl.phizic.business.resources.external;

/**
 * @author krenev
 * @ created 01.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ActiveRurCardFilter extends CardFilterConjunction
{
	public ActiveRurCardFilter()
	{
		addFilter(new ActiveCardFilter());
		addFilter(new RURCardFilter());
	}
}
