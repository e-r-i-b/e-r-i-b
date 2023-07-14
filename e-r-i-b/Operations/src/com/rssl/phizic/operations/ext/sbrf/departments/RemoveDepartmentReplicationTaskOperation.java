package com.rssl.phizic.operations.ext.sbrf.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicateDepartmentsBackgroundTask;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.background.BackgroundTaskService;
import com.rssl.phizic.business.operations.background.TaskState;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * Операция удаления фоновой задачи репликации подразделений
 * @author niculichev
 * @ created 23.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class RemoveDepartmentReplicationTaskOperation extends OperationBase implements RemoveEntityOperation
{
	private static final String REMOVE_ONLY_NEW_STATE_TASK_MESSAGE = "Удалена может быть только задача со статусом «Новая»";
	private static final String NOT_FOUND_TASK_MESSAGE = "Не найдена фоновая задача репликации подразделений с id = %s";

	private static final BackgroundTaskService backgroundTaskService = new BackgroundTaskService();

	private ReplicateDepartmentsBackgroundTask task;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		ReplicateDepartmentsBackgroundTask backgroundTask = backgroundTaskService.findById(ReplicateDepartmentsBackgroundTask.class, id, getInstanceName());
		if(backgroundTask == null)
			throw new BusinessException(String.format(NOT_FOUND_TASK_MESSAGE, id));

		if(backgroundTask.getState() != TaskState.NEW)
			throw new BusinessLogicException(REMOVE_ONLY_NEW_STATE_TASK_MESSAGE);

		this.task = backgroundTask;
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		backgroundTaskService.remove(task, getInstanceName());
	}

	public Object getEntity()
	{
		return task;
	}

	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
