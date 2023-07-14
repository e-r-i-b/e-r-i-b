package com.rssl.phizic.business.ext.sbrf.deposits.replica.destinations;

/**
 * @author EgorovaA
 * @ created 31.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsQVBReplicaDestination extends DepositsReplicaDestination
{
	protected String getDestinationEntityName()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVB";
	}

	protected String getDestinationClearQuery()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVB.deleteAll";
	}
}