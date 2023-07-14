package com.rssl.phizic.business.resources.external;

/**
 * @author osminin
 * @ created 16.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class ActiveRurNotCreditCardFilter extends CardFilterConjunction
{
	public ActiveRurNotCreditCardFilter()
	{
		addFilter(new ActiveRurCardFilter());
		addFilter(new NotCreditCardFilter());
	}
}
