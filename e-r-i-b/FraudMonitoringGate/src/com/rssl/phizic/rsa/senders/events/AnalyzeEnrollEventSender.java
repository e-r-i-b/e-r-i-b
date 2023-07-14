package com.rssl.phizic.rsa.senders.events;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.builders.events.AnalyzeEnrollEventBuilder;
import com.rssl.phizic.rsa.senders.initialization.EnrollInitializationData;

/**
 * —ендер событи€ самосто€тельной регистрации
 *
 * @author khudyakov
 * @ created 20.08.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEnrollEventSender extends AnalyzeEventFraudMonitoringSenderBase<EnrollInitializationData>
{
	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeEnrollEventBuilder()
				.append(getInitializationData())
				.build();
	}
}
