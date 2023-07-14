package com.rssl.phizic.scheduler.jobs.infobank;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.esberibgate.ws.TransportProvider;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * джоб для проверки работоспособности каанала взаимодействия ЕРИБ с Инфобанком
 * @author tisov
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 */

public class InfobankMonitoringJob extends BaseJob implements StatefulJob
{

	private static final long CORRECT_MESSAGE_STATUS = 0;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public InfobankMonitoringJob() throws JobExecutionException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		SecurityAccountTestRequestHelper requestHelper = new SecurityAccountTestRequestHelper(GateSingleton.getFactory());
		IFXRq_Type ifxRq = requestHelper.createSecurityAccountListRequest();
		try
		{
			IFXRs_Type ifxRs = TransportProvider.doRequest(ifxRq);
			Status_Type statusType = ifxRs.getBankAcctInqRs().getStatus();
			if (statusType.getStatusCode() != CORRECT_MESSAGE_STATUS)
				 log.error("некорректный код статуса ответа");
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
