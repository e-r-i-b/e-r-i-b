package com.rssl.phizicgate.wsgateclient.services.basket;

import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizicgate.wsgateclient.services.basket.generated.BasketRouteService;
import com.rssl.phizicgate.wsgateclient.services.basket.generated.BasketRouteServiceImpl_Impl;
import com.rssl.phizicgate.wsgateclient.services.basket.generated.BasketRouteService_Stub;
import com.sun.xml.rpc.client.ClientTransport;
import com.sun.xml.rpc.client.ClientTransportFactory;
import com.sun.xml.rpc.client.StubBase;

/**
 * @author niculichev
 * @ created 02.06.15
 * @ $Author$
 * @ $Revision$
 */
public class BasketRouteServiceWrapper implements BasketRouteService
{
	private static final String ROUTE_SERVICE_WS_NAME = "BasketRouteServiceImpl";
	private Long blockNumber;

	public BasketRouteServiceWrapper(Long blockNumber)
	{
		this.blockNumber = blockNumber;
	}

	public void acceptBillBasketExecute(String request) throws java.rmi.RemoteException
	{
		getStub().acceptBillBasketExecute(request);
	}

	public void addBillBasketInfo(String request, String messageId) throws java.rmi.RemoteException
	{
		getStub().addBillBasketInfo(request, messageId);
	}

	private BasketRouteService getStub()
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);

		String serviceUrl = String.format(config.getServiceRedirectUrl(blockNumber), ROUTE_SERVICE_WS_NAME);
		BasketRouteServiceImpl_Impl serviceImpl = new BasketRouteServiceImpl_Impl();
		BasketRouteService_Stub stub = (BasketRouteService_Stub) serviceImpl.getBasketRouteServicePort();
		stub._setProperty(StubBase.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);
		stub._setTransportFactory(getClientTimeoutTransportFactory(config.getBasketServiceAddInfoTimeOut()));

		return stub;
	}

	protected ClientTransportFactory getClientTimeoutTransportFactory(final int timeout)
	{
		return new ClientTransportFactory()
		{
			public ClientTransport create()
			{
				return new TimeoutHttpTransport(timeout);
			}
		};
	}
}
