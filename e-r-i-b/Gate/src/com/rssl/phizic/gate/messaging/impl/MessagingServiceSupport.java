package com.rssl.phizic.gate.messaging.impl;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.impl.GateProperties;
import com.rssl.phizic.gate.messaging.*;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.gate.monitoring.MonitoringHandler;
import com.rssl.phizic.gate.statistics.exception.ExternalExceptionInfo;
import com.rssl.phizic.gate.statistics.exception.GateExceptionStatisticHelper;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizic.logging.monitoring.MonitoringLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 29.08.2006
 * @ $Author: basharin $
 * @ $Revision: 79042 $
 */

public abstract class MessagingServiceSupport extends AbstractService implements WebBankServiceFacade
{
	private static Map<String, String> logMonitoringTypeMap;

	private MessageLogWriter messageLogWriter;
	private MessageGenerator messageGenerator;
	private MessageInfoParser messageInfoParser;
	private MonitoringHandler monitoringHandler;

	static
	{
		logMonitoringTypeMap = new HashMap<String, String>();
		logMonitoringTypeMap.put("CardToCardRequest", MonitoringDocumentType.CARD_TRANSFER.name());
	}

	/**
	 * ctor
	 * @param factory фабрика гейта
	 * @param logWriter лог writer (может быть null если записб в лог не нужна)
	 * @param system подсистема.
	 * @param messagingConfig конфиг для сообщений
	 * @throws GateException
	 */
	protected MessagingServiceSupport(GateFactory factory, MessageLogWriter logWriter, String system, MessagingConfig messagingConfig) throws GateException
	{
		super(factory);

		this.messageLogWriter = logWriter;
		this.messageGenerator = MessageGenerator.getInstance(system);
		this.messageInfoParser = new MessageInfoParser(messagingConfig);
		this.monitoringHandler = MonitoringHandler.getInstance();
	}

	public Document sendOfflineMessage(Document message, MessageHead messageHead) throws GateLogicException, GateException
	{
		MessageInfo messageInfo = null;
		MessageData request = null;
		long begin = 0;
		long end = 0;
		MessageData response = null;
		String responceTag = null;
		String requestTag = messageInfoParser.getRequestType(message);
		monitoringHandler.checkRequest(requestTag);
		try
		{
			messageInfo = messageInfoParser.parse(message);

			request = buildMessage(message, messageInfo, messageHead);
			begin = new Date().getTime();
			response = makeRequest(request, messageInfo);
			end = new Date().getTime();

			ResponseHandler responseHandler = parseResponse(response, messageInfo);
			monitoringHandler.processRequest(requestTag,end-begin,responseHandler.getErrorCode());

			responceTag = responseHandler.getResponceTag();
			if (!responseHandler.isSuccess())
				processError(messageInfo, response, responseHandler);

			if (!responseHandler.isVoid())
				throw new GateException("В ответ на оффлайн сообщение получен онлайн ответ");

			return (Document) responseHandler.getBody();
		}
		catch (GateTimeOutException e)
		{
			monitoringHandler.processError(requestTag);
			throw e;
		}
		finally
		{   //в любом случае пишем входящее сообщение в лог
			if(end==0)
				end = new Date().getTime();

			writeToLog(messageInfo, request, response, end - begin,responceTag, messageHead == null ? null : messageHead.getParentMessageId());
		}
	}

	public Document sendOnlineMessage(Document message, MessageHead messageHead) throws GateLogicException, GateException
	{
		MessagesCacheManager messagesManager = getMessagesCacheManager();
		Document responseDoc = messagesManager == null ? null : messagesManager.get(message);
		if (responseDoc == null)
		{
			MessageInfo messageInfo = null;
			MessageData request = null;
			long begin = 0;
			long end = 0;
			MessageData response = null;
			ResponseHandler responseHandler = null;
			String responseTag = null;
			String requestTag = messageInfoParser.getRequestType(message);
			monitoringHandler.checkRequest(requestTag);
			try
			{
				messageInfo = messageInfoParser.parse(message);
				request = buildMessage(message, messageInfo, messageHead);
				begin = new Date().getTime();
				response = makeRequest(request, messageInfo);
				end = new Date().getTime();

				responseHandler = parseResponse(response, messageInfo);
				monitoringHandler.processRequest(requestTag,end-begin,responseHandler.getErrorCode());

				responseTag = responseHandler.getResponceTag();

				if (!responseHandler.isSuccess())
					processError(messageInfo, response, responseHandler);

				if (responseHandler.isVoid())
					throw new GateException("В ответ на онлайн сообщение получено оффлайн подтверждение");

				responseDoc = (Document) responseHandler.getBody();
				if (messagesManager != null)
					messagesManager.put(message,responseDoc);
			}
			catch (GateTimeOutException e)
			{
				monitoringHandler.processError(requestTag);
				throw e;
			}
			finally
			{   //в любом случае пишем входящее сообщение в лог
				if (end == 0)
					end = new Date().getTime();

				writeToLog(messageInfo, request, response, end - begin, responseTag, null);
				writeToMonitoringLog(messageInfo, request, response, responseHandler);
			}
		}
		return responseDoc;
	}

