package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.AccountState;

import java.util.Comparator;

/**
 * @author kuzmin
 * @ created 10.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AccountsComparator extends ProductComparator implements Comparator<Pair<AccountLink, AccountAbstract>>
{
	public int compare(Pair<AccountLink, AccountAbstract> o1, Pair<AccountLink, AccountAbstract> o2)
	{
		Account account1 = o1.getFirst().getAccount();
		Account account2 = o2.getFirst().getAccount();

		if (account1.getAccountState() == null || account2.getAccountState() == null)
		{
			int result = compareToNull(account1.getAccountState(), account2.getAccountState());
			if (result != 0)
				return result;
		}

		//приоритет у счета со статусом "открыт"
		if (AccountState.OPENED == account1.getAccountState() && AccountState.OPENED != account2.getAccountState())
		{
			return -1;
		}

		if (AccountState.OPENED != account1.getAccountState() && AccountState.OPENED == account2.getAccountState())
		{
			return 1;
		}

		AccountAbstract abs1 = o1.getSecond();
		AccountAbstract abs2 = o2.getSecond();

		//приоритет у счета, по которому получили выписку
		if (abs1 == null || abs2 == null)
		{
			int result = compareToNull(abs2,  abs1);
			if( result != 0)
				return  result;
		}
		else
		{
			//приоритет у счета, по которому есть операции
			if(abs1.getTransactions() == null || abs2.getTransactions()== null)
			{
				int result = compareToNull(abs2.getTransactions(), abs1.getTransactions());
				if(result != 0)
					return  result;
			}
			else
			{
				if (!abs1.getTransactions().isEmpty() && !abs2.getTransactions().isEmpty())
				{
					//приоритетнее счет, по которому была совершена последняя операция в более позднюю дату
					int result = o2.getSecond().getTransactions().get(0).getDate().compareTo(o1.getSecond().getTransactions().get(0).getDate());
					if (result != 0)
						return result;
				}
				if (abs1.getTransactions().isEmpty() && !abs2.getTransactions().isEmpty())
				{
					return 	1;
				}
				if (!abs1.getTransactions().isEmpty() && abs2.getTransactions().isEmpty())
				{
					return 	-1;
				}
			}
		}

		if (account1.getBalance() == null || account2.getBalance() == null)
		{
			int result = compareToNull(account1.getBalance(), account2.getBalance());
			if (result != 0)
				return result;
		}
		else
		{
			//приоритетнее счет с наибольшим остатком
			int result = account2.getBalance().compareTo(account1.getBalance());
			if (result != 0)
				return  result;
		}

		if (account1.getOpenDate() == null || account2.getOpenDate() == null)
		{
			return compareToNull(account1.getOpenDate(), account2.getOpenDate());
		}

		//приоритетнее счет с более поздней датой открытия
		return account2.getOpenDate().compareTo(account1.getOpenDate());
	}
}
