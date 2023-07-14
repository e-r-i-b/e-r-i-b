package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.integration.ws.RSAMainTransportProvider;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse;

/**
 * Analyze экшен первого шага асинхронного взаимодействия с ВС ФМ
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ASyncAnalyzeSendingPhaseAction extends AnalyzeResponseProviderActionBase
{
	public ASyncAnalyzeSendingPhaseAction(AnalyzeRequest request)
	{
		this.request = request;
	}

	public void send() throws GateException
	{
		new RSAMainTransportProvider<AnalyzeRequest, AnalyzeResponse>().send(new Pair(request, this));
	}

	public void process(AnalyzeResponse response) {}
}
