package com.rssl.phizic.logging;

import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.AllNamesFoundException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.message.RPCElement;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.SAXParser;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;

/**
 * @author Erkin
 * @ created 19.07.2010
 * @ $Author$
 * @ $Revision$
 * ’елпер дл€ логировани€ axis запросов к шине.
 */
public final class AxisLoggerHelper
{
	public static final String UNKNOWN_TYPE = "unknown";
	public static final String UNKNOWN_ID = "0";

	private static final String UNKNOWN_ENVELOPE = "unknown SOAP-envelope of message (or message is not from Axis)";
	/*константы дл€ пула парсеров*/
	private static final int MAX_IDLE_PARSERS = 50; //максимальное количество свободных парсеров
	private static final int MIN_IDLE_PARSERS = 10; //минимальное количество свободных парсеров
	private static final int MAX_ACTIVE_PARSERS = 50; //максимальное количество активных парсеров
	private static final int MAX_TOTAL_PARSERS = 50; //максимальное количество парсеров


	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final KeyedObjectPool SAX_PARSERS_POOL = createSAXParsersPool();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ѕреобразует сообщение аксиса в строковый вид, пригодный дл€ записи в журнал
	 * @param message - сообщение аксиса
	 * @return сообщение в виде строки (never null)
	 * @throws LoggingException
	 */
	public static String serializeMessage(Message message) throws LoggingException
	{
		if (message == null)
			return "null message";
		MessageContext msgContext = message.getMessageContext();

		SOAPEnvelope envelope;
		try	{
			envelope = message.getSOAPEnvelope();
		} catch (AxisFault ex) {
			throw new LoggingException(ex);
		}

		if (envelope == null)
			return UNKNOWN_ENVELOPE;

		StringWriter writer = new StringWriter();
		try
		{
			SerializationContext ctx = new SerializationContext(writer, msgContext);
			envelope.output(ctx);
			writer.close();
			return writer.getBuffer().toString();
		}
		catch (Exception ex)
		{
			throw new LoggingException(ex);
		}
		finally
		{
			try { writer.close(); } catch (IOException ignored) {}
		}
	}

	/**
	 * ѕарсит xml-контент с помощью SAX-парсера, вз€того из пула парсеров
	 * @param content xml-контент
	 * @param handler наследник DefaultHandler-a
	 * @throws LoggingException
	 */
	public static void parse(String content, DefaultHandler handler) throws LoggingException
	{
		SAXParser parser = borrowParser();
		try
		{
			parser.parse(new InputSource(new StringReader(content)), handler);
		}
		catch (AllNamesFoundException ignored)
		{
			//все хорошо, это исключение брошено, чтобы прервать парсинг
		}
		catch(Exception e)
		{
			returnParser(parser);
			throw new LoggingException(e);
		}
		returnParser(parser);
	}

	/**
	 * получить им€ запроса или ответа: следующий за DoIFXRq(s) тег.
	 * @param body сообщение(запрос или ответ)
	 * @return им€ запроса.
	 * @throws LoggingException
	 */
	public static String resolveMessageType(SOAPBody body) throws LoggingException
	{
		if (body == null)
			return UNKNOWN_TYPE;

		try
		{
			// DoIFXRq or DoIFXRs
			Node node = body.getFirstChild();
			if (node != null)
				// first child of DoIFXRq/DoIFXRs
				node = node.getFirstChild();
			if (node != null)
				return node.getNodeName();

			return UNKNOWN_TYPE;
		}
		catch (Exception ex)
		{
			throw new LoggingException(ex);
		}
	}

	/**
	 * ѕолучить идентификатор запроса
	 * @param element - сообщение
	 * @return »дентификатор либо "0"
	 * @throws LoggingException
	 */
	public static String resolveMessageId(SOAPBody element) throws LoggingException
	{
		if (element == null)
			return UNKNOWN_ID;

		try
		{
			if (element != null)
			{
				String rqUid = XmlHelper.getSimpleElementValue(element, "RqUID");

				if (!StringHelper.isEmpty(rqUid))
					return rqUid;
			}

			return UNKNOWN_ID;
		}
		catch (Exception ex)
		{
			throw new LoggingException(ex);
		}
	}

