package com.rssl.phizic.business.dictionaries.tariffPlan;

import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * @author EgorovaA
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 */
public class TariffPlanComparator implements Comparator<TariffPlanConfig>
{
	public int compare(TariffPlanConfig tariffPlan1, TariffPlanConfig tariffPlan2)
	{
		if (tariffPlan1 == null && tariffPlan2 == null)
			return 0;

		if (tariffPlan1 == null || tariffPlan2 == null)
			return -1;

		int compareResult = tariffPlan1.getCode().compareTo(tariffPlan2.getCode());
		if(compareResult != 0)
			return compareResult;

		compareResult = StringHelper.compare(tariffPlan1.getName(), tariffPlan2.getName());
		if(compareResult != 0)
			return compareResult;

		compareResult = DateHelper.nullSafeCompare(tariffPlan1.getDateBegin(), tariffPlan2.getDateBegin());
		if(compareResult != 0)
			return compareResult;

		compareResult = DateHelper.nullSafeCompare(tariffPlan1.getDateEnd(), tariffPlan2.getDateEnd());
		if(compareResult != 0)
			return compareResult;

		compareResult = Boolean.valueOf(tariffPlan1.isState()).compareTo(Boolean.valueOf(tariffPlan2.isState()));
		if(compareResult != 0)
			return compareResult;

		return 0;
	}
}
