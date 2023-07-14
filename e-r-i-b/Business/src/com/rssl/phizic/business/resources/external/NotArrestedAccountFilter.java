package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;

/** ‘ильтр, провер€юший €вл€етс€ ли счет арестованым
 * @author akrenev
 * @ created 26.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class NotArrestedAccountFilter implements AccountFilter
{
	/**
	 * @param account - провер€емый счет
	 * @return true  - счет не арестован
	 *         false - счет арестован
	 */
	public boolean accept(Account account)
	{
		return account.getAccountState() != AccountState.ARRESTED;
	}

	public boolean accept(AccountLink accountLink)
	{
		return accept(accountLink.getAccount());
	}
}
