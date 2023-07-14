package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;

/**
 * @author osminin
 * @ created 13.02.2009
 * @ $Author$
 * @ $Revision$
 */

public class LossPassbookFilter extends ActiveOrArrestedAccountFilter
{
	public boolean accept(Account account) throws TemporalBusinessException
	{
		return super.accept(account) &&
				account.getAccountState() != AccountState.LOST_PASSBOOK &&
				account.getPassbook() != null &&
				account.getPassbook();
	}
}
