package com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc;

import com.rssl.phizgate.common.services.StubUrlWrapper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizicgate.sofia.messaging.Constants;
import com.rssl.phizicgate.sofia.messaging.SofiaMessageData;
import com.rssl.phizicgate.sofia.messaging.SofiaMessagingService;
import com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc.generated.IService1Service;
import com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc.generated.IService1Service_Impl;
import com.rssl.phizicgate.sofia.messaging.ryazan.jaxrpc.generated.IService1_PortType;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class JAXRPCSofiaMessagingService extends SofiaMessagingService
{
	private StubUrlWrapper<IService1_PortType> wrapper;

	public JAXRPCSofiaMessagingService(GateFactory factory) throws GateException
	{
		super(factory);
		wrapper = new StubUrlWrapper<IService1_PortType>(){
			protected void createStub()
			{
				IService1Service service = new IService1Service_Impl();
				try
				{
					setStub(service.getIService1_PortTypePort());
				}
				catch (ServiceException e)
				{
					throw new RuntimeException(e);
				}
			}
		};
	}

	protected MessageData makeRequest(MessageData request, MessageInfo messageInfo) throws GateException
	{
		SofiaMessageData data = (SofiaMessageData) request;
		String response;
		try
		{
			String messageName = messageInfo.getName();
			if (Constants.ATTRIBUTES_REQUEST.equals(messageName))
			{
				response = wrapper.getStub().requestAttr(data.getBody());
			}
			else if (Constants.EXECUTE_REQUEST.equals(messageName))
			{
				response = wrapper.getStub().executePayment(data.getBody());
			}
			else if (Constants.PREPARE_REQUEST.equals(messageName))
			{
				response = wrapper.getStub().preparePayment(data.getBody());
			}
			else if (Constants.REVOKE_REQUEST.equals(messageName))
			{
				response = wrapper.getStub().revokePayment(data.getBody());
			}
			else
			{
				response = wrapper.getStub().echoString(data.getBody()); //TODO throw new GateException("Запрос не поддерживается");
			}

//	        Тестовый запрос
//			String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml><messageId>12345</messageId><messageDate>date</messageDate><fromAbonent>ESK</fromAbonent><requestBillingAttr_q><CodeRecipient>CTC</CodeRecipient><CodeService>CTC_CTEL</CodeService></requestBillingAttr_q></xml>";
//			response = stub.requestAttr(str);
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}

		return new SofiaMessageData(response);
	}
}
