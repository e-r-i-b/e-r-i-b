package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.integration.jaxb.generated.Request;
import com.rssl.phizic.rsa.integration.ws.RSAMainTransportProvider;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyResponse;

/**
 * Синхронный Notify экшен взаимодействия с ВС ФМ
 *
 * @author tisov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 * Экшн уведомления RSA-системы
 */
public class SyncNotifyAction extends NotifyResponseProviderActionBase
{
	public SyncNotifyAction(NotifyRequest request)
	{
		this.request = request;
	}

	public void send() throws GateException
	{
		process(new RSAMainTransportProvider<NotifyRequest, NotifyResponse>().send(new Pair(request, this)));
	}

	public void process(NotifyResponse response) throws GateException
	{
		Request jmsRequest = getRequestFromQueue(request.getIdentificationData().getClientTransactionId());
		handleTokens(jmsRequest.getDeviceResult().getDeviceData());
	}
}
