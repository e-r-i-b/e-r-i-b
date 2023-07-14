package com.rssl.phizic.scheduler.ermb;

import com.rssl.phizic.business.ermb.sms.inbox.SmsInBoxService;
import com.rssl.phizic.business.ermb.sms.messaging.ErmbSmsChannel;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.scheduler.StatefulModuleJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Удаление просроченных СМС смс-канала
 * @author Puzikov
 * @ created 21.07.14
 * @ $Author$
 * @ $Revision$
 */

public class ExpiredSmsRemoverJob extends StatefulModuleJob
{
	private static final SmsInBoxService service = new SmsInBoxService();
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
			service.removeExpired();
		}
		catch (Exception e)
		{
			log.error("Сбой джоба на очистке ERMB_SMS_INBOX", e);
		}
	}
}
