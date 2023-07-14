/**
 * OfflineSrvSoap_BindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.listener.sbrf;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.listener.ListenerMessageReceiver;
import com.rssl.phizic.web.util.ExceptionLogHelper;

import java.rmi.RemoteException;

public class OfflineSrvSoap_BindingImpl implements com.rssl.phizic.ws.listener.sbrf.OfflineSrvSoap_PortType
{
	public java.lang.String getXMLmessage(java.lang.String xmlstr) throws java.rmi.RemoteException
	{
		ListenerMessageReceiver messageReceiver;
		String result;
		try
		{
			messageReceiver = GateSingleton.getFactory().service(ListenerMessageReceiver.class);
			result = messageReceiver.handleMessage(xmlstr);
		}
		catch(Exception e)
		{
			ExceptionLogHelper.writeLogMessage(e);
			throw new RemoteException("Internal error", e);
		}
		return result;
	}
}
