package com.rssl.phizic.business.dictionaries.offices.extended.replication;

import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.utils.StringHelper;

import java.util.Comparator;

/**
 * Компаратор который упорядочивает в соответствии с уровнем
 * (сначала записи самого высокого уровня, потом записи низкого)
 * @author niculichev
 * @ created 09.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentLevelComparator implements Comparator<ExtendedDepartment>
{
	public int compare(ExtendedDepartment department1, ExtendedDepartment department2)
	{
		ExtendedCodeImpl code1 = new ExtendedCodeImpl(department1.getCode());
		Long region1 = Long.valueOf(code1.getRegion());
		Long branch1 = StringHelper.isEmpty(code1.getBranch()) ? null : Long.valueOf(code1.getBranch());
		Long office1 = StringHelper.isEmpty(code1.getOffice()) ? null : Long.valueOf(code1.getOffice());

		ExtendedCodeImpl code2 = new ExtendedCodeImpl(department2.getCode());
		Long region2 =  Long.valueOf(code2.getRegion());
		Long branch2 = StringHelper.isEmpty(code2.getBranch()) ? null : Long.valueOf(code2.getBranch());
		Long office2 = StringHelper.isEmpty(code2.getOffice()) ? null : Long.valueOf(code2.getOffice());

		// сравниваем всп
		if(office1 == null && office2 != null)
			return -1;

		if(office1 != null && office2 == null)
			return 1;

		// сравниваем осб
		if(branch1 == null && branch2 != null)
			return -1;

		if(branch1 != null && branch2 == null)
			return 1;

		if(!equalToNull(region1, region2))
			return region1.compareTo(region2);

		if(!equalToNull(branch1, branch2))
			return branch1.compareTo(branch2);

		return compareToNull(office1, office2);
	}

	private boolean equalToNull(Long o1, Long o2)
	{
		if(o1 == null && o2 == null)
			return true;

		if(o1 == null || o2 == null)
			return false;

		return o1.equals(o2);
	}

	private int compareToNull(Long o1, Long o2)
	{
		if (o1 == null && o2 == null)
			return 0;

		if (o1 == null && o2 != null)
			return -1;

		if (o1 != null && o2 == null)
			return 1;

		return o1.compareTo(o2);
	}
}
