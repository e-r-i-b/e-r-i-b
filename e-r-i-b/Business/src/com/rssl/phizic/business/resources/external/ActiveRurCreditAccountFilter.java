package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.utils.MockHelper;

/**
 * @author Gainanov
 * @ created 04.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class ActiveRurCreditAccountFilter extends ActiveRurAccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		if(!MockHelper.isMockObject(accountLink.getAccount()))
		{
			return super.accept(accountLink) && accountLink.getAccount().getCreditAllowed();
		}
		else return false;
	}
}
