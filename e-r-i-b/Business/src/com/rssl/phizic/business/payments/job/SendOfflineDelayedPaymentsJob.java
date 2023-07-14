package com.rssl.phizic.business.payments.job;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.marker.JobExecutionMarker;
import com.rssl.phizic.business.marker.JobExecutionMarkerService;
import org.quartz.JobExecutionException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Джоб для отправки отложенных из-за неактивности внешних систем платежей
 * @author Pankin
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class SendOfflineDelayedPaymentsJob extends SendDelayedPaymentsJobBase
{
	private static final String OFFLINE_DELAYED_STATE = "OFFLINE_DELAYED";
	private static final JobExecutionMarkerService markerService = new JobExecutionMarkerService();
	private static final AtomicInteger counter = new AtomicInteger(0);//Счетчик текущего количества одновременно работающих джобов.

	/**
	 * конструктор джоба
	 * @throws JobExecutionException
	 */
	public SendOfflineDelayedPaymentsJob() throws JobExecutionException
	{
		super();
	}

	protected String getStateCode()
	{
		return OFFLINE_DELAYED_STATE;
	}

	protected AtomicInteger getJobCounter()
	{
		return counter;
	}

	@Override
	protected boolean afterFireEventCheckDocument(BusinessDocument document) throws BusinessException
	{
		if (!getStateCode().equals(document.getState().getCode()))
			return true;

		addToProcessed(document);
		return false;
	}

	@Override
	protected void doJob() throws Exception
	{
		JobExecutionMarker jobExecutionMarker = markerService.findForJob(getJobName());
		if(jobExecutionMarker != null)
		{
			super.doJob();
			if(!isInterrupt())
				markerService.remove(jobExecutionMarker);
		}
	}
}
