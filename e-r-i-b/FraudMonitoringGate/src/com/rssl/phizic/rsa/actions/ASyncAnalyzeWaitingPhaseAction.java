package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AdaptiveAuthenticationSoapBindingStub;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;

/**
 * Analyze экшен второго шага асинхронного взаимодействия с ВС ФМ
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ASyncAnalyzeWaitingPhaseAction extends AnalyzeResponseProcessActionBase
{
	public AnalyzeResponse doSend(AdaptiveAuthenticationSoapBindingStub stub)
	{
		throw new UnsupportedOperationException();
	}

	public void send() throws GateLogicException, GateException
	{
		process(null);
	}

	@Override
	protected String getClientTransactionId()
	{
		return FraudMonitoringRequestHelper.restoreClientTransactionId();
	}
}
