package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.utils.MockHelper;

/**
 * @author Egorova
 * @ created 08.02.2008
 * @ $Author$
 * @ $Revision$
 */
public class ActiveForeignCurrencyAccountFilter extends ForeignCurrencyAccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		if(!MockHelper.isMockObject(accountLink.getAccount()))
		{
			return super.accept(accountLink)
					&& accountLink.getAccount().getAccountState() == AccountState.OPENED;
		}
		else return false;
	}
}
