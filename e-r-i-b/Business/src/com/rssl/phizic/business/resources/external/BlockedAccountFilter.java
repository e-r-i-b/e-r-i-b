package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.utils.MockHelper;

/**
 * ������, ���������� �������� ������ � ������, � ������� �������� ������.
 * @author miklyaev
 * @ created 25.12.13
 * @ $Author$
 * @ $Revision$
 */

public class BlockedAccountFilter extends ClosedAccountFilter
{
	public boolean accept(Account account) throws TemporalBusinessException
		{
			return super.accept(account) || account.getAccountState() == AccountState.LOST_PASSBOOK;
		}
}
