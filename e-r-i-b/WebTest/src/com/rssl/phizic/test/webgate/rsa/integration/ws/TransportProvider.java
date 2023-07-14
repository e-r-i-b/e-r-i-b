package com.rssl.phizic.test.webgate.rsa.integration.ws;

import com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.FraudMonitoringAsyncInteractionServiceSoapBindingStub;
import com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.FraudMonitoringAsyncInteractionService_ServiceLocator;
import com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.Request_Type;
import com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.Response_Type;
import org.apache.axis.client.Stub;

import javax.xml.rpc.ServiceException;

/**
 * @author khudyakov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */
public class TransportProvider
{
	private static final String ENDPOINT_ADDRESS_PROPERTY = "http://localhost:8888/FraudMonitoringBackGateApp/services/FraudMonitoringAsyncInteractionService";
	private static final int TIME_OUT_PROPERTY = 5000;

	private static FraudMonitoringAsyncInteractionServiceSoapBindingStub getTransport() throws ServiceException
	{
		FraudMonitoringAsyncInteractionService_ServiceLocator service = new FraudMonitoringAsyncInteractionService_ServiceLocator();
		FraudMonitoringAsyncInteractionServiceSoapBindingStub stub = (FraudMonitoringAsyncInteractionServiceSoapBindingStub) service.getPort(FraudMonitoringAsyncInteractionServiceSoapBindingStub.class);
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, ENDPOINT_ADDRESS_PROPERTY);
		stub.setTimeout(TIME_OUT_PROPERTY);
		return stub;
	}

	public static Response_Type send(Request_Type request) throws Exception
	{
		FraudMonitoringAsyncInteractionServiceSoapBindingStub stub = getTransport();
		return stub.send(request);
	}
}
