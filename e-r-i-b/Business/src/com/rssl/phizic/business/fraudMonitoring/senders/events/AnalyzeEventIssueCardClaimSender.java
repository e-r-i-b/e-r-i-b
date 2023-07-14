package com.rssl.phizic.business.fraudMonitoring.senders.events;

import com.rssl.phizic.business.fraudMonitoring.senders.events.builders.AnalyzeEventIssueCardClaimRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.events.AnalyzeEventFraudMonitoringSenderBase;

/**
 * @author khudyakov
 * @ created 03.03.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEventIssueCardClaimSender extends AnalyzeEventFraudMonitoringSenderBase
{
	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeEventIssueCardClaimRequestBuilder()
				.build();
	}
}
