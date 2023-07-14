package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.Account;

import java.util.Comparator;

/**
 * @author vagin
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 *  Компаратор вида вклада. Сберегательные-первые.
 */
public class AccountSavingKindComparator implements Comparator<AccountLink>
{
	//Продукт с типом 61 является сберегательным счетом
	private static final long ACCOUNT_TYPE_SBEREGATELNYI = 61L;

	public int compare(AccountLink o1, AccountLink o2)
	{
		Account account1 = o1.getAccount();
		Account account2 = o2.getAccount();
		if (account1.getKind().equals(ACCOUNT_TYPE_SBEREGATELNYI))
		{
			if (account2.getKind().equals(ACCOUNT_TYPE_SBEREGATELNYI))
				return 0;
			return -1;
		}
		else if (account2.getKind().equals(ACCOUNT_TYPE_SBEREGATELNYI))
		{
			return 1;
		}
		return 0;
	}
}
