package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.StatusChanger;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author Novikov
 * @ created 21.05.2007
 * @ $Author: Novikov $
 * @ $Revision: 4156 $
 */
public class StatusHandlingJob extends BaseJob implements StatefulJob
{
	public static final String LAST_RUN_KEY   = "last-run-property";
	public static final String TASK_CLAZZ_KEY = "task-class";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	/**
	 * инициализация джоба
	 */
	public StatusHandlingJob() throws JobExecutionException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		log.info("Запуск синхрнизации статусов");

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		String lastRunProperty = dataMap.getString(LAST_RUN_KEY);
		String taskClass       = dataMap.getString(TASK_CLAZZ_KEY);

		log.info("lastRunProperty=" + lastRunProperty);
		log.info("taskClass="       + taskClass);

		if (lastRunProperty == null)
			throw new JobExecutionException("Не установлено свойство " + LAST_RUN_KEY);

		if (taskClass == null)
			throw new JobExecutionException("Не установлено свойство " + TASK_CLAZZ_KEY);

		try
		{
			StatusChanger changer = new StatusChanger();
			changer.setLastRunPropertyName(lastRunProperty);
			changer.setTaskClassName(taskClass);
			changer.update();

			log.info("Синхронизация статусов завершена");
		}
		catch (GateException ex)
		{
			throw new JobExecutionException(ex);
		}
		catch (GateLogicException e)
		{
			throw new JobExecutionException(e);
		}
	}
}
