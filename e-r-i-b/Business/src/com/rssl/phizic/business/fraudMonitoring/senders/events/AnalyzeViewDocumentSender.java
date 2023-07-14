package com.rssl.phizic.business.fraudMonitoring.senders.events;

import com.rssl.phizic.business.fraudMonitoring.senders.events.builders.AnalyzeAPIViewDocumentRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.events.AnalyzeEventFraudMonitoringSenderBase;

/**
 * @author tisov
 * @ created 11.02.15
 * @ $Author$
 * @ $Revision$
 * —ендер по событию просмотра документа
 */
public class AnalyzeViewDocumentSender extends AnalyzeEventFraudMonitoringSenderBase
{
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeAPIViewDocumentRequestBuilder()
				.build();
	}

}
