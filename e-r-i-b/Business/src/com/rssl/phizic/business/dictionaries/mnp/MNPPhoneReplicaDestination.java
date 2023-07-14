package com.rssl.phizic.business.dictionaries.mnp;

import com.rssl.phizic.business.dictionaries.departments.PreClearReplicaDestinationBase;

/**
 * @author Puzikov
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 */

public class MNPPhoneReplicaDestination extends PreClearReplicaDestinationBase
{
	@Override
	protected String getDestinationEntityName()
	{
		return "com.rssl.phizic.business.dictionaries.mnp.MNPPhone";
	}

	@Override
	protected String getDestinationClearQuery()
	{
		return "com.rssl.phizic.business.dictionaries.mnp.MNPPhone.deleteAll";
	}

	@Override
	protected String getInstanceName()
	{
		return null;
	}

	@Override
	protected boolean isMultiBlockEntity()
	{
		return false;
	}
}
