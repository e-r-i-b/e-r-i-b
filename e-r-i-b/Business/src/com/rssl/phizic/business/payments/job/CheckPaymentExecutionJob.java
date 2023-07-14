package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.forJob.paymentExecution.PaymentExecutionService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizicgate.manager.config.AdaptersConfig;

import org.apache.commons.lang.ArrayUtils;

import org.quartz.*;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Джоб уточнения статуса платежа и последующем его исполнении.
 * @author gladishev
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 */
public class CheckPaymentExecutionJob extends ReworkingPaymentJob
{
	protected static final PaymentExecutionService paymentExecutionService = new PaymentExecutionService();
	private static final AtomicInteger counter = new AtomicInteger(0);//Счетчик текущего количества одновременно работающих джобов.

	public CheckPaymentExecutionJob() throws JobExecutionException
	{
		super();
	}

	@Override
	protected String getStateCode()
	{
		return "DISPATCHED";
	}

	@Override
	protected AtomicInteger getJobCounter()
	{
		return counter;
	}

	@Override
	protected DocumentEvent getNextEvent()
	{
		throw new UnsupportedOperationException();
	}
	@Override
	protected List<Long> getDocumentForProcess(String stateCode, int maxRows, String jobName, String paymentSortOrder) throws BusinessException
	{
		String iqwUuid = null;
		if (!ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).isMultiblockMode())
		{
			AdaptersConfig adaptersConfig = ConfigFactory.getConfig(AdaptersConfig.class);
			iqwUuid = adaptersConfig.getCardTransfersAdapter().getUUID();
		}

		JobRefreshConfig jobRefreshConfig = ConfigFactory.getConfig(JobRefreshConfig.class);
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.SECOND, - jobRefreshConfig.getDocumentUpdateWaitingTime());
		return businessDocumentService.findByStateForJob(stateCode, maxRows, jobName, fromDate, paymentSortOrder, jobRefreshConfig.getProviderNumbersExclude(), iqwUuid);
	}

	@Override
	protected void processDocument(BusinessDocument document) throws Exception
	{
		if (!(document instanceof ConvertableToGateDocument))
			return;

		GateDocument gateDocument = ((ConvertableToGateDocument) document).asGateDocument();
		StateUpdateInfo updateInfo = update(gateDocument);
		if (updateInfo!= null && updateInfo.getState() == null && updateInfo.getNextProcessDate() != null)
		{
			paymentExecutionService.add(document.getId(), getJobName(), updateInfo.getNextProcessDate());
			return;
		}
		if (updateInfo == null || updateInfo.getState() == null)//статус документа пока неизвестен
		{

			//увеличиваем счетчик проверок документа
			int maxCount = ConfigFactory.getConfig(JobRefreshConfig.class).getRestrictionNumberRequests(getJobSimpleName());
			if (paymentExecutionService.getCount(document.getId(), getJobName())+1 > maxCount)
			{
				paymentExecutionService.delete(document.getId(), getJobName());
				document.setCheckStatusCountResult(true);
				dispatch(document, DocumentEvent.ERROR, null);
			}
			else
				paymentExecutionService.add(document.getId(), getJobName());
			return;
		}

		paymentExecutionService.delete(document.getId(), getJobName());

		State state = updateInfo.getState();
		dispatch(document, DocumentEvent.valueOf(state.getCode()), state.getDescription());
	}

	protected StateUpdateInfo update(GateDocument gateDocument) throws Exception
	{
		return GateSingleton.getFactory().service(DocumentService.class).update(gateDocument);
	}
}
