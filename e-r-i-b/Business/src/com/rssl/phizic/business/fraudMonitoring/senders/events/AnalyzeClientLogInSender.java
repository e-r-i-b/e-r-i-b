package com.rssl.phizic.business.fraudMonitoring.senders.events;

import com.rssl.phizic.business.fraudMonitoring.senders.events.builders.AnalyzeClientLogInRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.events.AnalyzeEventFraudMonitoringSenderBase;
import com.rssl.phizic.rsa.senders.initialization.OnLoginSenderInitializationData;

/**
 * @author tisov
 * @ created 10.02.15
 * @ $Author$
 * @ $Revision$
 * Сендер по события входа клиента в приложение
 */
public class AnalyzeClientLogInSender extends AnalyzeEventFraudMonitoringSenderBase<OnLoginSenderInitializationData>
{
	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeClientLogInRequestBuilder()
				.append(getInitializationData())
				.build();
	}
}
