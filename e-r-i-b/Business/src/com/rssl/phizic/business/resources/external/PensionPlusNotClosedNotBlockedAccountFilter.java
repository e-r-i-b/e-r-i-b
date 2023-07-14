package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.utils.MockHelper;

/**
 * Пенсионный Плюс (вид 34), кроме закрытых и заблокированных
 * @author Jatsky
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PensionPlusNotClosedNotBlockedAccountFilter implements AccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		if (!MockHelper.isMockObject(accountLink.getAccount()))
		{
			return accountLink.getAccount().getKind() == 34L && accountLink.getAccount().getAccountState()!= AccountState.CLOSED && accountLink.getAccount().getAccountState()!=AccountState.LOST_PASSBOOK;
		}
		else
			return false;
	}
}
