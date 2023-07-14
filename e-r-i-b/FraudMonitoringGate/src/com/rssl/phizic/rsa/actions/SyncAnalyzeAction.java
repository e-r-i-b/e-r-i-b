package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.RSAMainTransportProvider;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;

/**
 * Синхронный Analyze экшен взаимодействия с ВС ФМ
 *
 * @author tisov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public class SyncAnalyzeAction extends AnalyzeResponseProcessActionBase
{
	public SyncAnalyzeAction(AnalyzeRequest request)
	{
		this.request = request;
	}

	public void send() throws GateLogicException, GateException
	{
		process(new RSAMainTransportProvider<AnalyzeRequest, AnalyzeResponse>().send(new Pair(request, this)));
	}

	@Override
	protected String getClientTransactionId()
	{
		return request.getIdentificationData().getClientTransactionId();
	}
}
