package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.notification.generated.UpdateActivityRequestType;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;

/**
 * Created with IntelliJ IDEA.
 * User: tisov
 * Date: 19.08.15
 * Time: 18:53
 * Сендер онлайн отправки UpdateActivity-сообщений в ActivityEngine
 */
public class OfflineUpdateActivitySender extends UpdateActivitySender
{

	@Override
	protected UpdateActivityRequestType createOnLineRequest() throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Метод не поддерживается оффлайн-сендером");
	}

	@Override
	public FraudMonitoringRequestSendingType getSendingType()
	{
		return FraudMonitoringRequestSendingType.OFFLINE;
	}
}
