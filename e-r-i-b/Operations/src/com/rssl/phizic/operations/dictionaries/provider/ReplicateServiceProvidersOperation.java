package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.dictionaries.providers.ReplicateProvidersBackgroundTask;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.replication.providers.*;
import com.rssl.phizic.business.dictionaries.replication.replicator.PartialServiceProviderReplicator;
import com.rssl.phizic.business.dictionaries.replication.replicator.RemoveServiceProviderReplicator;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import com.rssl.phizic.business.operations.restrictions.DepartmentServiceProvidersRestriction;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.OneWayReplicator;
import com.rssl.phizic.gate.dictionaries.Replicator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.background.BackgroundOperationBase;
import org.hibernate.Session;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * @author hudyakov
 * @ created 25.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class ReplicateServiceProvidersOperation extends BackgroundOperationBase<ReplicationTaskResult, ReplicateProvidersBackgroundTask, ServiceProvidersRestriction>
{
	private InputStream inputStream;
	private List<Long> billingIds;
	private Properties properties;
	private ReplicateProvidersBackgroundTask task;
	private String dbInstanceName;

	private void init(InputStream inputStream, List<Long> billingIds, Properties properties)
	{
		this.inputStream = inputStream;
		this.billingIds = billingIds;
		this.properties = properties;
	}

	/**
	 * Инициализация
	 * @param inputStream - поток данных
	 * @param billingIds  - биллинговые системы
	 * @param properties  - специфичные свойства выполнения
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void initialize(InputStream inputStream, List<Long> billingIds, Properties properties) throws BusinessLogicException, BusinessException
	{
		init(inputStream, billingIds, properties);
		dbInstanceName = MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	public void initialize(ReplicateProvidersBackgroundTask backroundTask) throws BusinessException, BusinessLogicException
	{
		init(backroundTask.getFile(), backroundTask.getBillingIds(), backroundTask.getProperties());
		this.task = backroundTask;
		dbInstanceName = getInstanceName();
		setRestriction(new DepartmentServiceProvidersRestriction());
	}

	public ReplicateProvidersBackgroundTask createBackroundTask() throws BusinessException, BusinessLogicException
	{
		ReplicateProvidersBackgroundTask task = new ReplicateProvidersBackgroundTask(inputStream, billingIds);
		task.setProperties(properties);
		task.setOwnerId(MultiBlockModeDictionaryHelper.getEmployeeData().getLoginId());
		task.setOwnerFIO(getCurrentEmployeeFullName());
		return registerBackgroundTask(task, dbInstanceName);
	}

	/**
	 * Репликация
	 */
	public ReplicationTaskResult execute() throws BusinessLogicException, BusinessException
	{
		try
		{
			ReplicationTaskResult result = HibernateExecutor.getInstance(dbInstanceName).execute(new HibernateAction<ReplicationTaskResult>()
			{
				public ReplicationTaskResult run(Session session) throws Exception
				{
					return doExecute();
				}
			});

			MultiBlockModeDictionaryHelper.updateDictionary(ServiceProviderBase.class);
			return result;
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private ReplicationTaskResult doExecute() throws BusinessLogicException, BusinessException
	{
		ReplicationTaskResult result = new  ReplicationTaskResult();
		result.setStartDate(Calendar.getInstance());
		//если не фоновая репликация то задача не создана.
		if (task != null)
		{
			task.setResult(result);
			task.setFileInternal(null);
		}
		try
		{
			ServiceProvidersSAXLoader loader = new ServiceProvidersSAXLoader(inputStream, billingIds, getRestriction(), result, properties, dbInstanceName);
			Replicator replicator;
			ServiceProviderReplicaDestination destination;
			switch (loader.getLoadType())
			{
				case PARTIAL_REPLICATION:
					if (task != null)
						destination = new SimpleServiceProviderReplicaDestination(task,loader, dbInstanceName);
					else
						destination = new SimpleServiceProviderReplicaDestination(result,loader, dbInstanceName);
					replicator = new PartialServiceProviderReplicator(new ReplicaDestinationDecorator(destination, result));
					break;
				case FULL_REPLICATION:
					if (task != null)
						destination = new ServiceProviderReplicaDestination(task, loader, dbInstanceName);
					else
						destination = new ServiceProviderReplicaDestination(result, loader, properties, dbInstanceName);
					replicator = new OneWayReplicator(loader, new ReplicaDestinationDecorator(destination, result), new ServiceProviderComparator());
					break;
				case DELETE:
					if(task!=null)
						destination = new SimpleServiceProviderReplicaDestination(task, loader, dbInstanceName);
					else
						destination = new SimpleServiceProviderReplicaDestination(result, loader, dbInstanceName);
					replicator = new RemoveServiceProviderReplicator(new ReplicaDestinationDecorator(destination, result));
					break;
				default:
					throw new BusinessException("Неизвестный тип репликации " + loader.getLoadType());
			}
			replicator.replicate();
		}
		catch (AccessException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		result.setEndDate(Calendar.getInstance());
		return result;
	}
}
