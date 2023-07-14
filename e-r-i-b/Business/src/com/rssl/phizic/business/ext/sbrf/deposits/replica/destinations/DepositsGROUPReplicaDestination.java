package com.rssl.phizic.business.ext.sbrf.deposits.replica.destinations;

/**
 * @author EgorovaA
 * @ created 26.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsGROUPReplicaDestination extends DepositsReplicaDestination
{
	protected String getDestinationEntityName()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsGROUP";
	}

	protected String getDestinationClearQuery()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsGROUP.deleteAll";
	}
}

