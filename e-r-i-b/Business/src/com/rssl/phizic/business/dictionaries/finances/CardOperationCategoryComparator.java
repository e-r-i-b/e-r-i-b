package com.rssl.phizic.business.dictionaries.finances;

import java.util.Comparator;

/**
 * Компаратор для сравнения записей справочника категорий.
 *
 * @author Gololobov
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationCategoryComparator implements Comparator<CardOperationCategory>
{
	//Метод вызывается при сравнении записей в ресурсе с записью из базы - определение что конкретно поменялось.
	//Соответствующие записи определяются по "SynchKey"
	public int compare(CardOperationCategory o1, CardOperationCategory o2)
	{
		int compareResult = o1.getExternalId().compareTo(o2.getExternalId());
		if (compareResult == 0)
		  compareResult = o1.getName().trim().compareToIgnoreCase(o2.getName().trim());
		if (compareResult == 0)
		  compareResult = o1.isIncome() != o2.isIncome() ||
						  o1.getIsDefault() != o2.getIsDefault() ||
						  o1.isCash() != o2.isCash() ||
				          o1.isCashless() != o2.isCashless()||
				          o1.isIncompatibleOperationsAllowed() != o2.isIncompatibleOperationsAllowed() ? 1:0;

		return compareResult;
	}
}
