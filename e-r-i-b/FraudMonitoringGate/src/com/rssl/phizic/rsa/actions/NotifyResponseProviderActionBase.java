package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AdaptiveAuthenticationSoapBindingStub;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyResponse;

import java.rmi.RemoteException;

/**
 * Notify базовый экшен взаимодействия с ВС ФМ
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class NotifyResponseProviderActionBase extends ResponseProviderActionBase<NotifyRequest, NotifyResponse>
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public NotifyResponse doSend(AdaptiveAuthenticationSoapBindingStub stub) throws RSAIntegrationException
	{
		try
		{
			return stub.notify(this.request);
		}
		catch (RemoteException e)
		{
			throw new RSAIntegrationException(e);
		}
	}
}
