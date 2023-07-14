package com.rssl.phizicgate.iqwave.listener;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.listener.ListenerMessageReceiver;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.GateMessagingConfigurationException;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.logging.messaging.*;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.messaging.IQWaveMessageHandler;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.Date;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Базовый класс обработчика сообщений от IQWave
 *
 * @author gladishev
 * @ created 15.10.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class IQWaveListenerMessageReceiverBase extends AbstractService implements ListenerMessageReceiver
{
	protected static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);
	protected MessagingConfig messagingConfig;

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public IQWaveListenerMessageReceiverBase(GateFactory factory)
	{
		super(factory);

		try
		{
			messagingConfig = ConfigurationLoader.load(Constants.INCOMING_MESSAGES_CONFIG);
		}
		catch (GateMessagingConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}

	public String handleMessage(String request) throws GateException
	{
		IQWaveMessageHandler messageHandler = null;
		String responce = null;
		String requestType = null;
		Long executionTime = 0L;
		String messageId = null;
		try
		{
			messageHandler = parseMessage(request);
			requestType = messageHandler.getMessageTag();
			MesssageProcessor processor = getMessageProcessor(requestType, request);
			if (processor == null)
			{
				throw new GateException("Неверный запрос " + requestType);
			}
			messageId = messageHandler.getMessageId();
			long begin = new Date().getTime();
			responce = processor.process(messageHandler);
			executionTime = new Date().getTime() - begin;
			return responce;
		}
		catch (Exception e)
		{
			log.error("Ошибка обработки запроса",e);
			responce = MessageHelper.createErrorMessage(e.getMessage(), messageHandler);
			return responce;
		}
		finally
		{
			writeToLog(messageId, request, responce, requestType, executionTime);
		}
	}
	protected void writeToLog(String messageId, String request, String responce, String messageType, Long executionTime)
	{
		MessageLogWriter logWriter = MessageLogService.getMessageLogWriter();

		MessagingLogEntry logEntry = MessageLogService.createLogEntry();

		logEntry.setExecutionTime(executionTime);
		logEntry.setMessageType(messageType);
		logEntry.setMessageRequestId(messageId);
		logEntry.setMessageRequest(request);
		logEntry.setSystem(com.rssl.phizic.logging.messaging.System.iqwave);

		logEntry.setMessageResponseId(messageId);
		logEntry.setMessageResponse(responce);
		try
		{
			logWriter.write(logEntry);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	protected IQWaveMessageHandler parseMessage(String request) throws GateException
	{
		IQWaveMessageHandler messageHandler = new IQWaveMessageHandler(messagingConfig.avaibleRequests());
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(new StringReader(request)), messageHandler);
			return messageHandler;
		}
		catch (Exception e)
		{
			throw new GateException("Ошибка парсинга запроса: " + e.getMessage(), e);
		}
	}

	protected abstract MesssageProcessor getMessageProcessor(String requestType, String request);
}
