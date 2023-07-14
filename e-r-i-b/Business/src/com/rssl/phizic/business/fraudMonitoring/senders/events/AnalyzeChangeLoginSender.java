package com.rssl.phizic.business.fraudMonitoring.senders.events;

import com.rssl.phizic.business.fraudMonitoring.senders.events.builders.AnalyzeChangeLoginRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.events.AnalyzeEventFraudMonitoringSenderBase;

/**
 * @author tisov
 * @ created 09.02.15
 * @ $Author$
 * @ $Revision$
 * Сендер по событию смены логина
 */
public class AnalyzeChangeLoginSender extends AnalyzeEventFraudMonitoringSenderBase
{
	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeChangeLoginRequestBuilder()
				.build();
	}
}
