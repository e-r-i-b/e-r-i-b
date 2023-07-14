package com.rssl.phizicgate.esberibgate.ws.jms.common;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.monitoring.MonitoringLogHelper;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.utils.jms.MessageCreator;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OfflineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author komarov
 * @ created 07.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Провайдер сообщений в шину
 */
public final class JMSTransportProvider
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final Map<ESBSegment, JMSTransportProvider> INSTANCES = new HashMap<ESBSegment, JMSTransportProvider>();
	private static final JmsService jmsService = new JmsService();

	private final ESBSegment segment;
	private final QueueConnectionFactory factory;
	private final Queue inQueue;
	private final Queue outOnlineQueue;

	private JMSTransportProvider(ESBSegment segment)
	{
		InitialContext ctx = null;
		try
		{
			this.segment = segment;
			ctx          = new InitialContext();
			factory      = (QueueConnectionFactory) ctx.lookup(segment.getQueueFactoryName());
			inQueue      = (Queue) ctx.lookup(segment.getInQueueName());
			outOnlineQueue = StringHelper.isEmpty(segment.getOnlineOutQueueName())? null: (Queue) ctx.lookup(segment.getOnlineOutQueueName());
		}
		catch (NamingException e)
		{
			throw new InternalErrorException("[ESB] Сбой при инициализации JMSTransportProvider.", e);
		}
		finally
		{
			if (ctx != null)
				try { ctx.close(); } catch (NamingException ignored) {}
		}
	}

	/**
	 * @param segment сегмент шины
	 * @return инстанс
	 */
	public static JMSTransportProvider getInstance(ESBSegment segment) throws GateException
	{
		ExternalSystemHelper.check(ExternalSystemHelper.getESBSystemCode());

		JMSTransportProvider instance = INSTANCES.get(segment);
		if(instance != null)
			return instance;

		synchronized (INSTANCES)
		{
			instance = INSTANCES.get(segment);
			if (instance == null)
			{
				instance = new JMSTransportProvider(segment);
				INSTANCES.put(segment, instance);
			}
		}

		return instance;
	}

	/**
	 * поместить сообщение в очередь
	 * @param xml сообщение
	 * @param messageCreator конструктор jms сообщений
	 * @return сообщение
	 * @throws JMSException
	 */
	@Deprecated
	public Message doRequest(String xml, MessageCreator messageCreator) throws JMSException
	{
		return jmsService.sendToQueue(xml, inQueue, factory, messageCreator);
	}

	/**
	 * Отправляет документ не дожидаясь ответа
	 * @param request запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public final void processOffline(Request<OfflineMessageProcessor> request) throws GateException, GateLogicException
	{
		try
		{
			Message message = jmsService.sendToQueue(request.getMessage(), inQueue, factory, request.getMessageProcessor());
			request.setJmsMessageId(message.getJMSMessageID());
		}
		catch (JMSException e)
		{
			throw new GateException("[ESB] Сбой на отправке документа в очередь.", e);
		}
		catch (RuntimeException e)
		{
			throw new GateException("[ESB] Сбой на отправке документа.", e);
		}
		finally
		{
			// Логирование заявки в Журнал сообщений
			writeMessageLog(request);
		}
	}

	/**
	 * Отправляет документ с ожиданием документа
	 * @param request запрос
	 * @param timeout таймаут ожидания
	 * @return ответ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public final <Rs> Response<Rs> processOnline(Request<OnlineMessageProcessor<Rs>> request, long timeout) throws GateException, GateLogicException
	{
		if (outOnlineQueue == null)
			throw new GateException("[ESB] Не установлеа очередь онлайн ответов для сегмента " + segment.name());

		String responseBody = null;
		String responseId = null;
		String responseErrorCode = null;

		Calendar start = Calendar.getInstance();
		try
		{
			OnlineMessageProcessor<Rs> messageProcessor = request.getMessageProcessor();

			Message message = jmsService.sendToQueue(request.getMessage(), inQueue, factory, messageProcessor);
			request.setJmsMessageId(message.getJMSMessageID());
			String responseMessageSelector = messageProcessor.getResponseMessageSelector(message);
			Message response = jmsService.receiveMessageFormQueue(outOnlineQueue, factory, responseMessageSelector, timeout);

			Response<Rs> wrappedResponse = messageProcessor.buildResponse(response);
			responseBody = wrappedResponse.getResponseBody();
			responseId = wrappedResponse.getMessageId();
			responseErrorCode = wrappedResponse.getErrorCode();
			return wrappedResponse;
		}
		catch (JMSException e)
		{
			throw new GateException("[ESB] Сбой на отправке документа в очередь.", e);
		}
		catch (RuntimeException e)
		{
			throw new GateException("[ESB] Сбой на отправке документа.", e);
		}
		finally
		{
			writeMessageLog(request, responseBody, responseId, responseErrorCode, DateHelper.diff(Calendar.getInstance(), start));
		}
	}

	private void writeMessageLog(Request request)
	{
		writeMessageLog(request, null, null, null, 0);
	}

	private void writeMessageLog(Request request, String responseBody, String responseId, String responseErrorCode, long duration)
	{
		if (request.getMessage() == null)
			return;

		MessageLogWriter writer = MessageLogService.getMessageLogWriter();
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		logEntry.setSystem(com.rssl.phizic.logging.messaging.System.esberib);
		logEntry.setExecutionTime(duration);
		logEntry.setMessageRequestId(request.getMessageId());
		logEntry.setMessageType(request.getMessageType());
		logEntry.setMessageRequest(request.getMessage());
		logEntry.setMessageResponse(responseBody);
		logEntry.setMessageResponseId(responseId);

		try
		{
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("[ESB] Проблемы с записью в журнал сообщений.", e);
		}

		writeMonitoringLog(logEntry, request, responseErrorCode);
	}

	private static void writeMonitoringLog(MessagingLogEntry logEntry, Request request, String responseErrorCode)
	{
		String documentType = request.getMonitoringDocumentType();
		if (Request.SKIP_MONITORING.equals(documentType))
			return;

		MonitoringLogHelper.writeLog(logEntry, StringHelper.isNotEmpty(responseErrorCode) ? responseErrorCode : "0", documentType);
	}
}
