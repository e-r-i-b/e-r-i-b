package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.StringHelper;
import org.quartz.JobExecutionException;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Джоб получения списка билетов из модуля einvoicing для документа оплаты брони аэрофлота
 * @author gladishev
 * @ created 09.03.14
 * @ $Author$
 * @ $Revision$
 */
public class AirlineReservationPaymentTicketsJob extends ReworkingPaymentJob
{
	private static final AtomicInteger counter = new AtomicInteger(0);//Счетчик текущего количества одновременно работающих джобов.
	/**
	 * ctor
	 */
	public AirlineReservationPaymentTicketsJob() throws JobExecutionException
	{
		super();
	}

	@Override
	protected String getStateCode()
	{
		return DocumentEvent.TICKETS_WAITING.name();
	}

	@Override
	protected AtomicInteger getJobCounter()
	{
		return counter;
	}

	@Override
	protected void processDocument(BusinessDocument document) throws Exception
	{
		log.trace("Получение списка билетов по документу id=" + document.getId());

		LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());
		String ticketInfo = GateSingleton.getFactory().service(ShopOrderService.class).getTicketInfo(ShopHelper.get().getOrderUuidByPayment(document.getId()));
		if (StringHelper.isNotEmpty(ticketInfo))
			ShopHelper.get().setTicketsInfo((JurPayment)document, ticketInfo);
		else
			log.error("Не заполнен список билетов по документу id=" + document.getId());
	}

	@Override
	protected List<Long> getDocumentForProcess(String stateCode, int maxRows, String jobName, String paymentSortOrder) throws BusinessException
	{
		JobRefreshConfig jobRefreshConfig = ConfigFactory.getConfig(JobRefreshConfig.class);
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.SECOND, - jobRefreshConfig.getDocumentUpdateWaitingTime());
		return businessDocumentService.findByStateForJob(stateCode, maxRows, jobName, fromDate, paymentSortOrder, null, null);
	}
}
