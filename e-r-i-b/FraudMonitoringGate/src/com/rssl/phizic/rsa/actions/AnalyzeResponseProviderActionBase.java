package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AdaptiveAuthenticationSoapBindingStub;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse;

import java.rmi.RemoteException;

/**
 * Analyze базовый экшен взаимодействия с ВС ФМ
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeResponseProviderActionBase extends ResponseProviderActionBase<AnalyzeRequest, AnalyzeResponse>
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public AnalyzeResponse doSend(AdaptiveAuthenticationSoapBindingStub stub) throws RSAIntegrationException
	{
		try
		{
			return stub.analyze(this.request);
		}
		catch (RemoteException e)
		{
			throw new RSAIntegrationException(e);
		}
	}
}
