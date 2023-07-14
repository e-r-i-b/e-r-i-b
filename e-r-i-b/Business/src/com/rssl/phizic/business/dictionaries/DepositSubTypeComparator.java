package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.deposits.generated.DepositDictionaryElement;

import java.util.Comparator;

/**
 * Сравнить подвиды вклада (пришедшие из справочника)
 *
 * @author EgorovaA
 * @ created 02.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepositSubTypeComparator implements Comparator<DepositDictionaryElement>
{
	public int compare(DepositDictionaryElement o1, DepositDictionaryElement o2)
	{
		int typeResult = o1.getId().compareTo(o2.getId());
		if (typeResult != 0)
			return typeResult;

		int dateResult = o1.getInterestDateBegin().compareTo(o1.getInterestDateBegin());
		if (dateResult != 0)
			return dateResult;

		int pensionResult = Boolean.valueOf(o1.isPension()).compareTo(Boolean.valueOf(o2.isPension()));
		if (pensionResult != 0)
			return pensionResult;

		int minBalanceResult = o1.getMinBalance().compareTo(o2.getMinBalance());
		if (minBalanceResult != 0)
			return minBalanceResult;

		return 0;
	}
}
