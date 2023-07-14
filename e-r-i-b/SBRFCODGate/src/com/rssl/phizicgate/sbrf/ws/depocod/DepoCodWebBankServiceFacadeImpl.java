package com.rssl.phizicgate.sbrf.ws.depocod;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.ResponseHandler;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.impl.MessagingServiceSupport;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizicgate.sbrf.depoCOD.DepoCODTransportProvider;
import com.rssl.phizicgate.sbrf.ws.Constants;
import com.rssl.phizicgate.sbrf.ws.generated.depocod.DepoCODStub;

import java.rmi.RemoteException;
import static com.rssl.phizicgate.sbrf.senders.depocod.DepoCodSenderBase.ACC2ACC143_REQUEST;
import static com.rssl.phizicgate.sbrf.senders.depocod.DepoCodSenderBase.ACC2ACC_REQUEST;
import static com.rssl.phizicgate.sbrf.senders.depocod.DepoCodSenderBase.ACC2ACC365_REQUEST;
import static com.rssl.phizicgate.sbrf.senders.depocod.DepoCodSenderBase.CHECK_STATUS_REQUEST;
/**
 * Сервис для отправки сообщений в DepoCOD
 * @author gladishev
 * @ created 18.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class DepoCodWebBankServiceFacadeImpl extends MessagingServiceSupport
{
	private static final String ENDPOINT_COD = "DepoCOD";
	private static final String UNKNOWN_REQUEST = "Неизвестный запрос: ";
	private static final String BAD_ENDPOINT = "Недопустимый тип системы получателя: ";

	/**
	 * ctor
	 * @param factory фабрика гейта
	 */
	public DepoCodWebBankServiceFacadeImpl(GateFactory factory) throws GateException
	{
		super(factory, MessageLogService.getMessageLogWriter(), ENDPOINT_COD, ConfigurationLoader.load(Constants.OUTGOING_MESSAGES_CONFIG));
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException, GateLogicException
	{
		if(!messageInfo.getEndpoint().equals(ENDPOINT_COD))
			throw new GateException(BAD_ENDPOINT + messageInfo.getEndpoint());

		byte[] responce = null;
		try
		{
			DepoCODStub transport = DepoCODTransportProvider.getTransport();
			String requestName = messageInfo.getName();
			if (ACC2ACC143_REQUEST.equals(requestName))
				responce = transport.acc2Acc143((byte[]) messageData.getBody());
			else if (ACC2ACC_REQUEST.equals(requestName))
				responce = transport.acc2Acc((byte[]) messageData.getBody());
			else if (ACC2ACC365_REQUEST.equals(requestName))
				responce = transport.acc2Acc365((byte[]) messageData.getBody());
			else if (CHECK_STATUS_REQUEST.equals(requestName))
				responce = transport.operationFullResult((byte[]) messageData.getBody());
			else
				throw new GateException(UNKNOWN_REQUEST + requestName);
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}

		return new DepoCODResponseMessageData(responce, messageData.getId());
	}

	protected ResponseHandler parseResponse(MessageData response, MessageInfo messageInfo) throws GateLogicException, GateException
	{
		return new DepoCodFormatResponseHandler((DepoCODResponseMessageData)response, messageInfo.getResponses());
	}

	public MessagesCacheManager getMessagesCacheManager()
	{
		return null;
	}
}
