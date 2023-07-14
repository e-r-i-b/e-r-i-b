package com.rssl.phizic.business.ext.sbrf.deposits.replica.destinations;

import com.rssl.phizic.business.dictionaries.departments.PreClearReplicaDestinationBase;

/**
 * Цель репликации справочника процентных ставок по вкладам (DCF_TAR)
 * @author Pankin
 * @ created 02.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsDCFTARReplicaDestination extends PreClearReplicaDestinationBase
{
	protected String getDestinationEntityName()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsDCFTAR";
	}

	protected String getDestinationClearQuery()
	{
		return "com.rssl.phizic.business.ext.sbrf.deposits.DepositsDCFTAR.deleteAll";
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
