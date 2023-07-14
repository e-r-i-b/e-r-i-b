package com.rssl.phizic.operations.ext.sbrf.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.*;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.sources.DepartmentSubbranchReplicaSource;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.background.BackgroundTaskService;
import com.rssl.phizic.business.operations.restrictions.DepartmentServiceProvidersRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.background.BackgroundOperationBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * ќпераци€ дл€ выполнени€ репликации департаментов
 * @author niculichev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateDepartmentsBackgroundOperation extends BackgroundOperationBase<ReplicationDepartmentsTaskResult, ReplicateDepartmentsBackgroundTask, DepartmentServiceProvidersRestriction>
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final BackgroundTaskService backgroundTaskService = new BackgroundTaskService();

	private InputStream inputStream;
	private List<Long> departmentIds;
	private ReplicationDepartmentsMode replicationMode;
	private ReplicateDepartmentsBackgroundTask backroundTask;

	public void initialize(InputStream inputStream, List<Long> departmentIds, ReplicationDepartmentsMode replicationMode) throws BusinessLogicException, BusinessException
	{
		this.inputStream = inputStream;
		this.departmentIds = departmentIds;
		this.replicationMode = replicationMode;
	}


	public void initialize(ReplicateDepartmentsBackgroundTask backroundTask) throws BusinessException, BusinessLogicException
	{
		try
		{
			ReplicateDepartmentsBackgroundTaskContent content = backgroundTaskService.getReplicaTaskContent(backroundTask.getId());
			if (content == null)
			{
				throw new BusinessException("—одержание дл€ фоновой задачи " + backroundTask.getId() + " не найдено.");
			}
			initialize(
					content.getContent().getBinaryStream(),
					backroundTask.getDepartmentIds(),
					backroundTask.getReplicationMode());
			this.backroundTask = backroundTask;
		}
		catch (SQLException e)
		{
			throw new BusinessException(e);
		}
	}

	public ReplicateDepartmentsBackgroundTask createBackroundTask() throws BusinessException, BusinessLogicException
	{
		ReplicateDepartmentsBackgroundTask task = new ReplicateDepartmentsBackgroundTask();
		task.setDepartmentIdsInternal(StringUtils.join(departmentIds, ';'));
		task.setReplicationMode(replicationMode);
		task.setOwnerId(MultiBlockModeDictionaryHelper.getEmployeeData().getLoginId());
		task.setOwnerFIO(getCurrentEmployeeFullName());

		return registerBackgroundTask(task);
	}

	public ReplicationDepartmentsTaskResult execute() throws BusinessException, BusinessLogicException
	{
		final ReplicationDepartmentsTaskResult result = new ReplicationDepartmentsTaskResult();
		result.setStartDate(Calendar.getInstance());

		// сотрудник должен сразу видеть дату начала обработки, поэтому результат обработки сразу сетим, и впоследствии обновл€ем
		backroundTask.setResult(result);
		backgroundTaskService.addOrUpdate(backroundTask);

		// если не задан список подразделений берем все “Ѕ
		List<Long> depIds = CollectionUtils.isNotEmpty(this.departmentIds) ? this.departmentIds : departmentService.getTerbanksIds();

		final DepartmentReplicaDestination destination = new DepartmentReplicaDestination(depIds, result);
		final DepartmentSubbranchReplicaSource source = new DepartmentSubbranchReplicaSource(inputStream, depIds, result);

		// теперь в результате есть количества исходных элементов, нужно показать их
		backgroundTaskService.addOrUpdate(backroundTask);

		if(replicationMode == ReplicationDepartmentsMode.replica)
		{
			try
			{
				HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session) throws Exception
					{
						destination.setSession(session);
						doReplicate(source, destination, result);
						return null;
					}
				});
			}
			catch (Exception e)
			{
				throw new BusinessException(e.getMessage(), e);
			}
		}
		else
		{
			doReplicate(source, destination, result);
		}

		MultiBlockModeDictionaryHelper.updateDictionary(ExtendedDepartment.class);

		result.setEndDate(Calendar.getInstance());
		return result;
	}

	private void doReplicate(DepartmentSubbranchReplicaSource source, DepartmentReplicaDestination destination, ReplicationDepartmentsTaskResult result) throws BusinessException, BusinessLogicException
	{
		try
		{
			new DepartmentsTreeReplicator(source, destination, result).replicate();
		}
		catch (GateException e)
		{
			throw new BusinessException(e.getMessage(), e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
	}

	protected ReplicateDepartmentsBackgroundTask registerBackgroundTask(final ReplicateDepartmentsBackgroundTask backgroundTask) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<ReplicateDepartmentsBackgroundTask>()
			{
				public ReplicateDepartmentsBackgroundTask run(Session session) throws Exception
				{
					backgroundTaskService.addOrUpdate(backgroundTask, getInstanceName());
					// сохран€ем файл в отдельную таблицу, чтоб не ворочать им при обновлении задачи
					backgroundTaskService.addBackgroundTaskContent(backgroundTask,inputStream,getInstanceName());
					return backgroundTask;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ѕолучить список подразделений по выбранным идентификаторам
	 * @param ids идентификаторы
	 * @return список подразеделений
	 * @throws BusinessException
	 */
	public List<Department> getSelectedDepartments(List<Long> ids) throws BusinessException
	{
		return departmentService.findByIds(ids);
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
