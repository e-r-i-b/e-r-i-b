package com.rssl.phizic.scheduler.jobs.cardoperations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationExtendedFields;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.List;

/**
 * убирает операцию из расчетов АЛФ если время жизни операции вышло
 * @author Jatsky
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationLifeTimeJob extends BaseJob implements StatefulJob
{
	private static final CardOperationService cardOperationService = new CardOperationService();

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			List<CardOperation> deadOperations = cardOperationService.getDeadOperations();
			for (CardOperation deadOperation : deadOperations)
			{
				deadOperation.setLoadDate(null);
				cardOperationService.addOrUpdate(deadOperation);
				CardOperationExtendedFields deadOperationExtendedFields = cardOperationService.getCardOperationExtendedFields(deadOperation.getId());
				for (CardOperation deadChildOperation : cardOperationService.findByParentPushUID(deadOperationExtendedFields.getPushUID()))
				{
					deadChildOperation.setLoadDate(null);
					cardOperationService.addOrUpdate(deadChildOperation);
				}
			}
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(e);
		}
	}
}
