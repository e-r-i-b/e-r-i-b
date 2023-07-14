package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.rsa.integration.ws.control.generated.AdaptiveAuthenticationSoapBindingStub;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse;

/**
 * Заглушка на время отладки
 *
 * @author khudyakov
 * @ created 05.02.15
 * @ $Author$
 * @ $Revision$
 */
public class NullAction<RQ extends GenericRequest, RS extends GenericResponse> implements ResponseProviderAction<RQ, RS>
{
	public RS doSend(AdaptiveAuthenticationSoapBindingStub stub)
	{
		throw new UnsupportedOperationException();
	}

	public void send() {}

	public void process(RS response) {}

	public RQ getRequest()
	{
		throw new UnsupportedOperationException();
	}
}
