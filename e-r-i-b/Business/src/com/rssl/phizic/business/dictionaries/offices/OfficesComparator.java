package com.rssl.phizic.business.dictionaries.offices;

import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author Omeliyanchuk
 * @ created 12.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class OfficesComparator extends AbstractCompatator
{
	public int compare(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		if (o1 == null || o2 == null)
		{
			return -1;
		}

		Office office1 = (Office) o1;
		Office office2 = (Office) o2;

		if (!isObjectsEquals(office1.getSynchKey(), office2.getSynchKey()))
			return -1;

		if (!isObjectsEquals(office1.getName(), office2.getName()))
			return -1;

		if (!isCodeEquals(office1.getCode(), office2.getCode()))
			return -1;

		if (!isObjectsEquals(office1.getAddress(), office2.getAddress()))
			return -1;

		if (!isObjectsEquals(office1.getTelephone(), office2.getTelephone()))
			return -1;

		if (!isObjectsEquals(office1.getBIC(), office2.getBIC()))
			return -1;

		return 0;
	}

	protected boolean isCodeEquals(Code code1, Code code2)
	{
		return isObjectsEquals(code1, code2);
	}
}
