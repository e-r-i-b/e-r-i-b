package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.InvalidTargetException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.listener.ListenerMessageReceiver;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.manager.services.persistent.documents.UpdateDocumentServiceImpl;
import com.rssl.phizicgate.sbrf.utils.DocumentOfflineHandlerHelper;
import com.rssl.phizicgate.sbrf.ws.CODMessageData;
import com.rssl.phizicgate.sbrf.ws.Constants;
import com.rssl.phizicgate.sbrf.ws.listener.handler.*;
import org.w3c.dom.Document;

import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author Roshka
 * @ created 18.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class CODListenerMessageReceiver extends AbstractService implements ListenerMessageReceiver
{
	protected static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);
	private static String ABONENT_ESK = "ESK";
	private static final OfflineRequestHandler errorPaymentHandler = new CancelPaymentHandler();

	private List<String> baseRequestList = new ArrayList<String>(6);


	private Boolean isInit = false;
	MessagingConfig messagingConfig = null;

	private void inititialize() throws GateException
	{
		if(!isInit)
		{
			StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
			serviceStarter.startMBeans();

			baseRequestList.add(0,"confirmation_offline_a");
			baseRequestList.add(1,"error_offline_a");
		}
		isInit = true;
	}

	public CODListenerMessageReceiver(GateFactory factory) throws GateException
	{
		super(factory);

		messagingConfig = ConfigurationLoader.load(Constants.INCOMING_MESSAGES_CONFIG);
	}

	public String handleMessage(String request) throws GateException
	{
		long begin = new Date().getTime();
		String requestType = "unknown";
		String responce = null;
		String responceType = "unknown";
		String messageId = "0";
		try
		{
			inititialize();
			log.trace("получено offline сообщение из ЦОД:" + request);

			InternalMessageInfoContainer container = OfflineMessageParser.processMessage(messagingConfig, request);
			requestType = container.getMessageTag();
			CODMessageData message = container.getMessage();
			messageId = message.getId();
			
			log.debug("Пытаемся обработать offline сообщение:" + requestType);
			boolean result = handleRequest(container);

			log.debug("формирование ответа на offline сообщение:" + requestType);

			if(result)
			{
				responce = convertXmlToString(ResponseCreator.createAcknowledge(messageId, message.getDate(), ABONENT_ESK));
				responceType = ResponseCreator.SUCCESS_MESSAGE_TYPE;
			}
			else
			{
				responce = createErrorMessage(message);
				responceType = ResponseCreator.ERROR_MESSAGE_TYPE;
			}
		}
		catch( InvalidTargetException ex)
		{
			responce = processInvalidTargetException(ex, request);
			responceType = ResponseCreator.ERROR_MESSAGE_TYPE;
		}
		catch (Exception ex)
		{
			log.error("ошибка при обработке офлайн сообщения", ex);
			responce = createErrorMessage(null);
			responceType = ResponseCreator.ERROR_MESSAGE_TYPE;
		}
		finally
		{
			finalizeHandle();
			writeToLog(request, requestType, messageId, responce, responceType, new Date().getTime() - begin);
		}
		log.debug("ответ на offline сообщение отправлен:" + requestType);
		return responce;
	}

	protected void finalizeHandle()
	{
		UpdateDocumentServiceImpl.resetPersister();
	}

	/**
	 * Обработать сообщение, не относящееся к объектам системы
	 * по умолчанию возвращается тикет ERROR_VALIDATE
	 * @param ex исключение
	 * @param xmlMessage оригинальное сообщение
	 * @return ответ
	 * @throws GateException
	 */
	protected String processInvalidTargetException(InvalidTargetException ex, String xmlMessage) throws GateException
	{
		log.error("ошибка при обработке офлайн сообщения", ex);
		return createErrorMessage(null);
	}

	private boolean handleRequest(InternalMessageInfoContainer container) throws GateException, GateLogicException
	{
		int index = baseRequestList.indexOf(container.getMessageTag());
		switch(index)
		{
			case 0://confirmation_offline_a
					return handlePayment(container,true);
			case 1://error_offline_a
					return handlePayment(container, false);
			default:
				throw new GateException("Неизвестный тип сообщения: " + container.getMessageTag());
		}
	}

	protected String composeDocumentExternalId(InternalMessageInfoContainer container)
	{
		Calendar messageDate = container.getMessage().getParentMessageDate();
		return StringHelper.getEmptyIfNull(container.getLink()) + Constants.EXTERNAL_ID_DELIMITER
				+ (messageDate != null ? XMLDatatypeHelper.formatDate(messageDate) : "");
	}

	protected boolean handlePayment(InternalMessageInfoContainer container, boolean isSuccess) throws GateException, GateLogicException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);

		String externalId = composeDocumentExternalId(container);

		GateDocument document = updateDocumentService.find(externalId);

		if(document == null)
		{
			String message = "Статус документа с идентификатором " + externalId + " не может быть изменен. Документ не найден в БД ИКФЛ";
			log.error(message);
			throw new InvalidTargetException(message);
		}

		OfflineRequestHandler handler = getPaymentHandler(isSuccess, document.getType());
		return handler.handleMessage(container, document);
	}

	protected OfflineRequestHandler getPaymentHandler(boolean isConfirm, Class documentClass)
	{
		if(isConfirm)
			return DocumentOfflineHandlerHelper.getSuccessOfflineRequestHandler(documentClass);
		else
			return errorPaymentHandler;
	}

	private void writeToLog(String request, String requestType, String messageId, String responce, String responceType, Long executionTime)
	{
		try
		{
			MessageLogWriter codLogService = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			logEntry.setMessageRequest(request);
			logEntry.setMessageRequestId(messageId);
			logEntry.setMessageType(requestType);

			logEntry.setMessageResponseId(messageId);
			logEntry.setMessageResponse(responce);

			logEntry.setExecutionTime(executionTime);
			logEntry.setSystem(System.cod);

			codLogService.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи сообщения в журнал", e);
		}
	}

	private String convertXmlToString(Document message) throws GateException
	{
		try
		{
			return XmlHelper.convertDomToText(message);
		}
		catch(TransformerException ex)
		{
			throw new GateException(ex);
		}
	}

	private String createErrorMessage(CODMessageData message) throws GateException
	{
		String messageId = "0";
		Calendar messageDate = DateHelper.getCurrentDate();
		if (message != null)
		{
			messageId = message.getId();
			messageDate = message.getDate();
		}
		return convertXmlToString(ResponseCreator.createRefusal(messageId, messageDate, ABONENT_ESK, DefaultErrorMessageHandler.ERROR_VALIDATE_CODE, "error processing message"));
	}
}
