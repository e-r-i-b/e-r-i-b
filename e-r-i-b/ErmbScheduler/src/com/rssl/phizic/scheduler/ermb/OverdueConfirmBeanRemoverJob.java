package com.rssl.phizic.scheduler.ermb;

import com.rssl.phizic.business.ermb.sms.messaging.ErmbSmsChannel;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.scheduler.StatefulModuleJob;
import com.rssl.phizic.security.ConfirmBeanService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Джоб удаления просроченных бинов подтверждения
 * @author Puzikov
 * @ created 21.07.14
 * @ $Author$
 * @ $Revision$
 */

public class OverdueConfirmBeanRemoverJob extends StatefulModuleJob
{
	private static final ConfirmBeanService confirmBeanService = new ConfirmBeanService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	@Override
	public Class<? extends Module> getModuleClass()
	{
		return ErmbSmsChannel.class;
	}

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			confirmBeanService.removeOverdueConfirmBeans();
		}
		catch (Exception e)
		{
			log.error("Ошибка джоба удаления просроченных бинов подтверждения", e);
		}
	}
}
