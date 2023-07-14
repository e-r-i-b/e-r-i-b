package com.rssl.phizic.business.finances;

import java.util.Comparator;

/**
 * Сортировщик операций по дате
 *
 * @author lepihina
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class BalanceByOperationsComparator implements Comparator<BalanceByOperations>
{
	public int compare(BalanceByOperations operations1, BalanceByOperations operations2)
	{
		return operations1.getDate().compareTo(operations2.getDate());
	}
}