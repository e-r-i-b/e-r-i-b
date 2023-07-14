package com.rssl.phizicgate.sbrf.ws.mock.offline;

import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.listener.ListenerMessageReceiver;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbrf.ws.mock.AgreementCancelationQHandler;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

/**
 * @author Omeliyanchuk
 * @ created 07.05.2008
 * @ $Author$
 * @ $Revision$
 */

public class OfflineHandlingJob extends BaseJob implements StatefulJob
{
	private Log log;
	protected static final String LOCK_CLIENT_XML_PATH = "com/rssl/phizicgate/sbrf/ws/mock/xml/lockClient_esk_q.xml";
	protected static final String UNLOCK_CLIENT_XML_PATH = "com/rssl/phizicgate/sbrf/ws/mock/xml/unlockClient_esk_q.xml";

	private static Map<Class<? extends GateDocument>, OfflineMockHandler> handlersMapByClass = null;

	static
	{
		handlersMapByClass = new HashMap<Class<? extends GateDocument>, OfflineMockHandler>();
		handlersMapByClass.put(AccountIntraBankPayment.class, new BaseOfflineHandler());
		handlersMapByClass.put(ClientAccountsTransfer.class, new BaseOfflineHandler());
		handlersMapByClass.put(AbstractRUSPayment.class, new BaseOfflineHandler());
		handlersMapByClass.put(AccountToCardTransfer.class, new BaseOfflineHandler());
		handlersMapByClass.put(RUSTaxPayment.class, new BaseOfflineHandler());
//		handlersMapByClass.put("agreementCancellation_q", new AgrementCancelationOfflineHandler());
//		handlersMapByClass.put("agreementCancellationMandatory_q", new AgrementCancelationBankOfflineHandler());
//		handlersMapByClass.put("аgreementCancellationWithoutCharge_q", new AgrementCancelationOfflineHandler());
		handlersMapByClass.put(LossPassbookApplicationClaim.class, new LossPassbookOfflineHandler());
		handlersMapByClass.put(AbstractJurTransfer.class, new BaseOfflineHandler());
		handlersMapByClass.put(AccountPaymentSystemPayment.class, new BaseOfflineHandler());
	}

	public OfflineHandlingJob() throws JobExecutionException
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
		ListenerMessageReceiver listenerMessageReceiver = GateSingleton.getFactory().service(ListenerMessageReceiver.class);
		UpdateDocumentService updateService = GateSingleton.getFactory().service(UpdateDocumentService.class);

		if (!AgreementCancelationQHandler.PerantID.isEmpty())
		{
			Iterator it = AgreementCancelationQHandler.PerantID.iterator();
			for (int i = 0; i < AgreementCancelationQHandler.PerantID.size(); i++)
			{
				if (it.hasNext())

					try
					{
						AgrementCancelationOfflineHandler handler = new AgrementCancelationOfflineHandler();
						Document result = handler.createSuccessAnswer((String) it.next());
						listenerMessageReceiver.handleMessage(XmlHelper.convertDomToText(result));
					}
					catch (Exception ee)
					{
						log.error("Ошибка при попытке вызвать offline сообщение", ee);
					}
				;
			}
			AgreementCancelationQHandler.PerantID.clear();
		}

		try
		{
			ApplicationConfig appConfig = ApplicationConfig.getIt();
			String clientId = null;
			List<GateDocument> list = updateService.findUnRegisteredPayments(new State("DISPATCHED"));
			for (GateDocument gateDocument : list)
			{
				String externalId = ((SynchronizableDocument) gateDocument).getExternalId();
				//если externalId пришел без разделителя "|", то не проверяем соответствие подстроки текущего application-а
				if (externalId == null || (externalId.indexOf("|")>=0 && externalId.indexOf(appConfig.getApplicationPrefix())<=0)
						|| externalId.indexOf("kind")>=0)
					continue;
				try
				{
					OfflineMockHandler handler = handlersMapByClass.get(gateDocument.getType());
					if (handler == null)
					{
						log.info("Пропущена обработка платежа "+gateDocument.getId()+" с типом "+ gateDocument.getType()+", т.к. не найден обработчик");
						continue;
					}
					Document result = handler.handle(gateDocument);
					listenerMessageReceiver.handleMessage(XmlHelper.convertDomToText(result));
					clientId = gateDocument.getExternalOwnerId();
				}
				catch (Exception ex)
				{
					log.error("Ошибка при попытке вызвать offline сообщение", ex);
				}
			}

			Random rnd = new Random();
			if(rnd.nextInt(50)==3 && clientId!=null)
			{
				String message = getLockOrUnlockMessage(clientId);
				if (message != null)
					listenerMessageReceiver.handleMessage(message);
			}
		}
		catch (Exception ex)
		{
			log.error("Ошибка при попытке вызвать offline сообщение", ex);
		}
		finally
		{
			OperationContext.clear();
		}
	}

	private String getLockOrUnlockMessage(String clientId)
	{
		Document document = null;
		try
		{
			Random rnd = new Random();
			Element elem = null;
			if (rnd.nextInt(1) != 1)
			{
				document = XmlHelper.loadDocumentFromResource(LOCK_CLIENT_XML_PATH);
				elem = XmlHelper.selectSingleNode(document.getDocumentElement(), "/message/lockClient_esk_q/agreement/clientId");
			}
			else
			{
				document = XmlHelper.loadDocumentFromResource(UNLOCK_CLIENT_XML_PATH);
				elem = XmlHelper.selectSingleNode(document.getDocumentElement(), "/message/unlockClient_esk_q/agreement/clientId");
			}

			elem.setTextContent(clientId);
			return XmlHelper.convertDomToText(document);
		}
		catch (Exception ex)
		{
			log.error("Ошибка при попытке вызвать offline сообщение", ex);
		}
		return null;
	}
}
