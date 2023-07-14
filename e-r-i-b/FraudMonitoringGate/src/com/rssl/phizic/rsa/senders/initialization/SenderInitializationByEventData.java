package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * @author tisov
 * @ created 26.02.15
 * @ $Author$
 * @ $Revision$
 * Структура данных для инициализации сендера, с кастомизируемым clientDefinedEventType
 */
public class SenderInitializationByEventData extends PhaseInitializationData
{
	private ClientDefinedEventType eventType;       //тип клиентского события

	public SenderInitializationByEventData(InteractionType interactionType, PhaseType phaseType, ClientDefinedEventType eventType)
	{
		super(interactionType, phaseType);

		this.eventType = eventType;
	}

	public ClientDefinedEventType getEventType()
	{
		return eventType;
	}

	public void setEventType(ClientDefinedEventType eventType)
	{
		this.eventType = eventType;
	}
}
