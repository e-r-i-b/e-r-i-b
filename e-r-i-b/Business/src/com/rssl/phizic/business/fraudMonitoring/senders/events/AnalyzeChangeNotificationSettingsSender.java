package com.rssl.phizic.business.fraudMonitoring.senders.events;

import com.rssl.phizic.business.fraudMonitoring.senders.events.builders.AnalyzeChangeNotificationSettingsRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.senders.events.AnalyzeEventFraudMonitoringSenderBase;

/**
 * @author tisov
 * @ created 10.02.15
 * @ $Author$
 * @ $Revision$
 * —ендер по событию изменени€ настроек оповещени€
 */

public class AnalyzeChangeNotificationSettingsSender extends AnalyzeEventFraudMonitoringSenderBase
{
	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeChangeNotificationSettingsRequestBuilder()
				.build();
	}
}
