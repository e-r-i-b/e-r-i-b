package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizgate.common.services.StubTimeoutUrlWrapper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.cpfl.messaging.generated.jaxrpc.CommunalServicePT;
import com.rssl.phizicgate.cpfl.messaging.generated.jaxrpc.CommunalServiceService;
import com.rssl.phizicgate.cpfl.messaging.generated.jaxrpc.CommunalServiceService_Impl;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author krenev
 * @ created 16.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class JaxrpcCPFLMessagingService extends AbstractCPFLMessagingService
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	private StubTimeoutUrlWrapper<CommunalServicePT> wrapper;

	/**
	 * ctor
	 * @param factory фабрика гейта
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public JaxrpcCPFLMessagingService(GateFactory factory) throws GateException
	{
		super(factory);
		wrapper = new StubTimeoutUrlWrapper<CommunalServicePT>(){
			protected void createStub()
			{
				CommunalServiceService service = new CommunalServiceService_Impl();
				try
				{
					setStub(service.getCommunalServicePort());
				}
				catch (ServiceException e)
				{
					throw new RuntimeException(e);
				}
			}
		};
	}

	protected MessageData makeRequest(MessageData messageData, MessageInfo messageInfo) throws GateException, GateTimeOutException
	{
		CPFLMessageData request = (CPFLMessageData) messageData;
		try
		{
			return new CPFLMessageData(wrapper.getStub().sendMessage(request.getBody()));
		}
		catch (RemoteException e)
		{
			if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
			{
				log.error("получена ошибка таймаута при отправке сообщения в ЦПФЛ.");
				throw new GateTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, e);
			}
			throw new GateException(e);
		}
	}
}
