package com.rssl.phizic.scheduler.jobs.gorod.sevb;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.*;

import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 22.02.2008
 * @ $Author$
 * @ $Revision$
 */

/***
 * Джоб для проводки и обновления(не протухания) платежей в городе
 */
public class GorodPaymentsCarryJob extends BaseJob implements StatefulJob, InterruptableJob
{
	public static final String OK_ERROR_CODE = "0";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private volatile boolean  isInterrupt = false;

	/**
	 * инициализация джоба
	 */
	public GorodPaymentsCarryJob() throws JobExecutionException, InvalidClassException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			//сначала подготавливаем все посланные в ритейл, но не проведенные городские платежи
//Закоменчен из-за проблем с синхронизацией статусов
//утверждается, что сборщик работает раз в 40 д, поэтому необходимости в обновлениии платежа в городе нет
//			prepareGorodPayments();
			//подтверждаем в городе все городские платежи, проведенные в ритейле
			confirmGorodPayments();
		}
		catch (Exception e)
		{
			throw new JobExecutionException(e);
		}
	}

	/**
	 * подтвердить городской платеж в городе после проводки его в ретейле
	 */
	private void confirmGorodPayments() throws GateLogicException, GateException
	{
		UpdateDocumentService updateService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		List<GateDocument> list = updateService.findUnRegisteredPayments(new State("PARTLY_EXECUTED", "Принят"));
		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		for (GateDocument doc : list)
		{
			// если требуется остановится
			if(isInterrupt)
			{
				break;
			}

			if (!(doc instanceof SynchronizableDocument))
			{
				continue;
			}
			SynchronizableDocument payment = (SynchronizableDocument) doc;
			if (payment.getType() != AccountPaymentSystemPayment.class)
			{
				continue;
			}
			try
			{
				LogThreadContext.setLoginId(doc.getInternalOwnerId());
				log.debug("Подтверждаем в городе платеж " + doc.getId());
				documentService.confirm(doc);
			}
			catch (InactiveExternalSystemException e)
			{
				//внешняя система в данный момент неактивна, обработку платежа пропускаем
				log.error("Внешняя система неактивна. Обрабатываемый платеж id = " + doc.getId(), e);
			}
			catch (GateLogicException e)
			{
				log.info("Ошибка при подтверждении платежа в Городе. Приостанавливаем платеж id=" + doc.getId(), e);
				Map<String, Object> additionalFields = new HashMap<String, Object>();
				additionalFields.put(DocumentCommand.ERROR_TEXT, e.getMessage());
				updateService.update(payment, new DocumentCommand(DocumentEvent.ERROR, additionalFields));
			}
			catch (GateException e)
			{
				log.info("Ошибка при подтверждении платежа в Городе. Приостанавливаем платеж id=" + doc.getId(), e);
				Map<String, Object> additionalFields = new HashMap<String, Object>();
				additionalFields.put(DocumentCommand.ERROR_TEXT, e.getMessage());
				updateService.update(payment, new DocumentCommand(DocumentEvent.ERROR, additionalFields));
			}
			catch (Exception e)
			{
				log.error("ошибка подтверждения платежа id=" + doc.getId(), e);
			}
			finally
			{
				LogThreadContext.setLoginId(null);
			}
		}
	}

	/**
	 * Обновить платеж в городе, на тот случай если он был убит сборщиком мусора
	 * После этого обновляем основание платжеа в ретейл, чтоб там была акуальная информация из города
	 */
	private void prepareGorodPayments() throws GateLogicException, GateException
	{
		UpdateDocumentService updateService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		List<GateDocument> list = updateService.findUnRegisteredPayments(new State("DISPATCHED", "Принят"));
		for (GateDocument doc : list)
		{
			if (!(doc instanceof SynchronizableDocument))
			{
				continue;
			}
			SynchronizableDocument payment = (SynchronizableDocument) doc;
			if (payment.getType() != AccountPaymentSystemPayment.class)
			{
				continue;
			}
			try
			{
				BusinessDocumentOwner documentOwner = ((BusinessDocument) doc).getOwner();
				if (!documentOwner.isGuest())
					LogThreadContext.setLoginId(documentOwner.getLogin().getId());
				log.debug("Обновляем в городе платеж " + doc.getId());
				documentService.send(doc);
				//обновляем в бизнесе измененные поля
				log.debug("Обновляем в бизнесе обновленный в городе платеж " + doc.getId());
				updateService.update(payment);
			}
			catch (Exception e)
			{
				log.error("ошибка при подготовке платежа id=" + doc.getId(), e);
			}
			finally
			{
				LogThreadContext.setLoginId(null);
			}
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}