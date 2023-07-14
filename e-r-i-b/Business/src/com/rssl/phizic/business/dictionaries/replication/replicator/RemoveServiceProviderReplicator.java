package com.rssl.phizic.business.dictionaries.replication.replicator;

import com.rssl.phizic.business.dictionaries.replication.providers.ServiceProviderForReplicationWrapper;
import com.rssl.phizic.gate.dictionaries.Replicator;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.common.types.documents.ServiceProvidersConstants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.Iterator;

/**
 * @author hudyakov
 * @ created 13.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class RemoveServiceProviderReplicator implements Replicator
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private ReplicaDestination destination;

	/**
	 * ctor
	 * @param destination - ресурс репликации
	 */
	public RemoveServiceProviderReplicator(ReplicaDestination destination)
	{
		this.destination = destination;
	}

	public void replicate() throws GateException, GateLogicException
	{
		ServiceProviderForReplicationWrapper dest = null;

		try
		{
			Iterator iter = destination.iterator();

			while(iter.hasNext())
			{
				dest = (ServiceProviderForReplicationWrapper) iter.next();
				destination.remove(dest);
			}
		}
		catch (Exception e)
		{
			log.error(String.format(ServiceProvidersConstants.REPLICATION_PROVIDER_ERROR_MESSAGE, dest.getSynchKey()));
			throw new GateException(e);
		}
		finally
		{
			destination.close();
		}
	}
}
