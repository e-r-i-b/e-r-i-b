package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.integration.ws.RSAMainTransportProvider;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse;

/**
 * @author tisov
 * @ created 20.07.15
 * @ $Author$
 * @ $Revision$
 * Экшн отправки оффлайнового запроса на анализ в систему фрод-мониторинга
 */
public class AnalyzeResponseOfflineAction extends AnalyzeResponseProviderActionBase
{
	public AnalyzeResponseOfflineAction(AnalyzeRequest request)
	{
		this.request = request;
	}

	public void send() throws GateException
	{
		Pair<AnalyzeRequest, ResponseProviderAction<AnalyzeRequest, AnalyzeResponse>> pair = new Pair<AnalyzeRequest, ResponseProviderAction<AnalyzeRequest, AnalyzeResponse>>(request, this);
		new RSAMainTransportProvider<AnalyzeRequest, AnalyzeResponse>().send(pair);
	}

	public void process(AnalyzeResponse response) {}
}
