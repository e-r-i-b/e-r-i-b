package com.rssl.phizicgate.iqwave.messaging.jaxrpc;

import com.rssl.phizgate.common.services.StubTimeoutUrlWrapper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.iqwave.messaging.IQWaveMessageData;
import com.rssl.phizicgate.iqwave.messaging.IQWaveMessagingService;
import com.rssl.phizicgate.iqwave.messaging.jaxrpc.generated.IQWave4EskProtType;
import com.rssl.phizicgate.iqwave.messaging.jaxrpc.generated.IQWave4EskService;
import com.rssl.phizicgate.iqwave.messaging.jaxrpc.generated.IQWave4EskService_Impl;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author gladishev
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class JAXRPCIQWaveMessagingService extends IQWaveMessagingService
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	private StubTimeoutUrlWrapper<IQWave4EskProtType> wrapper;

	public JAXRPCIQWaveMessagingService(GateFactory factory) throws GateException
	{
		super(factory);
		wrapper = new StubTimeoutUrlWrapper<IQWave4EskProtType>(){

			protected void createStub()
			{
				IQWave4EskService service = new IQWave4EskService_Impl();
				try
				{
					setStub(service.getIQWave4Esk());
				}
				catch (ServiceException e)
				{
					throw new RuntimeException(e);
				}
			}
		};
	}

	protected MessageData makeRequest(MessageData request, MessageInfo messageInfo) throws GateException, GateLogicException
	{
		String response;
		try
		{
			response = wrapper.getStub().getXMLmessage(request.getBodyAsString(null));
		}
		catch (RemoteException e)
		{
			if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
			{
				log.error("получена ошибка таймаута при отправке сообщения в IQWave.");
				throw new GateTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, e);
			}
			throw new GateException(e);
		}

		return new IQWaveMessageData(response);
	}
}
