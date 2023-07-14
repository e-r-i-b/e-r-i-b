package com.rssl.phizic.rsa.senders.events;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest;
import com.rssl.phizic.rsa.senders.FraudMonitoringNotifySenderBase;
import com.rssl.phizic.rsa.senders.builders.events.NotifyAPIFailedMobileLogInRequestBuilder;
import com.rssl.phizic.rsa.senders.initialization.FailedLoginInitializationData;

/**
 * @author tisov
 * @ created 27.05.15
 * @ $Author$
 * @ $Revision$
 * Сендер отправки сообщения во фрод-мониторинг о неудачном входе в мобильное приложение
 */
public class NotifyFailedMobileLogInSender extends FraudMonitoringNotifySenderBase<FailedLoginInitializationData>
{
	@Override
	protected NotifyRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new NotifyAPIFailedMobileLogInRequestBuilder()
				.append(getInitializationData())
				.build();
	}
}
