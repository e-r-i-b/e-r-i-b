package com.rssl.phizicgate.shopclient;

import com.rssl.phizgate.common.providers.ProviderPropertiesService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ShopListenerConfig;
import com.rssl.phizic.config.invoicing.InvoicingConfig;
import com.rssl.phizic.gate.einvoicing.NotificationStatus;
import com.rssl.phizic.gate.einvoicing.ShopNotification;
import com.rssl.phizic.gate.einvoicing.ShopProvider;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.common.types.shop.ShopConstants;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.shopclient.generated.*;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.*;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPBody;

/**
 * @author gulov
 * @ created 12.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 *  ласс-обертка дл€ вызова веб-сервиса, который заполн€ет информацию по документу и принимает
 * оповещение о статусе оплаты документа
 */
public class ShopInfoServiceWrapper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final System SYSTEM_ID = System.shop;
	private static final ProviderPropertiesService providerPropertiesService = new ProviderPropertiesService();
	/**
	 *  лиентска€ часть веб-сервиса
	 */
	private String url;
	private String systemId = null;

	/**
	 * ѕоставщик, в адрес которого отправл€етс€ сообщение
	 * @param provider поставщик, в адрес которого отправл€етс€ сообщение
	 */
	public ShopInfoServiceWrapper(ShopProvider provider) throws GateException
	{
		if (providerPropertiesService.isUseESBProvider(provider.getId()))
		{
			this.url = ConfigFactory.getConfig(ShopListenerConfig.class).getShopEsbUrl();
			this.systemId = provider.getCodeRecipientSBOL();
		}
		else
		{
			this.url = provider.getUrl();
		}

	}

	private ShopInfoServiceSoapBindingStub getStub()
	{
		try
		{
			ShopInfoServiceImplLocator locator = new ShopInfoServiceImplLocator();
			locator.setShopInfoServicePortEndpointAddress(url);

			return (ShopInfoServiceSoapBindingStub) locator.getShopInfoServicePort();
		}
		catch (ServiceException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * ѕолучить данные по заказам
	 * @param eribUIDArray - массив идентификаторов операций
	 * @throws Exception
	 */
	public DocInfoRsType getOrdersInfo(String[] eribUIDArray) throws Exception
	{
		DocInfoRqType docInfo = new DocInfoRqType();

		DateFormat simpleDateFormat = ShopConstants.getDateFormat();

		docInfo.setRqUID(new RandomGUID().getStringValue());
		docInfo.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));
		docInfo.setDocuments(eribUIDArray);

		ShopInfoServiceSoapBindingStub stub = getStub();
		Calendar start = Calendar.getInstance();
		try
		{
			return stub.docInfo(docInfo);
		}
		finally
		{
			try
			{
				writeToLog(stub._getCall().getMessageContext(), DateHelper.diff(Calendar.getInstance(), start));
			}
			catch (Exception ex)
			{
				log.error("ѕроблемы с записью в журнал сообщений", ex);
			}
		}
	}

	/**
	 * ѕолучить данные по состо€нию заказа
	 * @param eribUID - идентификатор операций
	 * @param paidBy - Ќаименование типа карты, с которой был оплачен документ (Visa Classic, Maestro и т.п.)
	 */
	public DocInfoStatRsType getOrdersInfoStat(String eribUID, String paidBy) throws RemoteException
	{
		DocInfoStatRqType docInfoStat = new DocInfoStatRqType();

		DateFormat simpleDateFormat = ShopConstants.getDateFormat();

		docInfoStat.setRqUID(new RandomGUID().getStringValue());
		docInfoStat.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));
		docInfoStat.setSystemId(systemId);
		DocInfoStatRqDocumentsType document = new DocInfoStatRqDocumentsType(eribUID, paidBy);
		docInfoStat.setDocuments(document);

		ShopInfoServiceSoapBindingStub stub = getStub();
		Calendar start = Calendar.getInstance();
		try
		{
			return stub.docInfoStat(docInfoStat);
		}
		finally
		{
			try
			{
				writeToLog(stub._getCall().getMessageContext(), DateHelper.diff(Calendar.getInstance(), start));
			}
			catch (Exception ex)
			{
				log.error("ѕроблемы с записью в журнал сообщений", ex);
			}
		}
	}


	/**
	 * ќповещение о статусе оплаты документа
	 *
	 * @param source - список оповещений
	 * @return - список оповещний с обновленными статусами
	 */
	public Collection<ShopNotification> notify(Collection<ShopNotification> source) throws Exception
	{
		DocStatNotRqType docStatNot = new DocStatNotRqType();

		DateFormat dateFormat = ShopConstants.getDateFormat();

		docStatNot.setRqUID(new RandomGUID().getStringValue());
		docStatNot.setRqTm(dateFormat.format(Calendar.getInstance().getTime()));
		docStatNot.setSystemId(systemId);

		int notificationLimit = ConfigFactory.getConfig(ShopListenerConfig.class).getNotificationDocumentLimit();
		int size = source.size() < notificationLimit ? source.size() : notificationLimit;
		StatNotRqDocumentType[] documents = new StatNotRqDocumentType[size];
		int i = 0;
		Map<String, ShopNotification> notificationMap = new HashMap<String, ShopNotification>(size);
		for (ShopNotification notification : source)
		{
			if (i >= notificationLimit)
				break;

			String uuid = notification.getUuid();
			if (notificationMap.containsKey(uuid))
				//в списке source может находитс€ несколько оповещений с одинаковым uuid (и различными externalId),
				// но в ответе нам приходит только uuid и статус, поэтому ограничиваемс€ одним uuid
				continue;
			else
				notificationMap.put(uuid, notification);

			StatNotRqDocumentType document = new StatNotRqDocumentType();
			document.setERIBUID(uuid);
			document.setId(notification.getExternalId());
			document.setState(notification.getState());
			//если заказ проходит через  —Ў то utrrno передавать не нужно
			if (systemId == null)
				document.setUTRRNO(notification.getUtrrno());

			documents[i++] = document;
		}

		docStatNot.setDocuments(documents);

		ShopInfoServiceSoapBindingStub stub = getStub();
		Calendar start = Calendar.getInstance();
		DocStatNotRsType response;
		try
		{
			response = stub.docStatNot(docStatNot);
		}
		finally
		{
			try
			{
				writeToLog(stub._getCall().getMessageContext(), DateHelper.diff(Calendar.getInstance(), start));
			}
			catch (Exception ex)
			{
				log.error("ѕроблемы с записью в журнал сообщений", ex);
			}
		}
		Long maxCount = ConfigFactory.getConfig(InvoicingConfig.class).getOrderNotificationCount();
		for (ResultType document : response.getDocuments())
		{
			ShopNotification notification = notificationMap.get(document.getERIBUID());
			if (document.getStatus().getStatusCode() == 0L)
			{
				notification.setNotifStatus(NotificationStatus.NOTIFIED);
				notification.setNotifTime(Calendar.getInstance());
			}
			else
			{
				if (notification.getNotifCount() > maxCount)
				{
					notification.setNotifStatus(NotificationStatus.ERROR);
					notification.setNotifStatusDescription(document.getStatus().getStatusDesc());
				}
				else
					notification.setNotifCount(notification.getNotifCount()+1);
			}
		}
		return notificationMap.values();
	}

	private static void writeToLog(MessageContext messageContext, Long duration) throws Exception
	{
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		Message request = messageContext.getRequestMessage();
		Message response = messageContext.getResponseMessage();
		SOAPBody requestBody = AxisLoggerHelper.getBody(request);
		SOAPBody responseBody = AxisLoggerHelper.getBody(response);

		logEntry.setSystem(SYSTEM_ID);

		logEntry.setMessageRequestId(AxisLoggerHelper.resolveMessageId(requestBody));
		logEntry.setMessageType(AxisLoggerHelper.resolveMessageShopType(requestBody));
		logEntry.setMessageRequest(AxisLoggerHelper.serializeMessage(request));
		logEntry.setMessageResponseId(AxisLoggerHelper.resolveMessageId(responseBody));
		logEntry.setMessageResponse(AxisLoggerHelper.serializeMessage(response));
		logEntry.setExecutionTime(duration);

		MessageLogWriter writer = MessageLogService.getMessageLogWriter();
		writer.write(logEntry);
	}
}
