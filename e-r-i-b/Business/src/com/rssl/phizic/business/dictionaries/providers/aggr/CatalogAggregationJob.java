package com.rssl.phizic.business.dictionaries.providers.aggr;

import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author krenev
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 * Джоб агрегации каталога услуг.
 */
public class CatalogAggregationJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_SCHEDULER);
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			AggregationService.aggregate();
		}
		catch (Exception e)
		{
			log.error("Ошибка агрегации каталога услуг", e);
		}
	}
}
