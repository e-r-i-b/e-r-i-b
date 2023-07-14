package com.rssl.auth.csa.back.integration.dasreda;

import com.rssl.auth.csa.back.Config;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.net.URL;
import java.util.Calendar;
import javax.xml.soap.*;

/**
 * Сервис взаимодействия с ВС "Деловая среда"
 *
 * @author akrenev
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class BusinessEnvironmentService
{
	private static final String SOAP_ACTION_HEADER_NAME = "SOAPAction";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final System SYSTEM = System.dasreda;
	private static final Object CONNECTION_FACTORY_LOCKER = new Object();
	private static SOAPConnectionFactory connectionFactory;
	private static final Object MESSAGE_FACTORY_LOCKER = new Object();
	private static MessageFactory messageFactory;
	private static final String VERIFY_COMPLETE_STATUS = "0";

	private SOAPConnectionFactory getConnectionFactory() throws SOAPException
	{
		if (connectionFactory == null)
		{
			synchronized (CONNECTION_FACTORY_LOCKER)
			{
				if (connectionFactory == null)
				{
					connectionFactory = SOAPConnectionFactory.newInstance();
				}
			}
		}
		return connectionFactory;
	}

	private MessageFactory getMessageFactory() throws SOAPException
	{
		if (messageFactory == null)
		{
			synchronized (MESSAGE_FACTORY_LOCKER)
			{
				if (messageFactory == null)
				{
					messageFactory = MessageFactory.newInstance();
				}
			}
		}
		return messageFactory;
	}

	/**
	 * Отправить данные в деловую среду
	 * @param source источник данных для отправления
	 * @throws BusinessEnvironmentException
	 */
	public void sendData(BusinessEnvironmentDataSource source) throws BusinessEnvironmentException
	{
		SOAPMessage request = null;
		SOAPConnection connection = null;
		try
		{
			request = getMessageFactory().createMessage();
			source.buildExternalSystemRequest(request);
			SOAPConnectionFactory soapConnectionFactory = getConnectionFactory();
		    connection = soapConnectionFactory.createConnection();
		}
		catch (SOAPException e)
		{
			throw new BusinessEnvironmentException(e);
		}

		SOAPMessage response = doRequest(request, connection);

		checkResponse(response);
	}

	private SOAPMessage doRequest(SOAPMessage request, SOAPConnection connection) throws BusinessEnvironmentException
	{
		SOAPMessage response = null;
		Calendar start = Calendar.getInstance();
		try
		{
			Config config = ConfigFactory.getConfig(Config.class);
			request.getMimeHeaders().addHeader(SOAP_ACTION_HEADER_NAME, config.getBusinessEnvironmentSOAPAction());
		    response = connection.call(request, new URL(config.getBusinessEnvironmentURL()));
		}
		catch (Exception e)
		{
			if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				log.error("получена ошибка таймаута при отправке сообщения в КСШ.");
			throw new BusinessEnvironmentException(e);
		}
		finally
		{
			try
			{
				if (connection != null)
					connection.close();
				writeToLog(request, response, DateHelper.diff(Calendar.getInstance(), start));
			}
			catch (Exception e)
			{
				log.error("Ошибка закрытия коннекшена", e);
			}
		}
		return response;
	}

	private void checkResponse(SOAPMessage response) throws BusinessEnvironmentException
	{
		try
		{
			Element responseElement = asDocument(response).getDocumentElement();
			if (!VERIFY_COMPLETE_STATUS.equals(XmlHelper.getSimpleElementValue(responseElement, "StatusCode")))
				throw new FailVerifyBusinessEnvironmentException(XmlHelper.getSimpleElementValue(responseElement, "StatusDesc"));
		}
		catch (SOAPException e)
		{
			throw new BusinessEnvironmentException(e);
		}
	}

	private static Document asDocument(SOAPMessage message) throws SOAPException
	{
		return message.getSOAPPart().getEnvelope().getOwnerDocument();
	}

	private static void writeToLog(SOAPMessage request, SOAPMessage response, Long duration)
	{
		try
		{
			MessagingLogEntry logEntry = LogEntryBuilder.build(request, response);
			logEntry.setSystem(SYSTEM);
			logEntry.setExecutionTime(duration);
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(logEntry);
		}
		catch (Exception ex)
		{
			log.error("Проблемы с записью в журнал сообщений", ex);
		}
	}

	/**
	 * билдер сущности лога по контексту сообщения
	 */
	private static class LogEntryBuilder
	{
		public static final String UNKNOWN_TYPE = "unknown";
		private static final String UNKNOWN_ID = "0";

		private static SOAPBody getSoapBody(SOAPMessage message) throws BusinessEnvironmentException
		{
			if (message == null)
				return null;

			try
			{
				return message.getSOAPBody();
			}
			catch (Exception e)
			{
				throw new BusinessEnvironmentException(e);
			}
		}

		private static String getMessageId(SOAPBody element)
		{
			if (element == null)
				return UNKNOWN_ID;

			String rqUid = XmlHelper.getSimpleElementValue(element, "RqUID");
			if (!StringHelper.isEmpty(rqUid))
				return rqUid;

			return UNKNOWN_ID;
		}

		private static String getMessageType(SOAPBody element)
		{
			if (element == null)
				return UNKNOWN_TYPE;

			// RequestParameter
			Node node = element.getFirstChild();
			if (node != null)
				// первый дочерний узел RequestParameter
				node = node.getFirstChild();
			if (node != null)
				return node.getNodeName();

			return UNKNOWN_TYPE;
		}

		private static String serializeMessage(SOAPMessage message) throws BusinessEnvironmentException
		{
			if (message == null)
				return null;

			try
			{
				return XmlHelper.convertDomToText(asDocument(message).getFirstChild());
			}
			catch (Exception e)
			{
				throw new BusinessEnvironmentException(e);
			}
		}

		private static MessagingLogEntry build(SOAPMessage request, SOAPMessage response) throws BusinessEnvironmentException
		{
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			SOAPBody requestBody = getSoapBody(request);

			logEntry.setMessageRequestId(getMessageId(requestBody));
			logEntry.setMessageType(getMessageType(requestBody));
			logEntry.setMessageRequest(serializeMessage(request));

			try
			{
				logEntry.setMessageResponse(serializeMessage(response));
				SOAPBody responseBody = getSoapBody(response);
				logEntry.setMessageResponseId(getMessageId(responseBody));
			}
			catch (Exception ex)
			{
				log.error("Проблемы с записью ответа в журнал сообщений", ex);
			}
			return logEntry;
		}
	}
}
