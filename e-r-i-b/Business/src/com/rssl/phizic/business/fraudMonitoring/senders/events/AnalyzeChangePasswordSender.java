package com.rssl.phizic.business.fraudMonitoring.senders.events;

import com.rssl.phizic.business.fraudMonitoring.senders.events.builders.AnalyzeChangePasswordRequestBuilder;
import com.rssl.phizic.business.fraudMonitoring.senders.events.data.ChangePasswordInitializationData;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.events.AnalyzeEventFraudMonitoringSenderBase;

/**
 * Сендер запроса на смену пароля
 *
 * @author khudyakov
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
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
