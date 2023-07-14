package com.rssl.phizic.business.dictionaries.ageRequirement;

import com.rssl.phizic.utils.DateHelper;

import java.util.Comparator;

/**
 * @author EgorovaA
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 */
public class AgeRequirementComparator implements Comparator<AgeRequirement>
{
	public int compare(AgeRequirement ageRequirement1, AgeRequirement ageRequirement2)
	{
		if (ageRequirement1 == null && ageRequirement2 == null)
			return 0;

		if (ageRequirement1 == null || ageRequirement2 == null)
			return -1;

		int compareResult = ageRequirement1.getCode().compareTo(ageRequirement2.getCode());
		if(compareResult != 0)
			return compareResult;

		compareResult = DateHelper.nullSafeCompare(ageRequirement1.getDateBegin(), ageRequirement2.getDateBegin());
		if(compareResult != 0)
			return compareResult;

		compareResult = ageRequirement1.getLowLimitFemale().compareTo(ageRequirement2.getLowLimitFemale());
		if(compareResult != 0)
			return compareResult;

		compareResult = ageRequirement1.getLowLimitMale().compareTo(ageRequirement2.getLowLimitMale());
		if(compareResult != 0)
			return compareResult;

		compareResult = ageRequirement1.getTopLimit().compareTo(ageRequirement2.getTopLimit());
		if(compareResult != 0)
			return compareResult;

		return 0;
	}
}
