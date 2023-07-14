package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Jatsky
 * @ created 25.08.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanPrivateCacheKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = 0;
		if (!StringHelper.isEmpty(params))
			paramNum = Integer.parseInt(params);

		StringBuilder loansKey = new StringBuilder();

		List<Loan> loans = (List<Loan>) args[paramNum];
		Collections.sort(loans, new Comparator<Loan>()
		{
			public int compare(Loan o1, Loan o2)
			{
				return o1.getAccountNumber().compareTo(o2.getAccountNumber());
			}
		});
		for (Loan loan : loans)
		{
			loansKey.append(loan.getAccountNumber());
		}
		return loansKey.toString();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if (result == null || !(result instanceof List))
			return null;

		StringBuilder loansKey = new StringBuilder();

		List<Loan> loans = (List<Loan>) result;
		Collections.sort(loans, new Comparator<Loan>()
		{
			public int compare(Loan o1, Loan o2)
			{
				return o1.getAccountNumber().compareTo(o2.getAccountNumber());
			}
		});
		for (Loan loan : loans)
		{
			loansKey.append(loan.getAccountNumber());
		}
		return loansKey.toString();
	}
}
