package com.rssl.phizic.business.dictionaries.asyncSearch.job;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchDictionariesService;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchObject;
import com.rssl.phizic.job.BaseJob;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.List;

/**
 * Джоб актуализирует данные в базе "живого" поиска
 *
 * @ author: Gololobov
 * @ created: 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class AsynchSearchDictionariesLoaderJob extends BaseJob implements StatefulJob
{
	private static final AsynchSearchDictionariesService asynchSearchService = new AsynchSearchDictionariesService();
	private static final int LIST_LIMIT = 20;

	public AsynchSearchDictionariesLoaderJob() throws JobExecutionException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			while(true)
			{
				List<AsynchSearchObject> objectsForUpdateList = asynchSearchService.getObjectsForUpdate(LIST_LIMIT);
				//нечего обновлять - "давай до свиданья"
				if (CollectionUtils.isEmpty(objectsForUpdateList))
					break;
				asynchSearchService.addOrDeleteOrUpdateObjects(objectsForUpdateList);
			}
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(e);
		}
	}
}
