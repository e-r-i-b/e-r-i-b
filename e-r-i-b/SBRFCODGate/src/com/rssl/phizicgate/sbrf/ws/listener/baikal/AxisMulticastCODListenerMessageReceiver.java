package com.rssl.phizicgate.sbrf.ws.listener.baikal;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.config.cod.CodGateConfig;
import com.rssl.phizicgate.sbrf.ws.listener.baikal.generated.axis.OfflineSrvLocator;
import com.rssl.phizicgate.sbrf.ws.listener.baikal.generated.axis.OfflineSrvSoap_PortType;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author krenev
 * @ created 01.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class AxisMulticastCODListenerMessageReceiver extends MulticastCODListenerMessageReceiverBase
{
	public AxisMulticastCODListenerMessageReceiver(GateFactory factory) throws GateException
	{
		super(factory);
	}

	private OfflineSrvSoap_PortType getStub() throws GateException
	{
		try
		{
			OfflineSrvLocator codLocator = new OfflineSrvLocator();
			CodGateConfig config = ConfigFactory.getConfig(CodGateConfig.class);
			codLocator.setOfflineSrvSoapEndpointAddress(config.getSBOLUrl());
			return codLocator.getOfflineSrvSoap();
		}
		catch (ServiceException e)
		{
			throw new GateException(e);
		}
	}

	protected String multicast(String xmlMessage) throws GateException
	{
		try
		{
			return getStub().getXMLmessage(xmlMessage);
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}
}
