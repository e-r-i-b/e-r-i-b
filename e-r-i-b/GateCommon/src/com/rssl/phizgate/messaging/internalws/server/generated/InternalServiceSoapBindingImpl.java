/**
 * AuthServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizgate.messaging.internalws.server.generated;

import com.rssl.phizgate.messaging.internalws.InternalServiceConfig;
import com.rssl.phizgate.messaging.internalws.server.Logger;
import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.RequestMessageHandler;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.RequestProcessor;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.UnsupportedMessageTypeProcessor;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import org.apache.commons.logging.Log;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.Calendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class InternalServiceSoapBindingImpl implements InternalServicePortType
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	public String process(String request) throws RemoteException
	{
		Calendar start = Calendar.getInstance();
		String responce = null;
		try
		{
			RequestInfo requestInfo = parseRequest(request);
			fillContext(requestInfo);

			RequestProcessor requestHandler = InternalServiceConfig.getInstance().getRequestProcessor(requestInfo.getType());
			if (requestHandler == null)
				requestHandler = UnsupportedMessageTypeProcessor.INSTANCE;

			responce = requestHandler.process(requestInfo).asString();
			return responce;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RemoteException("Internal error", e);
		}
		finally
		{

			try
			{
				new Logger().writeToLog(request, responce, DateHelper.diff(Calendar.getInstance(), start));
			}
			catch (Exception ex)
			{
				log.error("Проблемы с записью в журнал сообщений", ex);
			}

			resetContext();
		}
	}

	/**
	 * Заполнить все возможные контексты
	 * @param requestInfo информация о запросе.
	 */
	private void fillContext(RequestInfo requestInfo)
	{
		LogThreadContext.setIPAddress(requestInfo.getIP());
		LogThreadContext.setLogUID(new RandomGUID().getStringValue());
	}

	/**
	 * Очистить всевозможные контексты.
	 */
	private void resetContext()
	{
		LogThreadContext.clear();
	}

	private RequestInfo parseRequest(String request) throws ParserConfigurationException, SAXException, IOException
	{
		RequestMessageHandler handler = new RequestMessageHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(new InputSource(new StringReader(request)), handler);
		return handler.getRequestInfo();
	}
}
