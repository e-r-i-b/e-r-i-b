package com.rssl.phizic.ws.listener.iqwave;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.listener.ListenerMessageReceiver;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.ws.listener.iqwave.generated.Esk4IQWaveProtType;

import java.rmi.RemoteException;

public class Esk4IQWaveService implements Esk4IQWaveProtType
{
	private static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	public String getXMLmessage(String request) throws RemoteException
	{
		try
		{
			ListenerMessageReceiver receiver = GateSingleton.getFactory().service(ListenerMessageReceiver.class);
			return receiver.handleMessage(request);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RemoteException("Internal error", e);
		}
	}
}
