package com.rssl.phizic.scheduler.jobs.fund;

import com.rssl.phizic.business.fund.initiator.FundInitiatorResponseService;
import com.rssl.phizic.business.fund.initiator.FundRequest;
import com.rssl.phizic.business.fund.initiator.FundRequestService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.List;

/**
 * @author osminin
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class BaseRequestJob extends BaseJob implements StatefulJob
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	protected static final FundRequestService requestService = new FundRequestService();
	protected static final FundInitiatorResponseService initiatorResponseService = new FundInitiatorResponseService();

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		ApplicationInfo.setCurrentApplication(Application.Scheduler);
		try
		{
			executeAdditional();
			for (FundRequest request: getRequests())
			{
				executeRequest(request);
			}
			synchronize();
		}
		finally
		{
            ApplicationInfo.setDefaultApplication();
		}
	}

	/**
	 * Дополнительные действия
	 */
	protected abstract void executeAdditional();

	/**
	 * синхронизировать между блоками
	 */
	protected abstract void synchronize();

	/**
	 * @return все новые запросы за месяц
	 */
	protected abstract List<FundRequest> getRequests();

	/**
	 * Обработать запрос
	 * @param request запрос
	 */
	protected abstract void executeRequest(FundRequest request);
}
