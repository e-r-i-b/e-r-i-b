package com.rssl.phizic.business.dictionaries.regions.replication;
import static com.rssl.phizic.business.Constants.DB_CSA;

/**
 * @author komarov
 * @ created 29.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class RegionSynchCSADestinitions extends RegionsReplicaDestinitions
{
	@Override
	protected String getInstanceName()
	{
		return DB_CSA;
	}
}
