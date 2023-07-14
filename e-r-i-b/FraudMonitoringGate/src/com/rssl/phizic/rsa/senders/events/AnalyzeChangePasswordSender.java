package com.rssl.phizic.rsa.senders.events;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.builders.events.AnalyzeChangePasswordRequestBuilder;
import com.rssl.phizic.rsa.senders.initialization.ChangePasswordInitializationData;

/**
 * @author tisov
 * @ created 09.02.15
 * @ $Author$
 * @ $Revision$
 * сендер по событию смены пароля
 */
public class AnalyzeChangePasswordSender extends AnalyzeEventFraudMonitoringSenderBase<ChangePasswordInitializationData>
{
	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeChangePasswordRequestBuilder()
				.append(getInitializationData())
				.build();
	}
}
