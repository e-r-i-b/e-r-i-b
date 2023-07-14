/**
 * AuthServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.auth.csa.back.ws;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.log.CSALoggerHelper;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.RequestMessageHandler;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestRouter;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
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

public class AuthServiceSoapBindingImpl implements com.rssl.auth.csa.back.ws.AuthServicePortType
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	public java.lang.String process(java.lang.String request) throws java.rmi.RemoteException
	{
		String responce = null;
		Calendar start = Calendar.getInstance();
		try
		{
			RequestInfo requestInfo = parseRequest(request);
			fillContext(requestInfo);
			ResponseInfo result = RequestRouter.getInstance().process(requestInfo);
			responce = result.asString();
			return responce;
		}
		catch (Throwable e)
		{
			log.error(e.getMessage(), e);
			throw new RemoteException("Internal error", e);
		}
		finally
		{
			try
			{
				//журнал сообщений фронта пишем в беке
				new CSALoggerHelper().writeToLog(request, responce, DateHelper.diff(Calendar.getInstance(), start));
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
		LogThreadContext.setLogUID(Utils.generateGUID());
		LoggingHelper.setAppServerInfoToLogThreadContext(null);
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
