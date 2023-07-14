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
 * ������ ��� ����������� axis �������� � ����.
 */
public final class AxisLoggerHelper
{
	public static final String UNKNOWN_TYPE = "unknown";
	public static final String UNKNOWN_ID = "0";

	private static final String UNKNOWN_ENVELOPE = "unknown SOAP-envelope of message (or message is not from Axis)";
	/*��������� ��� ���� ��������*/
	private static final int MAX_IDLE_PARSERS = 50; //������������ ���������� ��������� ��������
	private static final int MIN_IDLE_PARSERS = 10; //����������� ���������� ��������� ��������
	private static final int MAX_ACTIVE_PARSERS = 50; //������������ ���������� �������� ��������
	private static final int MAX_TOTAL_PARSERS = 50; //������������ ���������� ��������


	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final KeyedObjectPool SAX_PARSERS_POOL = createSAXParsersPool();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ����������� ��������� ������ � ��������� ���, ��������� ��� ������ � ������
	 * @param message - ��������� ������
	 * @return ��������� � ���� ������ (never null)
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
	 * ������ xml-������� � ������� SAX-�������, ������� �� ���� ��������
	 * @param content xml-�������
	 * @param handler ��������� DefaultHandler-a
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
			//��� ������, ��� ���������� �������, ����� �������� �������
		}
		catch(Exception e)
		{
			returnParser(parser);
			throw new LoggingException(e);
		}
		returnParser(parser);
	}

	/**
	 * �������� ��� ������� ��� ������: ��������� �� DoIFXRq(s) ���.
	 * @param body ���������(������ ��� �����)
	 * @return ��� �������.
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
	 * �������� ������������� �������
	 * @param element - ���������
	 * @return ������������� ���� "0"
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
	 * �������� ��� ������� ��� ������ ��� xml ��������� ��� ���� � ���
	 * @param body - ������ ��� �����
	 * @return ��� �������.
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
	 * �������� ��� ������� ��� ������ ��� xml ��������� ��� ����� � �������� ������
	 * @param body - ������ ��� �����
	 * @return ��� �������.
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
	 * �������� body �� ���������
	 * @param message ���������
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
			throw new LoggingException("���������� ��� ��������� ������� �� ����.",e);
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
			/** ��� ��������� ������� �� ����� �������
			 * @see org.apache.commons.pool.impl.GenericKeyedObjectPool#returnObject(Object, Object)
			 * �� �� ������ ������:
 			 */
			throw new LoggingException("���������� ��� ����������� ������� � ���.", e);
		}
	}

	/**
	 * ������ � ���
	 * @param systemId - ������������� ������� �������
	 * @param messageContext - �������� SOAP-���������
	 * @param duration - ����� ����������
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
			log.error("�������� � ������� ������ � ������ ���������", ex);
		}

		logEntry.setExecutionTime(duration);

		MessageLogWriter writer = MessageLogService.getMessageLogWriter();
		writer.write(logEntry);
	}
}
