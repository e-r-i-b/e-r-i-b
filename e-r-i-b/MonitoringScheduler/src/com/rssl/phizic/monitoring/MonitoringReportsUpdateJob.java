package com.rssl.phizic.monitoring;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.monitoring.usercounterreport.UserCounterReport;
import com.rssl.phizic.business.monitoring.usercounterreport.UserCounterReportService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.web.security.SessionCountListener;
import com.rssl.phizic.web.security.UserCounter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mihaylov
 * @ created 28.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringReportsUpdateJob extends MonitoringJobBase
{
	public MonitoringReportsUpdateJob() throws JobExecutionException
	{
		super();
	}

	public void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		UserCounterReportService service = new UserCounterReportService();
		Map<Application, UserCounter> counters = SessionCountListener.getCounters();
		for(Application module : counters.keySet())
		{
			try
			{
				UserCounter counter = counters.get(module);
				Map<Long, AtomicLong> counterByTb = counter.getCounterByTB();
				for(Long tb : counterByTb.keySet())
				{
					UserCounterReport userCounterReport = service.findReport(getAppServerName(),module.name(),tb);
					if(userCounterReport == null)
					{
						userCounterReport = new UserCounterReport();
						userCounterReport.setApplicationName(getAppServerName());
						userCounterReport.setModule(module.name());
						userCounterReport.setTB(tb);
					}
					userCounterReport.setCount(counterByTb.get(tb).get());
					userCounterReport.setUpdateTime(Calendar.getInstance());
					service.addOrUpdate(userCounterReport);
				}
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}
}
