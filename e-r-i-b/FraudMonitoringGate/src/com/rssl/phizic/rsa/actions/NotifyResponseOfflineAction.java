package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.integration.ws.RSAMainTransportProvider;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyResponse;

/**
 * @author tisov
 * @ created 20.07.15
 * @ $Author$
 * @ $Revision$
 * Экшн оффлайновой отправки запроса-оповещения в систему фрод-мониторинга
 */
public class NotifyResponseOfflineAction extends NotifyResponseProviderActionBase
{
	public NotifyResponseOfflineAction(NotifyRequest request)
	{
		this.request = request;
	}

	public void send() throws GateException
	{
		Pair<NotifyRequest, ResponseProviderAction<NotifyRequest, NotifyResponse>> pair = new Pair<NotifyRequest, ResponseProviderAction<NotifyRequest, NotifyResponse>>(request, this);
		new RSAMainTransportProvider<NotifyRequest, NotifyResponse>().send(pair);
	}

	public void process(NotifyResponse response) {}
}
