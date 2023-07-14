package com.rssl.phizicgate.sofia.messaging.ryazan.axis;

import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizicgate.sofia.messaging.SofiaMessagingService;
import com.rssl.phizicgate.sofia.messaging.SofiaMessageData;
import com.rssl.phizicgate.sofia.messaging.Constants;
import com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1_PortType;
import com.rssl.phizicgate.sofia.messaging.ryazan.axis.generated.IService1_ServiceLocator;

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

	private IService1_PortType getStub() throws GateException
	{
		try
		{
			GateConnectionConfig connectionConfig = ConfigFactory.getConfig(GateConnectionConfig.class);
			IService1_ServiceLocator locator = new IService1_ServiceLocator();
			locator.setIService1PortEndpointAddress(connectionConfig.getURL());

			return locator.getIService1Port();
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
			String messageName = messageInfo.getName();
			if (Constants.ATTRIBUTES_REQUEST.equals(messageName))
			{
				response = getStub().requestAttr(data.getBody());
			}
			else if (Constants.EXECUTE_REQUEST.equals(messageName))
			{
				response = getStub().executePayment(data.getBody());
			}
			else if (Constants.PREPARE_REQUEST.equals(messageName))
			{
				response = getStub().preparePayment(data.getBody());
			}
			else if (Constants.REVOKE_REQUEST.equals(messageName))
			{
				response = getStub().revokePayment(data.getBody());
			}
			else
			{
				response = getStub().echoString(data.getBody());//TODO throw new GateException("Запрос не поддерживается");
			}
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}

		return new SofiaMessageData(response);
	}
}