package com.rssl.phizic.business.resources.external;

/**
 * @author Gainanov
 * @ created 04.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class ActiveCreditAccountFilter extends ActiveAccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		return super.accept(accountLink) && accountLink.getAccount().getCreditAllowed();
	}
}
