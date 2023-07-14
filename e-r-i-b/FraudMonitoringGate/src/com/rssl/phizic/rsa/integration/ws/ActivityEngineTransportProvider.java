package com.rssl.phizic.rsa.integration.ws;

import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngineSOAPStub;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ActivityEngine_ServiceLocator;

import javax.xml.rpc.ServiceException;

/**
 * @author tisov
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс обёртки взаимодействия со вспомогательный веб-сервисом фрод-мониторинга
 */
public abstract class ActivityEngineTransportProvider<RQ, RS> extends TransportProviderBase<ActivityEngineSOAPStub, RQ, RS>
{
	@Override
	protected ActivityEngineSOAPStub createStub() throws RSAIntegrationException
	{
		try
		{
			ActivityEngine_ServiceLocator locator = new ActivityEngine_ServiceLocator();
			return (ActivityEngineSOAPStub) locator.getPort(ActivityEngineSOAPStub.class);
		}
		catch (ServiceException e)
		{
			throw new RSAIntegrationException(e);
		}
	}

	@Override
	protected String getUrl()
	{
		return RSAConfig.getInstance().getActivityEngineUrl();
	}

	@Override
	protected int getTimeOut()
	{
		return RSAConfig.getInstance().getWSTimeOut() * 1000;
	}
}