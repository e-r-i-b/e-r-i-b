package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.departments.TerBankDetails;
import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * Компаратор для сравнения записей справочника ТБ
 * @author Pankin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class TBDetailsComparator implements Comparator<TerBankDetails>
{
	public int compare(TerBankDetails o1, TerBankDetails o2)
	{
		int compareResult = StringHelper.compare(o1.getCode(), o2.getCode());
		if (compareResult == 0)
		{
			compareResult = StringHelper.compare(o1.getOffCode(), o2.getOffCode());
		}
		if (compareResult == 0)
		{
			compareResult = StringHelper.compare(o1.getName(), o2.getName());
		}
		if (compareResult == 0)
		{
			compareResult = StringHelper.compare(o1.getBIC(), o2.getBIC());
		}
		if (compareResult == 0)
		{
			compareResult = StringHelper.compare(o1.getOKPO(), o2.getOKPO());
		}
		if (compareResult == 0)
		{
			compareResult = StringHelper.compare(o1.getAddress(), o2.getAddress());
		}
		return compareResult;
	}
}
