package com.rssl.phizic.rsa.senders.events;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest;
import com.rssl.phizic.rsa.senders.FraudMonitoringNotifySenderBase;
import com.rssl.phizic.rsa.senders.builders.events.NotifyFailedLogInRequestBuilder;
import com.rssl.phizic.rsa.senders.initialization.FailedLoginInitializationData;

/**
 * @author tisov
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 * Сендер сообщений во Фрод-мониторинг по событию неудачного входа в приложение
 */
public class NotifyFailedLogInSender extends FraudMonitoringNotifySenderBase<FailedLoginInitializationData>
{
	@Override
	protected NotifyRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new NotifyFailedLogInRequestBuilder()
				.append(getInitializationData())
				.build();
	}
}
