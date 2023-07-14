package com.rssl.phizic.test.webgate.cpfl.generated.jaxrpc;

import com.rssl.phizicgate.cpfl.mock.MockRequestProcessor;
import com.rssl.phizic.gate.exceptions.GateException;

import java.rmi.RemoteException;

/**
 * @author krenev
 * @ created 16.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class CommunalServiceService implements CommunalServicePT
{
	public String sendMessage(String sendMessageRequest) throws RemoteException
	{
		try
		{
			return MockRequestProcessor.processMessage(sendMessageRequest);
		}
		catch (GateException e)
		{
			throw new RemoteException("Ошибка при обработке запроса", e);
		}
	}
}
