package com.rssl.phizic.business.fraudMonitoring.senders.events.builders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.integration.ws.control.generated.EventData;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.initialization.OnLoginSenderInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * Билдер запроса по событию входа клиента
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeClientLogInRequestBuilder extends AnalyzeEventRequestBuilderBase<OnLoginSenderInitializationData>
{
	private OnLoginSenderInitializationData initializationData;

	/**
	 * Добавить инициализирующие данные
	 *
	 * @param initializationData инициализирующие данные
	 * @return билдер
	 */
	public AnalyzeClientLogInRequestBuilder append(OnLoginSenderInitializationData initializationData)
	{
		this.initializationData = initializationData;
		return this;
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.SESSION_SIGNIN;
	}

	@Override
	protected EventData createEventData() throws GateLogicException, GateException
	{
		EventData eventData = super.createEventData();
		eventData.setClientDefinedEventType(initializationData.getEventType().getType());
		return eventData;
	}

	@Override
	protected ClientDefinedFactBuilder getBuilder()
	{
		ClientDefinedFactBuilder builder = super.getBuilder();
		builder.append(new ClientDefinedFact("Номера карт клиента", initializationData.getCardNumbers(), DataType.STRING));
		builder.append(new ClientDefinedFact("Номера телефонов клиента", initializationData.getPhoneNumbers(), DataType.STRING));
		return builder;
	}

	@Override
	protected String getEventDescription()
	{
		return initializationData.getEventType().getDescription();
	}
}
