package com.rssl.phizicgate.esberibgate.mock.rate;

import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.service.StartupServiceDictionary;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author gulov
 * @ created 04.10.2010
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Джоб для обновления курсов валют, вызывает клиентскую часть вебсервиса 
 */
public class MockEribRatesJob extends BaseJob implements StatefulJob
{
	public MockEribRatesJob() throws JobExecutionException
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			EribRatesMessagingService service = new EribRatesMessagingService();

			service.makeRequest(false);
		}
		catch (Exception e)
		{
			throw new JobExecutionException(e);
		}
	}
}
