package com.rssl.phizicgate.way4u.messaging;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizicgate.way4u.Config;

import java.util.Calendar;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author krenev
 * @ created 10.10.2013
 * @ $Author$
 * @ $Revision$
 * Транспортный сервис way4u.
 * Взаимодействие происходит через 2 очереди. В исходящую очередь помещается запрос.
 * Ответ получается из входящей очереди. Квитовка происходит по заголовку CorrelId.
 */

public class Way4uTransport
{
	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);
	private static final JmsService jmsService = new JmsService();
	private static final String RESPONSE_SELECTOR_PATTERN = "JMSCorrelationID = '%s'";

	protected Way4uResponse doRequest(Way4uRequest request) throws SystemException, GateTimeOutException
	{
		Way4uResponse response = null;
		Calendar start = Calendar.getInstance();
		try
		{
			TextMessage requestMessage = jmsService.sendMessageToQueue(request.asString(), Constants.INPUT_QUEUE_NAME, Constants.INPUT_QUEUE_CONNECTION_FACTORY_NAME, null, null);
			long responseTimeout = ConfigFactory.getConfig(Config.class).getResponseTimeout();
			TextMessage responseMessage = jmsService.receiveMessageFormQueue(
					Constants.OUTPUT_QUEUE_NAME,
					Constants.OUTPUT_QUEUE_CONNECTION_FACTORY_NAME,
					String.format(RESPONSE_SELECTOR_PATTERN, requestMessage.getJMSMessageID()),  //Квитовку производим по заголовку CorrelId, в котором лежит идентифкатор запроса
					responseTimeout);

			if (responseMessage == null)
			{
				throw new GateTimeOutException("Не получен ответ от way4u. timeout=" + responseTimeout + "мс");
			}
			response = new Way4uResponse(responseMessage.getText());

			if (!request.getId().equals(response.getId()))
			{
				throw new SystemException("Ошибка 'квитовка' запросов-ответов. Идентификатор запроса: " + request.getId() + ". Идентифкатор ответа: " + response.getId());
			}
			return response;
		}
		catch (JMSException e)
		{
			log.error("Ошибка взаимодействия с way4u", e);
			throw new SystemException(e);
		}
		finally
		{
			logMessages(request, response, DateHelper.diff(Calendar.getInstance(), start));
		}
	}

	private void logMessages(Way4uRequest request, Way4uResponse response, Long executionTime)
	{
		try
		{
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setExecutionTime(executionTime);

			logEntry.setMessageType(request.getType());
			logEntry.setMessageRequestId(request.getId());
			logEntry.setMessageRequest(request.asString());
			logEntry.setSystem(com.rssl.phizic.logging.messaging.System.way4);
			if (response != null)
			{
				logEntry.setMessageResponseId(response.getId());
				logEntry.setMessageResponse(response.asString());
				logEntry.setErrorCode(response.getRespCode());
			}
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(logEntry);
		}
		catch (Exception ex)
		{
			log.error("Ошибка записи в журнал сообщений", ex);
		}
	}
}
