package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.utils.DateHelper;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Джоб для уточнения статуса документов в статусе UNKNOW, обрабатываемых через веб-сервис АС "ЦОД"
 * @author gladishev
 * @ created 06.06.2014
 * @ $Author$
 * @ $Revision$
 */

public class DepoCodPaymentsExecutionJob extends CheckPaymentExecutionJob
{
	private static final AtomicInteger counter = new AtomicInteger(0);//Счетчик текущего количества одновременно работающих джобов.

	public DepoCodPaymentsExecutionJob() throws JobExecutionException
	{
		super();
	}

	@Override
	protected String getStateCode()
	{
		return "UNKNOW";
	}

	@Override
	protected AtomicInteger getJobCounter()
	{
		return counter;
	}

	@Override
	protected List<Long> getDocumentForProcess(String stateCode, int maxRows, String jobName, String paymentSortOrder) throws BusinessException
	{
		return businessDocumentService.getDepoCODDocuments(stateCode, maxRows, jobName);
	}

	@Override
	protected void processDocument(BusinessDocument document) throws Exception
	{
		Calendar date = DateHelper.getPreviousHours(ConfigFactory.getConfig(JobRefreshConfig.class).getNumberOfHourAfterDocumentIsExpired(getJobSimpleName()));
		if (document.getOperationDate().before(date))
		{
			paymentExecutionService.delete(document.getId(), getJobName());
			document.setCheckStatusCountResult(true);
			dispatch(document, DocumentEvent.ERROR, null);
			return;
		}

		super.processDocument(document);
	}

	@Override
	protected StateUpdateInfo update(GateDocument gateDocument) throws Exception
	{
		try
		{
			return super.update(gateDocument);
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
		catch (GateTimeOutException ignore)
		{
			return null;
		}
	}
}
