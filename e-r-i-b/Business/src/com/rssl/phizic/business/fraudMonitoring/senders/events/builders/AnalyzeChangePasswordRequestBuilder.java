package com.rssl.phizic.business.fraudMonitoring.senders.events.builders;

import com.rssl.phizic.business.fraudMonitoring.senders.events.data.ChangePasswordInitializationData;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.EventData;
import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * ������ ������� �� ����� ������ �������
 *
 * @author khudyakov
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeChangePasswordRequestBuilder extends AnalyzeEventRequestBuilderBase<ChangePasswordInitializationData>
{
	private ChangePasswordInitializationData initializationData;

	/**
	 * �������� ���������������� ������
	 * @param initializationData ���������������� ������
	 * @return ������
	 */
	public AnalyzeChangePasswordRequestBuilder append(ChangePasswordInitializationData initializationData)
	{
		this.initializationData = initializationData;
		return this;
	}

	@Override
	protected EventData createEventData() throws GateLogicException, GateException
	{
		EventData eventData = super.createEventData();
		eventData.setClientDefinedEventType(initializationData.getEventType().getType());
		return eventData;
	}

	@Override
	protected String getEventDescription()
	{
		return initializationData.getEventType().getDescription();
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.CHANGE_PASSWORD;
	}
}