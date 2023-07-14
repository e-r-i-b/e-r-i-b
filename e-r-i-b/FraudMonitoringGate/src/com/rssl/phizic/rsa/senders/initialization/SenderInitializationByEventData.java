package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * @author tisov
 * @ created 26.02.15
 * @ $Author$
 * @ $Revision$
 * ��������� ������ ��� ������������� �������, � ��������������� clientDefinedEventType
 */
public class SenderInitializationByEventData extends PhaseInitializationData
{
	private ClientDefinedEventType eventType;       //��� ����������� �������

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
