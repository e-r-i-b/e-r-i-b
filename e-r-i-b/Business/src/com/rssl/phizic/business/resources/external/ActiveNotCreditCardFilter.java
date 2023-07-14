package com.rssl.phizic.business.resources.external;

/**
 * @author gladishev
 * @ created 06.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class ActiveNotCreditCardFilter extends CardFilterConjunction
{
	public ActiveNotCreditCardFilter()
	{
		addFilter(new ActiveCardFilter());
		addFilter(new NotCreditCardFilter());
	}
}
