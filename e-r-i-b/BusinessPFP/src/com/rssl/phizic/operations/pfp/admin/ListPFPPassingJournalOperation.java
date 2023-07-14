package com.rssl.phizic.operations.pfp.admin;

import com.rssl.phizic.business.pfp.PFPConstants;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ListForEmployeeOperation;

/**
 * @author komarov
 * @ created 20.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListPFPPassingJournalOperation extends ListForEmployeeOperation implements ListEntitiesOperation
{
	protected String getInstanceName()
	{
		return PFPConstants.INSTANCE_NAME;
	}

}
