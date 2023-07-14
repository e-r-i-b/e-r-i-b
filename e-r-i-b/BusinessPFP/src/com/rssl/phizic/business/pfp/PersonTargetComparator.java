package com.rssl.phizic.business.pfp;

import java.util.Comparator;

/**
 * сортируем по датам, затем по идентификаторам
 * @author lepihina
 * @ created 10.09.13
 * @ $Author$
 * @ $Revision$
 */
public class PersonTargetComparator implements Comparator<PersonTarget>
{

	public int compare(PersonTarget target1, PersonTarget target2)
	{
		int compareResult = target1.getPlannedDate().compareTo(target2.getPlannedDate());
		return compareResult == 0? target1.getId().compareTo(target2.getId()): compareResult;
	}
}
