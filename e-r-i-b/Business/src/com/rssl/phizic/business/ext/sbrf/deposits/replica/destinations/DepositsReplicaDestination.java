package com.rssl.phizic.business.ext.sbrf.deposits.replica.destinations;

import com.rssl.phizic.business.dictionaries.departments.PreClearReplicaDestinationBase;

/**
 * @author EgorovaA
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsReplicaDestination extends PreClearReplicaDestinationBase
{
	protected String getDestinationEntityName()
	{
		return null;
	}

	protected String getDestinationClearQuery()
	{
		return null;
	}

	protected String getInstanceName()
	{
		return null;
	}

	protected boolean isMultiBlockEntity()
	{
		return false;
	}
}
