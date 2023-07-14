package com.rssl.phizic.rsa.senders.builders.events;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.EventData;
import com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData;
import com.rssl.phizic.rsa.integration.ws.control.generated.UserStatus;
import com.rssl.phizic.rsa.integration.ws.control.generated.WSUserType;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.rsa.senders.initialization.ChangePasswordInitializationData;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * Билдер запросов по событию смены пароля
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeChangePasswordRequestBuilder extends AnalyzeEventRequestBuilderBase<ChangePasswordInitializationData>
{
	private ChangePasswordInitializationData initializationData;

	/**
	 * Добавить инициализирующие данные
	 * @param initializationData инициализирующие данные
	 * @return билдер
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

	protected IdentificationData createIdentificationData()
	{
		Long csaProfileId = initializationData.getCsaProfileId();
		if (csaProfileId == null)
		{
			throw new IllegalArgumentException("идентификатор ЦСА профиля не может быть равен null");
		}

		IdentificationData identificationData = new IdentificationData();
		identificationData.setUserLoginName(initializationData.getUserAlias());
		identificationData.setOrgName(initializationData.getTb());
		identificationData.setUserType(WSUserType.PERSISTENT);
		identificationData.setUserStatus(UserStatus.VERIFIED);
		identificationData.setUserName(csaProfileId.toString());
		identificationData.setClientTransactionId(FraudMonitoringRequestHelper.generateClientTransactionId(csaProfileId));
		return identificationData;
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
