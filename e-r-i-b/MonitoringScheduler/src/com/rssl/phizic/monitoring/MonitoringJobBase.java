package com.rssl.phizic.monitoring;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author gladishev
 * @ created 23.07.2011
 * @ $Author$
 * @ $Revision$
 */

abstract class MonitoringJobBase extends BaseJob implements StatefulJob
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	protected MonitoringJobBase() throws JobExecutionException
	{
	}

	protected String getAppServerName()
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		return applicationConfig.getApplicationInstanceName();
	}

}
