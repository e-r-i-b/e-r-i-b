package com.rssl.phizic.rsa.integration.ws;

import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngineSOAPStub;
import com.rssl.phizic.rsa.integration.ws.notification.generated.UpdateActivityRequestType;
import com.rssl.phizic.rsa.integration.ws.notification.generated.UpdateActivityResponseType;

import java.rmi.RemoteException;

/**
 * @author tisov
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 * Обёртка для отправки запроса на post factum изменение фрод-вердикта по транзакции
 */
public class UpdateActivityTransportProvider extends ActivityEngineTransportProvider<UpdateActivityRequestType, UpdateActivityResponseType>
{
	private static final UpdateActivityTransportProvider INSTANCE = new UpdateActivityTransportProvider();

	private UpdateActivityTransportProvider() {};

	/**
	 * @return инстанс синглтона
	 */
	public static UpdateActivityTransportProvider getInstance()
	{
		return INSTANCE;
	}

	@Override
	protected UpdateActivityResponseType doSend(ActivityEngineSOAPStub stub, UpdateActivityRequestType request) throws RSAIntegrationException
	{
		try
		{
			return stub.updateActivity(request);
		}
		catch (RemoteException e)
		{
			throw new RSAIntegrationException(e);
		}
	}
}
