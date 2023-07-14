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
 * @ created 12.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class PartialServiceProviderReplicator implements Replicator
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private ReplicaDestination destination;

	/**
	 * ctor
	 * @param destination - список поставщиков услуг
	 */
	public PartialServiceProviderReplicator(ReplicaDestination destination)
	{
		this.destination = destination;
	}

	public void replicate() throws GateException, GateLogicException
	{
		ServiceProviderForReplicationWrapper src = null;

		try
		{
			Iterator iter = destination.iterator();

			while(iter.hasNext())
			{
				src = (ServiceProviderForReplicationWrapper) iter.next();
				destination.update(src, src);
			}
		}
		catch (Exception e)
		{
			log.error(String.format(ServiceProvidersConstants.REPLICATION_PROVIDER_ERROR_MESSAGE, src.getSynchKey()));
			throw new GateException(e);
		}
		finally
		{
			destination.close();
		}
	}
}
