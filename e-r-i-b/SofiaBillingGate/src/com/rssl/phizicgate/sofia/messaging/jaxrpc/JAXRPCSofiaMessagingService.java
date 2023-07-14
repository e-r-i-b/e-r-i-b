package com.rssl.phizicgate.sofia.messaging.jaxrpc;

import com.rssl.phizgate.common.services.StubUrlWrapper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizicgate.sofia.messaging.SofiaMessageData;
import com.rssl.phizicgate.sofia.messaging.SofiaMessagingService;
import com.rssl.phizicgate.sofia.messaging.jaxrpc.generated.WebBankService;
import com.rssl.phizicgate.sofia.messaging.jaxrpc.generated.WebBankServiceIF;
import com.rssl.phizicgate.sofia.messaging.jaxrpc.generated.WebBankService_Impl;

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
	private StubUrlWrapper<WebBankServiceIF> wrapper;

	public JAXRPCSofiaMessagingService(GateFactory factory) throws GateException
	{
		super(factory);

		wrapper = new StubUrlWrapper<WebBankServiceIF>(){
			protected void createStub()
			{
				WebBankService service = new WebBankService_Impl();
				try
				{
					setStub(service.getWebBankServiceIFPort());
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
			response = wrapper.getStub().sendMessage(data.getBody());
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}

		return new SofiaMessageData(response);
	}
}