	/**
	 * получить им€ запроса или ответа дл€ xml сообщени€ дл€ ќ«ќЌ и ѕ‘–
	 * @param body - запрос или ответ
	 * @return им€ запроса.
	 * @throws LoggingException
	 */
	public static String resolveMessageShopType(SOAPBody body) throws LoggingException
	{
		if (body == null)
			return UNKNOWN_TYPE;
		try
		{
			Node node = body.getFirstChild();
			if (node != null)
				return node.getNodeName();
			else
				return UNKNOWN_TYPE;
		}
		catch (Exception e)
		{
			throw new LoggingException(e);
		}
	}

	/**
	 * получить им€ запроса или ответа дл€ xml сообщени€ дл€ ¬идов и размеров пенсий
	 * @param body - запрос или ответ
	 * @return им€ запроса.
	 * @throws LoggingException
	 */
	public static String resolveMessageDepoCODType(SOAPBody body) throws LoggingException
	{
		if (body == null)
			return UNKNOWN_TYPE;
		try
		{
			Node node = body.getFirstChild();
			if (node != null)
				return ((RPCElement) node).getName();
			else
				return UNKNOWN_TYPE;
		}
		catch (Exception e)
		{
			throw new LoggingException(e);
		}
	}

	/**
	 * ѕолучить body из сообщени€
	 * @param message сообщенеи
	 * @return body
	 * @throws LoggingException
	 */
	public static SOAPBody getBody(Message message) throws LoggingException
	{
		if (message == null)
			return null;

		try
		{
			return message.getSOAPBody();
		}
		catch (SOAPException e)
		{
			throw new LoggingException(e);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	private static KeyedObjectPool createSAXParsersPool()
	{
		KeyedObjectPoolFactory factory = new GenericKeyedObjectPoolFactory(new SAXParserObjectFactory());
		GenericKeyedObjectPool pool = (GenericKeyedObjectPool)factory.createPool();
		pool.setMaxIdle(MAX_IDLE_PARSERS);
		pool.setMinIdle(MIN_IDLE_PARSERS);
		pool.setMaxActive(MAX_ACTIVE_PARSERS);
		pool.setMaxTotal(MAX_TOTAL_PARSERS);
		return pool;
	}

	private static SAXParser borrowParser() throws LoggingException
	{
		try
		{
			return (SAXParser) SAX_PARSERS_POOL.borrowObject("");
		}
		catch(Exception e)
		{
			throw new LoggingException("»сключение при получении парсера из пула.",e);
		}
	}

	private static void returnParser(SAXParser parser) throws LoggingException
	{
		try
		{
			SAX_PARSERS_POOL.returnObject("", parser);
		}
		catch (Exception e)
		{
			/** это ислючение никогда не будет брошено
			 * @see org.apache.commons.pool.impl.GenericKeyedObjectPool#returnObject(Object, Object)
			 * но на вс€кий случай:
 			 */
			throw new LoggingException("»сключение при возвращении парсера в пул.", e);
		}
	}

	/**
	 * «апись в лог
	 * @param systemId - идентификатор внешней системы
	 * @param messageContext - контекст SOAP-сообщени€
	 * @param duration - врем€ исполнени€
	 * @throws Exception
	 */
	public static void writeToLog(System systemId, MessageContext messageContext, Long duration) throws Exception
	{
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		Message request = messageContext.getRequestMessage();
		Message response = messageContext.getResponseMessage();
		SOAPBody requestBody = AxisLoggerHelper.getBody(request);

		logEntry.setSystem(systemId);

		logEntry.setMessageRequestId(AxisLoggerHelper.resolveMessageId(requestBody));
		logEntry.setMessageType(AxisLoggerHelper.resolveMessageType(requestBody));
		logEntry.setMessageRequest(AxisLoggerHelper.serializeMessage(request));

		try
		{
			logEntry.setMessageResponse(AxisLoggerHelper.serializeMessage(response));
			SOAPBody responseBody = AxisLoggerHelper.getBody(response);
			logEntry.setMessageResponseId(AxisLoggerHelper.resolveMessageId(responseBody));
		}
		catch (Exception ex)
		{
			log.error("ѕроблемы с записью ответа в журнал сообщений", ex);
		}

		logEntry.setExecutionTime(duration);

		MessageLogWriter writer = MessageLogService.getMessageLogWriter();
		writer.write(logEntry);
	}
}
