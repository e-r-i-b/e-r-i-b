package com.rssl.phizicgate.rsretailV6r20.notification.handlers;

import com.rssl.phizic.gate.notification.StatusDocumentChangeNotification;
import com.rssl.phizgate.common.payments.documents.DocumentStateChangeHandler;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.deposit.DepositOpeningClaim;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.notifications.Notification;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author eMakarov
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class AddAccountHandler extends DocumentStateChangeHandler
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final String OK_ERROR_CODE = "0";

	public void handle(List<? extends Notification> events)
	{
		for (Notification event : events)
		{
			try
			{
				StatusDocumentChangeNotification statusDocumentChangeNotification = (StatusDocumentChangeNotification) event;
				UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);

				SynchronizableDocument document = updateDocumentService.find(statusDocumentChangeNotification.getDocumentId());

				if (document == null)
				{
					log.info("Документ не найден, documentId = " + statusDocumentChangeNotification.getDocumentId()) ;
					continue;
				}
				if (DepositOpeningClaim.class.equals(document.getType()))
				{
					updateDocumentAccount((DepositOpeningClaim) document, statusDocumentChangeNotification);
				}
			}
			catch (Exception e)
			{
				log.info("Ошибка при обработке оповещенеия", e);
			}
		}
	}

	/**
	 * Вставляем в документ открытый счет
	 * @param document документ
	 * @param statusDocumentChangeNotification статус
	 * @throws GateException
	 */
	private void updateDocumentAccount(DepositOpeningClaim document, StatusDocumentChangeNotification statusDocumentChangeNotification) throws GateException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		try
		{
			String applicationKind = "" + statusDocumentChangeNotification.getApplicationKind();
			String applicationKey = statusDocumentChangeNotification.getApplicationKey();
			GateMessage gateMessage = null;
			gateMessage = service.createRequest("getReferencFromDocument_q");
			gateMessage.addParameter("applicationKind", applicationKind);
			gateMessage.addParameter("applicationKey", applicationKey);

			Document messageResponse = null;
			messageResponse = service.sendOnlineMessage(gateMessage, null);
			Element element = messageResponse.getDocumentElement();

			String errorCode = null;
			errorCode = XmlHelper.selectSingleNode(element, "errorCode").getTextContent();
			if (OK_ERROR_CODE.equals(errorCode))
			{
				document.setAccount(XmlHelper.selectSingleNode(element, "accountReferenc").getTextContent());
				UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
				updateDocumentService.update(document);
			}
			else
			{
				String errorText = null;
				errorText = XmlHelper.selectSingleNode(element, "errorText").getTextContent();
				log.info("Ошибка при определении счета для заявки с id:" + document.getId() +
						".Код ошибки:" + errorCode + ".Текст ошибки:" + errorText);
			}
		}
		catch (GateLogicException gle)
		{
			throw new GateException(gle);
		}
		catch (TransformerException te)
		{
			throw new GateException(te);
		}
	}
}