package com.rssl.phizic.business.ext.sbrf.deposits.replica.destinations;

/**
 * @author EgorovaA
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsQVBTPReplicaDestination extends DepositsReplicaDestination
{
	protected String getDestinationEntityName()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVBTP";
	}

	protected String getDestinationClearQuery()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVBTP.deleteAll";
	}
}
