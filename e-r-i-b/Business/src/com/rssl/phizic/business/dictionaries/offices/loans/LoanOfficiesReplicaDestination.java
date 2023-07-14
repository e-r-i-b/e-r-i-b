package com.rssl.phizic.business.dictionaries.offices.loans;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.business.dictionaries.BaseDependendReplicaDestinationBase;

/**
 * @author Krenev
 * @ created 12.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanOfficiesReplicaDestination extends BaseDependendReplicaDestinationBase<Office>
{
	public LoanOfficiesReplicaDestination()
	{
		super("com.rssl.phizic.gate.dictionaries.officies.loans.LoanOffice.getAllBySynchKey");
	}
}
