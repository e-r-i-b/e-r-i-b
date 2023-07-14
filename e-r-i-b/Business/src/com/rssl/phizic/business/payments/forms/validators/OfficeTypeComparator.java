package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeTypeCompare;

/**
 * @author hudyakov
 * @ created 09.09.2009
 * @ $Author$
 * @ $Revision$
 */

public class OfficeTypeComparator
{
	public static boolean compare(Office office1, Office office2, OfficeTypeCompare compareType)
	{
		if (compareType.equals(OfficeTypeCompare.oneBranch))
		{
			String branch1 = office1.getCode().getFields().get("branch");
			String branch2 = office2.getCode().getFields().get("branch");

			return branch1.equals(branch2);
		}
		else if(compareType.equals(OfficeTypeCompare.oneTB))
		{
			String tb1 = office1.getCode().getFields().get("region");
			String tb2 = office2.getCode().getFields().get("region");

			return tb1.equals(tb2);
		} else
			return office1.equals(office2);
	}
}
