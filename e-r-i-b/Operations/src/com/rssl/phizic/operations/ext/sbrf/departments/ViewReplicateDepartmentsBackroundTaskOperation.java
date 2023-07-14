package com.rssl.phizic.operations.ext.sbrf.departments;

import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicateDepartmentsBackgroundTask;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicationDepartmentsTaskResult;
import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import com.rssl.phizic.operations.background.ViewBackgroundTaskOperationBase;

/**
 * Операция просмотра фоновой задачи репликации подразделений
 * @author niculichev
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ViewReplicateDepartmentsBackroundTaskOperation extends ViewBackgroundTaskOperationBase<ReplicationDepartmentsTaskResult, ReplicateDepartmentsBackgroundTask>
{
	protected Class<ReplicateDepartmentsBackgroundTask> getBackgroundTaskClass()
	{
		return ReplicateDepartmentsBackgroundTask.class;
	}
}
