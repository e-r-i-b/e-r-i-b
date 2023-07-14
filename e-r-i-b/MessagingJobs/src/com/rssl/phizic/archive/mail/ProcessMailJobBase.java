package com.rssl.phizic.archive.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.mail.archive.ProcessMailOperationBase;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author mihaylov
 * @ created 24.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс для шедуллеров обрабатывающих письма
 */
public abstract class ProcessMailJobBase extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	public ProcessMailJobBase() throws JobExecutionException
	{
	}

	public void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		ProcessMailOperationBase operation = getProcessOperation();
		try
		{
			operation.initialize();
			operation.process();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка архивации сообщений службы помощи", e);
		}
	}

	protected abstract ProcessMailOperationBase getProcessOperation();
}
