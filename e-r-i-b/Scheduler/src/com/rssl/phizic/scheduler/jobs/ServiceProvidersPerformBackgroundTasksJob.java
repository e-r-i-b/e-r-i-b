package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.dictionaries.providers.ReplicateProvidersBackgroundTask;
import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import org.quartz.StatefulJob;

/**
 * @author akrenev
 * @ created 17.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Джоб для выполнения фоновой загрузки поставщиков
 */

public class ServiceProvidersPerformBackgroundTasksJob extends PerformBackgroundTasksJob<ReplicationTaskResult, ReplicateProvidersBackgroundTask> implements StatefulJob
{
	@Override
	protected Class<ReplicateProvidersBackgroundTask> getTaskClass()
	{
		return ReplicateProvidersBackgroundTask.class;
	}
}
