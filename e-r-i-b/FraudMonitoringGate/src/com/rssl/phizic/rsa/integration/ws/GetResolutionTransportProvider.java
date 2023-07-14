package com.rssl.phizic.rsa.integration.ws;

import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngineSOAPStub;
import com.rssl.phizic.rsa.integration.ws.notification.generated.GetResolutionRequestType;
import com.rssl.phizic.rsa.integration.ws.notification.generated.GetResolutionResponseType;

import java.rmi.RemoteException;

/**
 * @author tisov
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 * Обёртка взаимодействия по запросу на получение актуального вердикта по заданной транзакции
 */
public class GetResolutionTransportProvider extends ActivityEngineTransportProvider<GetResolutionRequestType, GetResolutionResponseType>
{
	private static final GetResolutionTransportProvider INSTANCE = new GetResolutionTransportProvider();

	private GetResolutionTransportProvider() {};

	/**
	 * @return Инстанс синглтона
	 */
	public static GetResolutionTransportProvider getInstance()
	{
		return INSTANCE;
	}

	@Override
	protected GetResolutionResponseType doSend(ActivityEngineSOAPStub stub, GetResolutionRequestType request) throws RSAIntegrationException
	{
		try
		{
			return stub.getResolution(request);
		}
		catch (RemoteException e)
		{
			throw new RSAIntegrationException(e);
		}
	}
}
