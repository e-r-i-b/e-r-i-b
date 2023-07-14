/**
 * ERIBClientServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.webAPI.generated;

import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.SimpleRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.Response;
import com.rssl.phizic.web.webApi.protocol.jaxb.router.JAXBRequestRouter;
import com.rssl.phizic.web.webApi.protocol.jaxb.router.JAXBRequestRouterImpl;
import org.apache.commons.logging.Log;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.Calendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

public class ERIBClientServiceSoapBindingImpl implements com.rssl.phizic.ws.webAPI.generated.ERIBClientServicePortType
{

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Web);
	private static final com.rssl.phizic.logging.messaging.System SYSTEM_ID = System.WebAPI;
	private final static String JAXB_CONTEXT_PATH = Request.class.getPackage().getName() + ":" +
			Response.class.getPackage().getName();

	public java.lang.String process(java.lang.String source) throws java.rmi.RemoteException
	{

		Calendar start = Calendar.getInstance();
		String response = null;
		JAXBRequestRouter router = JAXBRequestRouterImpl.INSTANCE;
		Request request = null;
		try
		{
			JAXBContext context = JAXBContext.newInstance(JAXB_CONTEXT_PATH);
			request = context.createUnmarshaller().unmarshal(new StreamSource(new StringReader(source)), SimpleRequest.class).getValue();
 			response = router.process(request, source);
			return response;
		}
		catch (JAXBException e)
		{
			log.error(e.getMessage(), e);
			throw new RemoteException("Internal error", e);
		}
		finally
		{
			writeToLog(source, response, DateHelper.diff(Calendar.getInstance(), start), request);
			router.clearRouteContext();
		}
	}

	private void writeToLog(String source, String response, Long diff, Request request)
	{
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();

		try
		{
			logEntry.setSystem(SYSTEM_ID);
			logEntry.setExecutionTime(diff);
			logEntry.setMessageRequest(source);
			logEntry.setMessageResponse(response);
			logEntry.setMessageType((request == null) ? "WebAPI_JAXBException" : request.getOperation().name());

			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(logEntry);
		}
		catch (Exception ex)
		{
			log.error("ѕроблемы с записью в журнал сообщений", ex);
		}
	}
}
