package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.utils.MockHelper;

/**
 * ‘ильтр, провер€ющий что счет арестован или активен
 * @author basharin
 * @ created 05.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ActiveOrArrestedAccountFilter implements AccountFilter
{
	public boolean accept(Account account) throws TemporalBusinessException
	{
		if (MockHelper.isMockObject(account))
			throw new TemporalBusinessException("ќшибка при получении информации по счету є" + account.getNumber());

		return account.getAccountState() == AccountState.OPENED || account.getAccountState() == AccountState.LOST_PASSBOOK || account.getAccountState() == AccountState.ARRESTED;
	}

	public boolean accept(AccountLink accountLink)
	{
		try
		{
			return accept(accountLink.getAccount());
		}
		catch (TemporalBusinessException e)
		{
			return false;
		}
	}
}
