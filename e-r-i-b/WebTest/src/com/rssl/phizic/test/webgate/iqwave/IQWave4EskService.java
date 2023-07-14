package com.rssl.phizic.test.webgate.iqwave;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.test.webgate.iqwave.generated.IQWave4EskProtType;
import com.rssl.phizicgate.iqwave.messaging.mock.MockRequestProcessor;

import java.rmi.RemoteException;

/**
 * @author gladishev
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWave4EskService implements IQWave4EskProtType
{
	public String getXMLmessage(String getXMLmessageRequest) throws RemoteException
	{
		try
		{
			return MockRequestProcessor.processMessage(getXMLmessageRequest);
		}
		catch (GateException e)
		{
			throw new RemoteException("Ошибка при обработке запроса", e);
		}
	}
}
