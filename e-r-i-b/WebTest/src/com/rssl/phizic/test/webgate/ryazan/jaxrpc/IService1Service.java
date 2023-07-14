package com.rssl.phizic.test.webgate.ryazan.jaxrpc;

import com.rssl.phizic.test.webgate.ryazan.common.MessageUpdate;
import com.rssl.phizic.test.webgate.ryazan.jaxrpc.generated.IService1_PortType;

import java.rmi.RemoteException;

/**
 * @author gladishev
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class IService1Service implements IService1_PortType
{
	public String echoString(String sendMessageRequest) throws RemoteException
	{
		return sendMessageRequest;
	}

	public String requestAttr(String sendMessageRequest) throws RemoteException
	{
		return new MessageUpdate().requestAttr(sendMessageRequest);
	}

	public String preparePayment(String sendMessageRequest) throws RemoteException
	{
		return new MessageUpdate().preparePayment(sendMessageRequest);
	}

	public String executePayment(String sendMessageRequest) throws RemoteException
	{
		return new MessageUpdate().executePayment(sendMessageRequest);
	}

	public String revokePayment(String sendMessageRequest) throws RemoteException
	{
		return new MessageUpdate().revokePayment(sendMessageRequest);
	}
}
