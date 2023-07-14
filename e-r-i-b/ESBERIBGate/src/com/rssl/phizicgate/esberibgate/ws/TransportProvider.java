package com.rssl.phizicgate.esberibgate.ws;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.gate.monitoring.MonitoringLogHelper;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.AxisLoggerHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPTServiceLocator;
import com.rssl.phizicgate.esberibgate.ws.generated.ERIBAdapterPTSoapBindingStub;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Stub;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.soap.SOAPBody;

/**
 * @author krenev
 * @ created 10.09.2010
 * @ $Author$
 * @ $Revision$
 * Провайдер транспорного сервиса.
 */
public class TransportProvider
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final System SYSTEM_ID = System.esberib;
	private static Map<String, LogMonitoringTypeSelector> logMonitoringTypeMap;
	public static final String NAME_NEW_DEP_ADD_RQ = "NewDepAddRq";
	public static final String NAME_CARD_TO_NEW_DEP_ADD_RQ = "CardToNewDepAddRq";
	public static final String NAME_DEP_TO_NEW_DEP_ADD_RQ = "DepToNewDepAddRq";
	public static final String NAME_DEP_TO_NEW_IMA_ADD_RQ = "DepToNewIMAAddRq";
	public static final String NAME_CARD_TO_NEW_IMA_ADD_RQ = "CardToNewIMAAddRq";
	public static final String NAME_CARD_TO_IMA_ADD_RQ = "CardToIMAAddRq";
	public static final String NAME_IMA_TO_CARD_ADD_RQ = "IMAToCardAddRq";
	public static final String NAME_XFER_ADD_RQ = "XferAddRq";

	private interface LogMonitoringTypeSelector
	{
		String getDocumentType(SOAPBody requestBody);
	}

	private static class SimpleLogMonitoringTypeSelector implements LogMonitoringTypeSelector
	{
		private String documentType;

		public SimpleLogMonitoringTypeSelector(String documentType)
		{
			this.documentType = documentType;
		}

		public String getDocumentType(SOAPBody requestBody)
		{
			return documentType;
		}
	}

	private static class ParseRequestLogMonitoringTypeSelector implements LogMonitoringTypeSelector
	{
		public String getDocumentType(SOAPBody requestBody)
		{
			return XmlHelper.getSimpleElementValue(requestBody, "OperName");
		}
	}

	static
	{
		logMonitoringTypeMap = new HashMap<String, LogMonitoringTypeSelector>();
		logMonitoringTypeMap.put(NAME_NEW_DEP_ADD_RQ, new SimpleLogMonitoringTypeSelector(MonitoringDocumentType.TDO.name()));
		logMonitoringTypeMap.put(NAME_CARD_TO_NEW_DEP_ADD_RQ, new SimpleLogMonitoringTypeSelector(MonitoringDocumentType.TCDO.name()));
		logMonitoringTypeMap.put(NAME_DEP_TO_NEW_DEP_ADD_RQ, new SimpleLogMonitoringTypeSelector(MonitoringDocumentType.TDDO.name()));
		logMonitoringTypeMap.put(NAME_DEP_TO_NEW_IMA_ADD_RQ, new SimpleLogMonitoringTypeSelector(MonitoringDocumentType.TDIO.name()));
		logMonitoringTypeMap.put(NAME_CARD_TO_NEW_IMA_ADD_RQ, new SimpleLogMonitoringTypeSelector(MonitoringDocumentType.TCIO.name()));
		logMonitoringTypeMap.put(NAME_CARD_TO_IMA_ADD_RQ, new SimpleLogMonitoringTypeSelector(MonitoringDocumentType.TCI.name()));
		logMonitoringTypeMap.put(NAME_IMA_TO_CARD_ADD_RQ, new SimpleLogMonitoringTypeSelector(MonitoringDocumentType.TIC.name()));
		logMonitoringTypeMap.put(NAME_XFER_ADD_RQ, new ParseRequestLogMonitoringTypeSelector());
	}

	/**
	 * получить настроенную транспортную реализацию.
	 * @return транспорт.
	 * @deprecated необходимо использовать TransportProvider.doRequest. Данный метод разрешается использовать только в тестах и отладке.
	 */
	@Deprecated
	public static ERIBAdapterPTSoapBindingStub getTransport() throws GateException
	{
		try
		{
			//проверка активности внешней системы (проверка активности шины вцелом)
			ExternalSystemHelper.check(ExternalSystemHelper.getESBSystemCode());

			ERIBAdapterPTServiceLocator service = new ERIBAdapterPTServiceLocator();
			ERIBAdapterPTSoapBindingStub stub = (ERIBAdapterPTSoapBindingStub) service.getERIBAdapterPTServicePort();
			ESBEribConfig eribConfig = ConfigFactory.getConfig(ESBEribConfig.class);
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, eribConfig.getUrl());
			String timeout = eribConfig.getTimeout();
			stub.setTimeout(StringHelper.isEmpty(timeout) ? 0 : Integer.parseInt(timeout));
			return stub;
		}
		catch (javax.xml.rpc.ServiceException e)
		{
			throw new GateException(e);
		}
	}

	public static IFXRs_Type doRequest(IFXRq_Type ifxRq) throws GateException, GateLogicException
	{
		ERIBAdapterPTSoapBindingStub stub = getTransport();
		Calendar start = Calendar.getInstance();
		try
		{
			return stub.doIFX(ifxRq);
		}
		catch (RemoteException e)
		{
			if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
			{
				log.error("получена ошибка таймаута при отправке сообщения в КСШ.");
				throw new GateTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, e);
			}
			//Возникновение таймаута
			log.error("Получена техническая ошибка при отправке сообщения в КСШ.",e);
			throw new GateTimeOutException(GateWrapperTimeOutException.GATE_TECHNICAL_EXCEPTION_TIMEOUT_MESSAGE, e);
		}
		finally
		{
			try
			{
				writeToLog(stub._getCall().getMessageContext(), DateHelper.diff(Calendar.getInstance(), start));
			}
			catch (Exception ex)
			{
				log.error("Проблемы с записью в журнал сообщений", ex);
			}
		}
	}

	private static void writeToLog(MessageContext messageContext, Long duration) throws Exception
	{
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		Message request = messageContext.getRequestMessage();
		Message response = messageContext.getResponseMessage();
		SOAPBody requestBody = AxisLoggerHelper.getBody(request);

		logEntry.setSystem(SYSTEM_ID);

		logEntry.setMessageRequestId(AxisLoggerHelper.resolveMessageId(requestBody));
		logEntry.setMessageType(AxisLoggerHelper.resolveMessageType(requestBody));
		logEntry.setMessageRequest(AxisLoggerHelper.serializeMessage(request));

		SOAPBody responseBody = null;
		try
		{
			logEntry.setMessageResponse(AxisLoggerHelper.serializeMessage(response));
			responseBody = AxisLoggerHelper.getBody(response);
			logEntry.setMessageResponseId(AxisLoggerHelper.resolveMessageId(responseBody));
		}
		catch (Exception ex)
		{
			log.error("Проблемы с записью ответа в журнал сообщений", ex);
		}
		
		logEntry.setExecutionTime(duration);

		MessageLogWriter writer = MessageLogService.getMessageLogWriter();
		writer.write(logEntry);

		writeMonitoringLog(logEntry, requestBody, responseBody);
	}

	private static void writeMonitoringLog(MessagingLogEntry logEntry, SOAPBody requestBody, SOAPBody responseBody)
	{
		MonitoringOperationConfig config = ConfigFactory.getConfig(MonitoringOperationConfig.class);
		if (!config.isActive())
			return;

		LogMonitoringTypeSelector selector = logMonitoringTypeMap.get(logEntry.getMessageType());
		if (selector == null)
			return;

		String documentType = selector.getDocumentType(requestBody);;
		if (documentType == null)
			return;

		String errorCode = XmlHelper.getSimpleElementValue(responseBody, "StatusCode");
		MonitoringLogHelper.writeLog(logEntry, errorCode != null ? errorCode : "0", documentType);
	}
}
