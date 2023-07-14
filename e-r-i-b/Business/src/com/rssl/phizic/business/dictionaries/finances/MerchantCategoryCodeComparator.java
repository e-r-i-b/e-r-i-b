package com.rssl.phizic.business.dictionaries.finances;

import java.util.Comparator;

/**
 * Компаратор для сравнения записей справочника MCC-кодов
 * @author Gololobov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class MerchantCategoryCodeComparator implements Comparator<MerchantCategoryCode>
{
	public int compare(MerchantCategoryCode o1, MerchantCategoryCode o2)
	{
		int compareResult = o1.getCode().compareTo(o2.getCode());
		if (compareResult == 0)
			compareResult = compareCategories(o1.getIncomeOperationCategory(), o2.getIncomeOperationCategory());
		if (compareResult == 0)
			compareResult = compareCategories(o1.getOutcomeOperationCategory(), o2.getOutcomeOperationCategory());
		return compareResult;
	}

	private int compareCategories(CardOperationCategory category1, CardOperationCategory category2)
	{
		if (category1 == null && category2 == null)
			return 0;

		if (category1 == null)
			return -1;

		if (category2 == null)
			return 1;

		return category1.getExternalId().compareTo(category2.getExternalId());
	}
}
