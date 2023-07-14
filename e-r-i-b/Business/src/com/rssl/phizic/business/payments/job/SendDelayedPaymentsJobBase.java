package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.BusinessOfflineDocumentException;
import com.rssl.phizic.business.payments.BusinessSendTimeOutException;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;
import org.quartz.JobExecutionException;

import java.util.Calendar;

/**
 * Базовый джоб отправки отложенных платежей.
 * @author vagin
 * @ created 26.03.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class SendDelayedPaymentsJobBase extends ReworkingPaymentJob
{
	protected SendDelayedPaymentsJobBase() throws JobExecutionException{}

	private Integer getNumberOfHourAfterDocumentIsExpired()
	{
		return ConfigFactory.getConfig(JobRefreshConfig.class).getNumberOfHourAfterDocumentIsExpired(getJobSimpleName());
	}

	private boolean checkDate(BusinessDocument document)
	{
		if (document == null || document.getOperationDate() == null)
			return false;

		Integer actualHourInterval = getNumberOfHourAfterDocumentIsExpired();
		if (actualHourInterval == null)
			return false;

		return document.getOperationDate().before(DateHelper.getPreviousHours(actualHourInterval));
	}

	protected void dispatch(BusinessDocument document, DocumentEvent nextEvent, String description) throws Exception
	{
		StateMachineExecutor executor = getStateMachineExecutor(document);

		if (checkDate(document))
		{
			log.debug(DateHelper.formatDateToStringWithSlash2(Calendar.getInstance()) + ": переводим в статус 'отказано' (REFUSE)" +
					" документ id = " + document.getId() +
					", дата документа = " + DateHelper.formatDateToStringWithSlash2(document.getOperationDate()) +
					", установленное время актуальности документов = " + getNumberOfHourAfterDocumentIsExpired() + " ч.");

			process(executor, new ObjectEvent(DocumentEvent.REFUSE, JOB_EVENT_TYPE, "Приостановлен"), document);
			return;
		}

		try
		{
			log.debug("Отсылаем документ " + document.getId() + " во внешнюю систему.");
			process(executor, new ObjectEvent(nextEvent, JOB_EVENT_TYPE, description), document);
		}
		catch (BusinessSendTimeOutException e)
		{
			//При ошибке при проведении платежа при таймауте стандарто в unknow
			log.error("При обработке платежа id = " + document.getId() + " возникла ситуация time-out. Переводим документ в статус 'UNKNOW'", e);
			process(executor, new ObjectEvent(DocumentEvent.DOUNKNOW, JOB_EVENT_TYPE), document);
		}
		catch (BusinessTimeOutException e)
		{
			//При ошибке при проведении платежа при таймауте стандарто в unknow
			log.error("При обработке платежа id = " + document.getId() + " возникла ситуация time-out. Переводим документ в статус 'UNKNOW'", e);
			process(executor, new ObjectEvent(DocumentEvent.DOUNKNOW, JOB_EVENT_TYPE), document);
		}
		catch (BusinessOfflineDocumentException ignore)
		{
			//ничего не делаем, проведем документ в следующий раз, когда запустят соответствующий сервис.
			addToProcessed(document);
		}
		catch (Exception e)
		{
			//при других ошибках в отказано
			log.error("Ошибка обработки документа " + document.getId() + ". Переводим в статус 'отказано'.", e);
			DocumentHelper.decrementPromoCode(document);
			process(executor, new ObjectEvent(DocumentEvent.REFUSE, JOB_EVENT_TYPE, "Приостановлен"), document);
		}
	}
}
