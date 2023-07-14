package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.ima.IMAccountState;

import java.util.Comparator;

/**
 * @author kuzmin
 * @ created 11.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class IMAComparator  extends ProductComparator  implements Comparator<Pair<IMAccountLink, IMAccountAbstract>>
{
	public int compare(Pair<IMAccountLink,IMAccountAbstract> o1, Pair<IMAccountLink,IMAccountAbstract> o2)
	{
		IMAccount account1 = o1.getFirst().getImAccount();
		IMAccount account2 = o2.getFirst().getImAccount();

		if (account1.getState() == null || account2.getState() == null)
		{
			int result = compareToNull(account1.getState(), account2.getState());
			if (result != 0)
				return result;
		}

		//приоритетнее омс со статусом "открыт"
		if (IMAccountState.opened == account1.getState() && IMAccountState.opened != account2.getState())
		{
			return -1;
		}

		if (IMAccountState.opened != account1.getState() && IMAccountState.opened == account2.getState())
		{
			return 1;
		}

		IMAccountAbstract abs1 = o1.getSecond();
		IMAccountAbstract abs2 = o2.getSecond();
		if (abs1 == null || abs2 == null)
		{
			int result = compareToNull(abs2, abs1);
			if(result != 0)
			{
				return result;
			}
		}
		else
		{
			//приоритет у счета, по которому есть операции
			if(abs1.getTransactions() == null || abs2.getTransactions()== null)
			{
				int result = compareToNull(abs2.getTransactions(),  abs1.getTransactions());
				if(result != 0)
				{
					return result;
				}
			}
			else
			{
				//приоритетнее омс, по которому была совершена последняя операция в более позднюю дату
				if (!abs1.getTransactions().isEmpty() && !abs2.getTransactions().isEmpty())
				{
					int result = abs2.getTransactions().get(0).getDate().compareTo(abs1.getTransactions().get(0).getDate());
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
			//приоритетнее омс с наибольшим остатком
			int result = account2.getBalance().compareTo(account1.getBalance());
			if (result != 0)
			{
				return result;
			}
		}

		if (account1.getOpenDate() == null || account2.getOpenDate() == null)
		{
			return compareToNull(account1.getOpenDate(), account2.getOpenDate());
		}

		//приоритетнее осм с более поздней датой открытия
		return account2.getOpenDate().compareTo(account1.getOpenDate());
	}
}
