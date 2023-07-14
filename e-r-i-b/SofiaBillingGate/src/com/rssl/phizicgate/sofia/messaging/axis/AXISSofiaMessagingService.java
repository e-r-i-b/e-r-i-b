package com.rssl.phizicgate.sofia.messaging.axis;

import com.rssl.phizicgate.sofia.messaging.SofiaMessagingService;
import com.rssl.phizicgate.sofia.messaging.SofiaMessageData;
import com.rssl.phizicgate.sofia.messaging.axis.generated.WebBankServiceLocator;
import com.rssl.phizicgate.sofia.messaging.axis.generated.WebBankServiceIFBindingStub;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.config.ConfigFactory;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class AXISSofiaMessagingService extends SofiaMessagingService
{
	public AXISSofiaMessagingService(GateFactory factory) throws GateException
	{
		super(factory);
	}

	private WebBankServiceIFBindingStub getStub() throws GateException
	{
		try
		{
			GateConnectionConfig connectionConfig = ConfigFactory.getConfig(GateConnectionConfig.class);
			WebBankServiceLocator codLocator = new WebBankServiceLocator();
            codLocator.setWebBankServiceIFPortEndpointAddress(connectionConfig.getURL());
            return(WebBankServiceIFBindingStub) codLocator.getWebBankServiceIFPort();
		}
		catch (ServiceException e)
		{
			throw new GateException(e);
		}
	}

	protected MessageData makeRequest(MessageData request, MessageInfo messageInfo) throws GateException
	{
		SofiaMessageData data = (SofiaMessageData) request;
		String response;
		try
		{
			response = getStub().sendMessage(data.getBody());
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}

		return new SofiaMessageData(response);
	}
}