package com.rssl.phizic.rsa.senders.builders.events;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.EventData;
import com.rssl.phizic.rsa.senders.builders.AnalyzeRequestBuilderBase;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;

/**
 * Базовый класс билдера запросов по событию (тип Analyze)
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeEventRequestBuilderBase<ID extends InitializationData> extends AnalyzeRequestBuilderBase
{
	@Override
	protected EventData createEventData() throws GateException, GateLogicException
	{
		EventData eventData = super.createEventData();
		eventData.setEventDescription(getEventDescription());
		return eventData;
	}

	protected abstract String getEventDescription();
}
