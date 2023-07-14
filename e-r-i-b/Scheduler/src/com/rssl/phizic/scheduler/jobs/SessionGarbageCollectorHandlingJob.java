package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.sms.banking.security.SessionManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author hudyakov
 * @ created 21.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class SessionGarbageCollectorHandlingJob implements StatefulJob
{
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		SessionManager.removeExpiredSession();
	}
}
