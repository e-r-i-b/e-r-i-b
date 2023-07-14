package com.rssl.phizic.operations.config.gate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.job.SendOfflineDelayedPaymentsJob;
import com.rssl.phizic.business.marker.JobExecutionMarkerService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.monitoring.MonitoringParameters;
import com.rssl.phizic.gate.monitoring.RunGateMonitoringService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author akrenev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class RunGateMonitoringOperation extends OperationBase implements EditEntityOperation
{
	private String[] gateUrls;
	private MonitoringParameters monitoringParameters = new MonitoringParameters();
	private static final JobExecutionMarkerService jobExecutionMarkerService = new JobExecutionMarkerService();

	public void save() throws BusinessException, BusinessLogicException
	{
		String currentUrl = null;
		try
		{
			RunGateMonitoringService gateMonitoringService = GateSingleton.getFactory().service(RunGateMonitoringService.class);
			for (String gateUrl : gateUrls)
			{
				currentUrl = gateUrl;
				gateMonitoringService.run(gateUrl, monitoringParameters);
			}
			jobExecutionMarkerService.createForJob(SendOfflineDelayedPaymentsJob.class.getName());
		}
		catch (TemporalGateException e)
		{
			throw new BusinessLogicException("Невозможно оповестить шлюз " + currentUrl, e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException("Невозможно оповестить шлюз " + currentUrl, e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * задать урлы шлюзов
	 * @param gateUrls урлы шлюзов
	 */
	public void setGateUrls(String[] gateUrls)
	{
		this.gateUrls = gateUrls;
	}

	public MonitoringParameters getEntity() throws BusinessException, BusinessLogicException
	{
		return monitoringParameters;
	}
}
