package com.rssl.phizicgate.sbrf.depoCOD;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.sbrf.ws.generated.depocod.DepoCODStub;
import com.rssl.phizicgate.sbrf.ws.generated.depocod.DepoCOD_ServiceLocator;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Stub;

import java.rmi.RemoteException;
import java.util.Calendar;
import javax.xml.soap.SOAPBody;

/**
 * Провайдер DepoCOD
 * @author Jatsky
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */

public class DepoCODTransportProvider
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final com.rssl.phizic.logging.messaging.System SYSTEM_ID = System.cod;

	/**
	 * получить настроенную транспортную реализацию.
	 * @return транспорт.
	 * @deprecated необходимо использовать TransportProvider.doRequest. Данный метод разрешается использовать только в тестах и отладке.
	 */
	@Deprecated
	public static DepoCODStub getTransport() throws GateException
	{
		try
		{
			DepoCOD_ServiceLocator service = new DepoCOD_ServiceLocator();
			DepoCODStub stub = (DepoCODStub) service.getDepoCOD();
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, ConfigFactory.getConfig(DepoCODConfig.class).getUrl());
			String timeout = ConfigFactory.getConfig(DepoCODConfig.class).getTimeout();
			stub.setTimeout(StringHelper.isEmpty(timeout) ? 0 : Integer.parseInt(timeout));
			return stub;
		}
		catch (javax.xml.rpc.ServiceException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получить ответ на запрос детальной выписки по счёту
	 * @param request - запрос
	 * @return ответ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static byte[] doGetAccHistoryFullExtractRequest(byte[] request) throws GateException, GateLogicException
	{
		DepoCODStub stub = getTransport();
		Calendar start = Calendar.getInstance();
		try
		{
			return stub.getAccHistoryFullExtract(request);
		}
		catch (RemoteException e)
		{
			checkRemoteException(e);
			throw new GateException(e);
		}
		finally
		{
			writeToLog(stub._getCall().getMessageContext(), DateHelper.diff(Calendar.getInstance(), start));
		}
	}

	private static void writeToLog(MessageContext messageContext, Long duration)
	{
		try
		{
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			Message request = messageContext.getRequestMessage();
			Message response = messageContext.getResponseMessage();
			SOAPBody requestBody = AxisLoggerHelper.getBody(request);

			logEntry.setSystem(SYSTEM_ID);

			logEntry.setMessageRequestId(AxisLoggerHelper.resolveMessageId(requestBody));
			logEntry.setMessageType(AxisLoggerHelper.resolveMessageDepoCODType(requestBody));
			logEntry.setMessageRequest(AxisLoggerHelper.serializeMessage(request));

			try
			{
				logEntry.setMessageResponse(AxisLoggerHelper.serializeMessage(response));
				SOAPBody responseBody = AxisLoggerHelper.getBody(response);
				logEntry.setMessageResponseId(AxisLoggerHelper.resolveMessageId(responseBody));
			}
			catch (Exception ex)
			{
				log.error("Проблемы с записью ответа в журнал сообщений", ex);
			}

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
	 * Подучить ответ на запрос остатка по счёту
	 * @param request - запрос
	 * @return ответ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static byte[] doAccountRemainderRequest(byte[] request) throws GateException, GateLogicException
	{
		DepoCODStub stub = getTransport();
		Calendar start = Calendar.getInstance();
		try
		{
			return stub.accountRemainder(request);
		}
		catch (RemoteException e)
		{
			checkRemoteException(e);
			throw new GateException(e);
		}
		finally
		{
			writeToLog(stub._getCall().getMessageContext(), DateHelper.diff(Calendar.getInstance(), start));
		}
	}

	private static void checkRemoteException(RemoteException e) throws GateTimeOutException, GateException
	{
		if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
		{
			log.error("получена ошибка таймаута при отправке сообщения.");
			throw new GateTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, e);
		}
	}
}
