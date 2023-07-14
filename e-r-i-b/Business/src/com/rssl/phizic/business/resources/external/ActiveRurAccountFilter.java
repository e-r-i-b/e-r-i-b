package com.rssl.phizic.business.resources.external;

/**
 * @author Egorova
 * @ created 08.02.2008
 * @ $Author$
 * @ $Revision$
 */
public class ActiveRurAccountFilter extends RURAccountFilter
{
	public boolean accept(AccountLink accountLink)
	{

		return (new ActiveAccountFilter()).accept(accountLink) && super.accept(accountLink);
	}
}
