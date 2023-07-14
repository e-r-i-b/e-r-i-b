package com.rssl.phizic.rsa.integration.ws;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.rsa.actions.ResponseProviderAction;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AdaptiveAuthenticationLocator;
import com.rssl.phizic.rsa.integration.ws.control.generated.AdaptiveAuthenticationSoapBindingStub;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse;

import javax.xml.rpc.ServiceException;

/**
 * @author tisov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 * Транспорт-провайдер фрод-мониторинга
 */
public class RSAMainTransportProvider<RQ extends GenericRequest, RS extends GenericResponse> extends TransportProviderBase<AdaptiveAuthenticationSoapBindingStub, Pair<RQ, ResponseProviderAction<RQ, RS>>, RS>
{

	@Override
	protected AdaptiveAuthenticationSoapBindingStub createStub() throws RSAIntegrationException
	{
		try
		{
			AdaptiveAuthenticationLocator locator = new AdaptiveAuthenticationLocator();
			return (AdaptiveAuthenticationSoapBindingStub) locator.getPort(AdaptiveAuthenticationSoapBindingStub.class);
		}
		catch (ServiceException e)
		{
			throw new RSAIntegrationException(e);
		}
	}

	@Override
	protected RS doSend(AdaptiveAuthenticationSoapBindingStub stub, Pair<RQ, ResponseProviderAction<RQ, RS>> request) throws RSAIntegrationException
	{
		ResponseProviderAction<RQ, RS> action = request.getSecond();
		return action.doSend(stub);
	}

	@Override
	protected String getUrl()
	{
		return RSAConfig.getInstance().getUrl();
	}

	@Override
	protected int getTimeOut()
	{
		return RSAConfig.getInstance().getWSTimeOut() * 1000;
	}
}