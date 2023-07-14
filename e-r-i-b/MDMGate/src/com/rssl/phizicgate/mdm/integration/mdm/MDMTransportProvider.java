package com.rssl.phizicgate.mdm.integration.mdm;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizicgate.mdm.integration.mdm.message.*;

import java.util.Calendar;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Провайдер мдм
 */

public class MDMTransportProvider
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final JmsService jmsService = new JmsService();

	private static volatile MDMTransportProvider instance = null;

	private final QueueConnectionFactory factory;
	private final Queue inQueue;
	private final Queue ticketQueue;
	private final Queue outOnlineQueue;

	private MDMTransportProvider()
	{
		InitialContext ctx = null;
		try
		{
			ctx             = new InitialContext();
			factory         = (QueueConnectionFactory) ctx.lookup(MDMSegmentInfo.FACTORY_NAME);
			inQueue         = (Queue) ctx.lookup(MDMSegmentInfo.IN_QUEUE_NAME);
			ticketQueue     = (Queue) ctx.lookup(MDMSegmentInfo.TICKET_QUEUE_NAME);
			outOnlineQueue  = (Queue) ctx.lookup(MDMSegmentInfo.OUT_ONLINE_QUEUE_NAME);
		}
		catch (NamingException e)
		{
			throw new InternalErrorException("[MDM] Сбой при инициализации MDMTransportProvider.", e);
		}
		finally
		{
			if (ctx != null)
				try { ctx.close(); } catch (NamingException ignored) {}
		}
	}

	/**
	 * @return инстанс сервиса
	 */
	public static MDMTransportProvider getInstance()
	{
		if (instance == null)
		{
			synchronized (MDMTransportProvider.class)
			{
				if (instance == null)
				{
					instance = new MDMTransportProvider();
				}
			}
		}
		return instance;
	}

	/**
	 * Отправляет документ не дожидаясь ответа
	 * @param request запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public final void sendTicket(Request<TicketMessageProcessor> request) throws GateException
	{
		try
		{
			jmsService.sendToQueue(request.getMessage(), ticketQueue, factory, request.getMessageProcessor());
		}
		catch (JMSException e)
		{
			throw new GateException("[MDM] Сбой на отправке запроса в очередь.", e);
		}
		catch (RuntimeException e)
		{
			throw new GateException("[MDM] Сбой на отправке запроса.", e);
		}
		finally
		{
			// Логирование заявки в Журнал сообщений
			writeMessageLog(request);
		}
	}

	/**
	 * Отправляет запрос не дожидаясь ответа
	 * @param request запрос
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public final void processOffline(Request<OfflineMessageProcessor> request) throws GateException
	{
		try
		{
			jmsService.sendToQueue(request.getMessage(), inQueue, factory, request.getMessageProcessor());
		}
		catch (JMSException e)
		{
			throw new GateException("[MDM] Сбой на отправке запроса в очередь.", e);
		}
		catch (RuntimeException e)
		{
			throw new GateException("[MDM] Сбой на отправке запроса.", e);
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
	 */
	public final <Rs> Response<Rs> processOnline(Request<OnlineMessageProcessor<Rs>> request, long timeout) throws GateException
	{

		String responseBody = null;
		String responseId = null;

		Calendar start = Calendar.getInstance();
		try
		{
			OnlineMessageProcessor<Rs> messageProcessor = request.getMessageProcessor();

			Message message = jmsService.sendToQueue(request.getMessage(), inQueue, factory, messageProcessor);
			String responseMessageSelector = messageProcessor.getResponseMessageSelector(message);
			Message response = jmsService.receiveMessageFormQueue(outOnlineQueue, factory, responseMessageSelector, timeout);

			Response<Rs> wrappedResponse = messageProcessor.buildResponse(response);
			responseBody = wrappedResponse.getResponseBody();
			responseId = wrappedResponse.getMessageId();
			return wrappedResponse;
		}
		catch (JMSException e)
		{
			throw new GateException("[MDM] Сбой на отправке запроса в очередь.", e);
		}
		catch (RuntimeException e)
		{
			throw new GateException("[MDM] Сбой на отправке запроса.", e);
		}
		finally
		{
			writeMessageLog(request, responseBody, responseId, DateHelper.diff(Calendar.getInstance(), start));
		}
	}

	private void writeMessageLog(Request request)
	{
		writeMessageLog(request, null, null, 0);
	}

	private void writeMessageLog(Request request, String responseBody, String responseId, long duration)
	{
		if (request.getMessage() == null)
			return;

		MessageLogWriter writer = MessageLogService.getMessageLogWriter();
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		logEntry.setSystem(com.rssl.phizic.logging.messaging.System.mdm);
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
			log.error("[MDM] Проблемы с записью в журнал сообщений.", e);
		}
	}
}
