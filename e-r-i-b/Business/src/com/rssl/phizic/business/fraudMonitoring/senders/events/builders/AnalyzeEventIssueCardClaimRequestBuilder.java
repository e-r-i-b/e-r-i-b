package com.rssl.phizic.business.fraudMonitoring.senders.events.builders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.EventData;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEventIssueCardClaimRequestBuilder extends AnalyzeEventRequestBuilderBase
{
	@Override
	protected EventData createEventData() throws GateException, GateLogicException
	{
		EventData eventData = super.createEventData();
		eventData.setClientDefinedEventType(ClientDefinedEventType.ISSUE_CARD.getType());
		return eventData;
	}

	@Override
	protected String getEventDescription()
	{
		return "Заявка на карту";
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.REQUEST_NEW_CARD;
	}
}
