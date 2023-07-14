package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.gate.bankroll.TransactionBase;

import java.util.Comparator;

/**
 * @author Rydvanskiy
 * @ created 01.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class TransactionComparator implements Comparator<TransactionBase>
{
	public static final Integer ASC = 1;
	public static final Integer DESC = -1;
	private Integer order;

	public TransactionComparator (Integer order) {
	  this.order = order;
	}

	public TransactionComparator () {
		this(ASC);
	}

	public int compare(TransactionBase transaction1, TransactionBase transaction2)
	{
		if (transaction1 == null || transaction2 == null)
			return compareToNull(transaction1, transaction2)*order;
	    return transaction2.getDate().compareTo(transaction1.getDate())*order;
	}

	private int compareToNull(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		if (o1 == null && o2 != null)
		{
			return -1;
		}
		if (o1 != null && o2 == null)
		{
			return 1;
		}
		throw new IllegalArgumentException("Хотя бы один из парамектров должен быть null");
	}
}
