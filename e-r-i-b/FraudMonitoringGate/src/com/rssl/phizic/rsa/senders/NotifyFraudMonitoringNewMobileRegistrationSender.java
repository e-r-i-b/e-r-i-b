package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest;
import com.rssl.phizic.rsa.senders.builders.events.NotifyFraudMonitoringNewMobileRegistrationRequestBuilder;
import com.rssl.phizic.rsa.senders.initialization.MobileRegistrationInitializationData;

/**
 * @author vagin
 * @ created 06.02.15
 * @ $Author$
 * @ $Revision$
 * Сендер во фрод сообщения о регистрации мобильного приложения.
 */
public class NotifyFraudMonitoringNewMobileRegistrationSender extends FraudMonitoringNotifySenderBase<MobileRegistrationInitializationData>
{
	@Override
	protected NotifyRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new NotifyFraudMonitoringNewMobileRegistrationRequestBuilder()
				.append(getInitializationData())
				.build();
	}
}
