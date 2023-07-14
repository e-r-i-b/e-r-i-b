package com.rssl.phizic.rsa.senders.builders.events;

import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.rsa.senders.builders.NotifyRequestBuilderBase;
import com.rssl.phizic.rsa.senders.initialization.MobileRegistrationInitializationData;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.StringHelper;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.MOBILE_APP_INFO_FIELD_NAME;

/**
 * Ѕилдер запросов о регистрации мобильного приложени€.
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class NotifyFraudMonitoringNewMobileRegistrationRequestBuilder extends NotifyRequestBuilderBase<MobileRegistrationInitializationData>
{
	private MobileRegistrationInitializationData initializationData;

	/**
	 * ƒобавить инициализирующие данные
	 * @param initializationData инициализирующие данные
	 * @return билдер
	 */
	public NotifyFraudMonitoringNewMobileRegistrationRequestBuilder append(MobileRegistrationInitializationData initializationData)
	{
		this.initializationData = initializationData;
		return this;
	}

	public NotifyRequest build() throws GateException, GateLogicException
	{
		NotifyRequest request = super.build();
		request.setChannelIndicator(ChannelIndicatorType.MOBILE);
		request.setClientDefinedChannelIndicator(FraudMonitoringRequestHelper.MOBILE_API_CLIENT_DEFINED_CHANNEL_INDICATOR_VALUE);
		return  request;
	}

	protected DeviceRequest getDeviceRequest()
	{
		DeviceRequest deviceRequest = FraudMonitoringRequestHelper.createDeviceRequest();
		MobileDevice mobileDevice = new MobileDevice();
		mobileDevice.setMobileSdkData(RSAContext.getMobileSdkData());
		deviceRequest.setDeviceIdentifier(new DeviceIdentifier[]{mobileDevice});
		return  deviceRequest;
	}

	protected EventData createEventData() throws GateLogicException, GateException
	{
		EventData eventData = super.createEventData();
		eventData.setEventType(EventType.fromValue(getEventsType().getDescription()));
		eventData.setEventDescription("–егистраци€ нового мобильного приложени€");
		eventData.setClientDefinedEventType(ClientDefinedEventType.NEW_MOB_APPL.getType());
		return eventData;
	}

	protected ClientDefinedFactBuilder getBuilder()
	{
		ClientDefinedFactBuilder builder = super.getBuilder();
		builder.append(MOBILE_APP_INFO_FIELD_NAME, initializationData.getmGUID(), DataType.STRING);
		return builder;
	}

	@Override
	protected IdentificationData createIdentificationData() throws GateException
	{
		String csaProfileId = initializationData.getCsaProfileId();
		if (StringHelper.isEmpty(csaProfileId))
		{
			throw new GateException("CsaProfileId не может быть null");
		}

		IdentificationData identificationData = new IdentificationData();
		identificationData.setUserName(initializationData.getCsaProfileId());
		identificationData.setOrgName(LogThreadContext.getDepartmentRegion());
		identificationData.setUserType(WSUserType.PERSISTENT);
		identificationData.setUserStatus(UserStatus.VERIFIED);
		identificationData.setClientTransactionId(FraudMonitoringRequestHelper.generateClientTransactionId(Long.parseLong(csaProfileId)));
		return identificationData;
	}

	protected EventsType getEventsType()
	{
		return EventsType.UPDATE_USER;
	}
}
