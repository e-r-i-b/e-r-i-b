package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchDictionariesService;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchObjectState;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ReplicateProvidersBackgroundTask;
import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;

import java.util.Iterator;

/**
 * @author khudyakov
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class SimpleServiceProviderReplicaDestination extends ServiceProviderReplicaDestination
{
	private ServiceProvidersSAXLoader loader;
	private static final AsynchSearchDictionariesService asynchSearchDictionariesService = new AsynchSearchDictionariesService();

	public SimpleServiceProviderReplicaDestination(ReplicationTaskResult result, ServiceProvidersSAXLoader loader, String dbInstanceName)
	{
		super(result, false, false, dbInstanceName);
		
		this.replicationType = loader.getReplicationType();
		this.loader = loader;
	}

	public SimpleServiceProviderReplicaDestination(ReplicateProvidersBackgroundTask task, ServiceProvidersSAXLoader loader, String dbInstanceName)
	{
		super(task, false, false, dbInstanceName);

		this.replicationType = loader.getReplicationType();
		this.loader = loader;
	}

	public Iterator<ServiceProviderForReplicationWrapper> iterator() throws GateException, GateLogicException
	{
		initialize();

		return loader.iterator();
	}

	public void update(DictionaryRecord oldValue, DictionaryRecord newValue) throws GateException
	{
		DetachedCriteria subCriteria = DetachedCriteria.forClass(BillingServiceProvider.class)
								.add(Expression.eq("synchKey", oldValue.getSynchKey())).setProjection(Projections.property("id"));

		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderForReplicationWrapper.class).add(Subqueries.propertyEq("provider.id", subCriteria));

		ServiceProviderForReplicationWrapper wrapper = (ServiceProviderForReplicationWrapper) criteria
								.getExecutableCriteria(getSession()).uniqueResult();
		ServiceProviderForReplicationWrapper newWrapper = (ServiceProviderForReplicationWrapper) newValue;
		if (wrapper != null)
		{
			super.update(wrapper, newValue);
			asynchSearchDictionariesService.addObjectInfoForAsynchSearch(wrapper.getProvider(), AsynchSearchObjectState.UPDATED);
		}
		else
		{
			super.add(newValue);
			asynchSearchDictionariesService.addObjectInfoForAsynchSearch(newWrapper.getProvider(), AsynchSearchObjectState.INSERTED);
		}
	}

	protected void updateEntity(BillingServiceProvider oldValue, BillingServiceProvider newValue)
	{
		oldValue.updateFrom(newValue, replicationType);     
	}
}