	private void processError(MessageInfo messageInfo, MessageData response, ResponseHandler responseHandler) throws GateLogicException, GateException
	{
		String system = ApplicationConfig.getIt().getApplicationPrefix();
		ExternalExceptionInfo exceptionInfo = GateExceptionStatisticHelper.getBaseExceptionInfo(system, messageInfo.getName(), responseHandler.getErrorCode(), responseHandler.getErrorText());
		exceptionInfo.setDetail(response.getBodyAsString(getCodePage()));
		exceptionInfo.setGate(System.fromValue(messageInfo.getEndpoint()));
		String customErrorMessage = GateExceptionStatisticHelper.getCustomErrorMessage(exceptionInfo);
		if (StringHelper.isNotEmpty(customErrorMessage))
			throw new GateLogicException(customErrorMessage);

		responseHandler.throwException();
	}

	private String getCodePage() throws GateException
	{
		return GateProperties.getProperties().getProperty("com.rssl.messages.code.page");
	}

	private void writeToMonitoringLog(MessageInfo messageInfo, MessageData request, MessageData response, ResponseHandler responseHandler) throws GateException
	{
		String documentType = logMonitoringTypeMap.get(messageInfo.getName());
		if (documentType == null)
			return;

		if (responseHandler == null)
			return;

		MonitoringOperationConfig config = ConfigFactory.getConfig(MonitoringOperationConfig.class);
		if (!config.isActive())
			return;

		try
		{
			MonitoringLogEntry entry = new MonitoringLogEntry();
			entry.setApplication(LogThreadContext.getApplication().name());
			entry.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
			entry.setCreationDate(Calendar.getInstance());
			entry.setStartDate(Calendar.getInstance());
			String errorCode = responseHandler.getErrorCode();
			entry.setStateCode(errorCode != null ? errorCode : "0");
			entry.setDocumentType(documentType);
			config.writeLog(entry);
		}
		catch (Exception e)
		{
			LogFactory.getLog(LogModule.Gate.name()).error(e);
		}
	}

	private void writeToLog(MessageInfo messageInfo, MessageData request, MessageData response, Long executionTime,String responceTage, String entityId) throws GateException
	{
		if(messageLogWriter == null)
			return;
		String codePage = getCodePage();
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		logEntry.setExecutionTime(executionTime);
		logEntry.setMessageType(messageInfo.getName());
		logEntry.setLoginId(LogThreadContext.getLoginId());
		logEntry.setSurName(LogThreadContext.getSurName());
		logEntry.setPatrName(LogThreadContext.getPatrName());
		logEntry.setFirstName(LogThreadContext.getFirstName());
		logEntry.setPersonSeries(LogThreadContext.getSeries());
		logEntry.setPersonNumbers(LogThreadContext.getNumber());
		if (request != null)
		{
			logEntry.setMessageRequestId(request.getId());
			logEntry.setMessageRequest(request.getBodyAsString(codePage));
		}
		logEntry.setSystem(System.fromValue(messageInfo.getEndpoint()));
		if(response != null)
		{
			logEntry.setMessageResponseId(response.getId());
			logEntry.setMessageResponse(response.getBodyAsString(codePage));
		}
		try
		{
			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			LogFactory.getLog(LogModule.Gate.name()).error(e);
		}
	}

	protected abstract MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException, GateLogicException;

	private MessageData buildMessage(Document message, MessageInfo info, MessageHead messageHead) throws GateException
	{
		return messageGenerator.buildMessage(message, info, messageHead == null ? new MessageHeadImpl() : messageHead);
	}

	protected abstract ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException;

	public GateMessage createRequest(String nameRequest) throws GateException
	{
		try
		{
			return new DOMMessageImpl(nameRequest);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Document sendOfflineMessage(GateMessage request, MessageHead messageHead) throws GateLogicException, GateException
	{
		return sendOfflineMessage(request.getDocument(), messageHead);
	}

	public Document sendOnlineMessage(GateMessage request, MessageHead messageHead) throws GateLogicException, GateException
	{
		return sendOnlineMessage(request.getDocument(), messageHead);
	}
}

