package com.rssl.phizic.scheduler.jobs.gorod;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.documents.UpdateDocumentServiceImpl;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.service.StartupServiceDictionary;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.HashMap;
import java.util.List;

/**
 * @author Gainanov
 * @ created 07.08.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Джоб для проводки городских платежей при работе с ЦОД.
 * Этот джоб вытаскивает проведенные в цоде городские платежи  и проводит их в городе
 */
public class GorodPaymentsCarryJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	public GorodPaymentsCarryJob() throws JobExecutionException
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		UpdateDocumentService updateService = null;
		try
		{
//			 юзается вместо конструкции GateSingleton.getFactory().service(UpdateDocumentService.class);
//           в силу того, что в данном случае нам необходимы полные айдишники клиентов (то есть, <client_id>|<external_system_id>)
			updateService = new UpdateDocumentServiceImpl(GateSingleton.getFactory());
		}
		catch (Exception ex)
		{
			throw new JobExecutionException(ex);
		}
		List<GateDocument> list = null;
		try
		{
			list = updateService.findUnRegisteredPayments(new State("PARTLY_EXECUTED"));
		}
		catch (GateException e)
		{
			throw new JobExecutionException(e);
		}
		catch (GateLogicException e)
		{
			throw new JobExecutionException(e);
		}

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

			DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
			try
			{
				//отправляем платеж в город и подтверждаем его
				documentService.confirm(payment);
				updateService.update(payment, new DocumentCommand(DocumentEvent.EXECUTE, new HashMap<String, Object>()));
			}
			catch (GateMessagingException e)
			{
				try
				{
					documentService.recall(doc);
				}
				catch (GateException e1)
				{
					throw new JobExecutionException(e1);
				}
				catch (GateLogicException e1)
				{
					throw new JobExecutionException(e1);
				}
				finally
				{
					log.debug("Не удалось провести платеж в ИСППН \" Город\"!", e);
				}
			}
			catch (GateException e)
			{
				throw new JobExecutionException(e);
			}
			catch (GateLogicException e)
			{
				throw new JobExecutionException(e);
			}
		}
	}
}
