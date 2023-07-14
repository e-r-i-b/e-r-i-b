package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicateDepartmentsBackgroundTask;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicationDepartmentsTaskResult;
import org.quartz.StatefulJob;

/**
 * @author akrenev
 * @ created 17.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Джоб для выполнения фоновой загрузки подразделений
 */

public class DepartmentsPerformBackgroundTasksJob extends PerformBackgroundTasksJob<ReplicationDepartmentsTaskResult, ReplicateDepartmentsBackgroundTask> implements StatefulJob
{
	@Override
	protected Class<ReplicateDepartmentsBackgroundTask> getTaskClass()
	{
		return ReplicateDepartmentsBackgroundTask.class;
	}
}
