package com.rssl.phizic.business.dictionaries.offices.loans;

import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;

/**
 * @author Krenev
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class LoanOfficesComparator extends AbstractCompatator
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

		LoanOffice office1 = (LoanOffice) o1;
		LoanOffice office2 = (LoanOffice) o2;

		if (!isObjectsEquals(office1.getSynchKey(), office2.getSynchKey()))
			return -1;
		//другие поля не сравниваем, т.к. изменененные данные в нашей базе не должны обновляться при
		//последующей репликации.
		return 0;
	}
}
