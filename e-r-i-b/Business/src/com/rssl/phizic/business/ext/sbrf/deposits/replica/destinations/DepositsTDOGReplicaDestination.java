package com.rssl.phizic.business.ext.sbrf.deposits.replica.destinations;

/**
 * @author EgorovaA
 * @ created 31.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsTDOGReplicaDestination extends DepositsReplicaDestination
{
	protected String getDestinationEntityName()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG";
	}

	protected String getDestinationClearQuery()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG.deleteAll";
	}
}
