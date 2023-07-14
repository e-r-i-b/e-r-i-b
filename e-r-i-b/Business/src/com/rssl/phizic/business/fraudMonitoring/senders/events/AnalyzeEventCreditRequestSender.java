package com.rssl.phizic.business.fraudMonitoring.senders.events;

import com.rssl.phizic.business.etsm.offer.OfferToFraudInitializationData;
import com.rssl.phizic.business.fraudMonitoring.senders.events.builders.AnalyzeEventCreditRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.events.AnalyzeEventFraudMonitoringSenderBase;

/**
 * @author Moshenko
 * @ created 28.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEventCreditRequestSender extends AnalyzeEventFraudMonitoringSenderBase<OfferToFraudInitializationData>
{
	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeEventCreditRequestBuilder()
				.append(getInitializationData())
				.build();
	}
}
