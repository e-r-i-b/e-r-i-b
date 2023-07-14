package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.forJob.paymentExecution.PaymentExecutionService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizicgate.manager.routing.Adapter;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * јтодокат биллинговых платежей в шину.
 *
 * @author bogdanov
 * @ created 25.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class ResendESBPaymentJob extends ReworkingPaymentJob
{
	private static final AtomicInteger counter = new AtomicInteger(0);//—четчик текущего количества одновременно работающих джобов.
	private static final PaymentExecutionService paymentExecutionService = new PaymentExecutionService();

	public ResendESBPaymentJob() throws JobExecutionException
	{
	}

	protected String getStateCode()                                                                        
	{
		return UNKNOW_STATE;
	}

	@Override
	protected DocumentEvent getNextEvent()
	{
		return DocumentEvent.SPECIFY;
	}

	protected AtomicInteger getJobCounter()
	{
		return counter;
	}

	@Override
	protected void addToProcessed(Long docId, String additionalJobData) throws BusinessException
	{
		super.addToProcessed(docId, additionalJobData);
		paymentExecutionService.add(docId, this.getJobName());
	}

	@Override
	protected List<Long> getDocumentForProcess(String stateCode, int maxRows, String jobName, String paymentSortOrder) throws BusinessException
	{
		Adapter adapter = ESBHelper.getIQWaveAdapter();
		JobRefreshConfig config = ConfigFactory.getConfig(JobRefreshConfig.class);
		return businessDocumentService.findByStateForJob(stateCode, maxRows, jobName, adapter.getUUID(), paymentSortOrder,
				config.getNumberOfHourAfterDocumentIsExpired(getJobSimpleName()), config.getNumberOfResendPayment());
	}

	protected void processUnknownState(BusinessDocument document) throws BusinessException
	{
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		if (StringHelper.isNotEmpty(payment.getAuthorizeCode()))
		{
			businessDocumentService.addOrUpdate(document);
		}
		addToProcessed(document);
	}
}
