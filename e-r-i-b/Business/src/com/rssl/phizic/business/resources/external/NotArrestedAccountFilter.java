package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;

/** ������, ����������� �������� �� ���� �����������
 * @author akrenev
 * @ created 26.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class NotArrestedAccountFilter implements AccountFilter
{
	/**
	 * @param account - ����������� ����
	 * @return true  - ���� �� ���������
	 *         false - ���� ���������
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
