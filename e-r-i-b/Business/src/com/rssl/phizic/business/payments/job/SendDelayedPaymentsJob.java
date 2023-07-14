package com.rssl.phizic.business.payments.job;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.business.receptiontimes.TimeMatching;
import org.quartz.JobExecutionException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Джоб находит все платежи с отложенной обработкой и переводит в состояние обрабатывается, если бек-офис готов принять такой платеж
 *
 * @author egorova
 * @ created 23.09.2009
 * @ $Author$
 * @ $Revision$
 */
public class SendDelayedPaymentsJob extends SendDelayedPaymentsJobBase
{
	private static final AtomicInteger counter = new AtomicInteger(0);//Счетчик текущего количества одновременно работающих джобов.

	private static final ReceptionTimeService receptionTimeService = new ReceptionTimeService();
	private static final String DELAYED_DISPATCH_STATE = "DELAYED_DISPATCH";

	/**
	 * конструктор джоба
	 * @throws JobExecutionException
	 */
	public SendDelayedPaymentsJob() throws JobExecutionException
	{
		super();
	}

	protected String getStateCode()
	{
		return DELAYED_DISPATCH_STATE;
	}

	protected AtomicInteger getJobCounter()
	{
		return counter;
	}

	@Override
	protected boolean beforeFireEventCheckDocument(BusinessDocument document) throws BusinessException
	{
		boolean isTime = true;
		try
		{
			getLog().trace("Определяем время приема для документа " + document.getId());
			isTime = receptionTimeService.getWorkTimeInfo(document).isWorkTimeNow() == TimeMatching.RIGHT_NOW;
		}
		catch (Exception e)
		{
			//ошибки получения времени приема документа не должны отражаться на статусе платежа
			getLog().error("Ошибка получения времени приема документа " + document.getId() + ". Пропускаем документ.", e);
			addToProcessed(document);
			return false;
		}

		//если не выходной и по времени "влезает" отправляем
		if (isTime)
			return true;
		
		getLog().debug("Время приема для документа " + document.getId() + " еще не настало. Пропускаем документ.");
		addToProcessed(document);
		return false;
	}
}
