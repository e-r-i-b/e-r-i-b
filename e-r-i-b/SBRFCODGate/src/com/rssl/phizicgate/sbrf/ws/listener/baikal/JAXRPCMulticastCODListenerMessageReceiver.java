package com.rssl.phizicgate.sbrf.ws.listener.baikal;

import com.rssl.phizgate.common.services.StubTimeoutUrlWrapper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.config.cod.CodGateConfig;
import com.rssl.phizicgate.sbrf.ws.listener.baikal.generated.jaxrpc.OfflineSrvSoap;
import com.rssl.phizicgate.sbrf.ws.listener.baikal.generated.jaxrpc.OfflineSrv_Impl;

import java.rmi.RemoteException;

/**
 * @author krenev
 * @ created 01.10.2010
 * @ $Author$
 * @ $Revision$
 *
 */
public class JAXRPCMulticastCODListenerMessageReceiver extends MulticastCODListenerMessageReceiverBase
{
	private StubTimeoutUrlCodListenerWrapper wrapper;

	public JAXRPCMulticastCODListenerMessageReceiver(GateFactory factory) throws GateException
	{
		super(factory);
		wrapper = new StubTimeoutUrlCodListenerWrapper();
	}

	protected String multicast(String xmlMessage) throws GateException
	{
		try
		{
			return wrapper.getStub().getXMLmessage(xmlMessage);
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}

	private class StubTimeoutUrlCodListenerWrapper extends StubTimeoutUrlWrapper<OfflineSrvSoap>
	{
		protected String getUrl()
		{
			CodGateConfig config = ConfigFactory.getConfig(CodGateConfig.class);
			return config.getSBOLUrl();
		}

		protected void createStub()
		{
			OfflineSrv_Impl offlineSrv = new OfflineSrv_Impl();
			setStub(offlineSrv.getOfflineSrvSoap());
		}
	}
}
