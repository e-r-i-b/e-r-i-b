package com.rssl.phizic.rsa.senders.builders.events;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.rsa.senders.builders.NotifyRequestBuilderBase;
import com.rssl.phizic.rsa.senders.initialization.FailedLoginInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * Базовый класс билдеров сообщений во Фрод-мониторинг по событию неудачного входа в приложение
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class NotifyFailedLogInRequestBuilderBase<ID extends FailedLoginInitializationData> extends NotifyRequestBuilderBase<ID>
{
	private ID initializationData;

	/**
	 * Добавить инициализирующую информацию
	 * @param initializationData инициализирующая информация
	 * @return билдер
	 */
	public NotifyFailedLogInRequestBuilderBase append(ID initializationData)
	{
		this.initializationData = initializationData;
		return this;
	}

	protected ID getInitializationData()
	{
		return initializationData;
	}

	@Override
	public NotifyRequest build() throws GateException, GateLogicException
	{
		NotifyRequest request = super.build();

		ApplicationInfo appInfo = ApplicationConfig.getIt().getApplicationInfo();
		request.setChannelIndicator(appInfo.isMobileApi() ? ChannelIndicatorType.MOBILE : ChannelIndicatorType.WEB);
		request.setClientDefinedChannelIndicator(appInfo.isMobileApi() ? FraudMonitoringRequestHelper.MOBILE_API_CLIENT_DEFINED_CHANNEL_INDICATOR_VALUE : FraudMonitoringRequestHelper.WEB_API_CLIENT_DEFINED_CHANNEL_INDICATOR_VALUE);
		return request;
	}

	@Override
	protected EventData createEventData() throws GateException, GateLogicException
	{
		EventData eventData = super.createEventData();
		eventData.setEventDescription(getEventDescription());
		return eventData;
	}

	protected String getEventDescription()
	{
		return "Неуспешный вход в систему";
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.FAILED_LOGIN_ATTEMPT;
	}

	@Override
	protected IdentificationData createIdentificationData()
	{
		Long csaProfileId = initializationData.getCsaProfileId();
		if (csaProfileId == null)
		{
			throw new IllegalArgumentException("идентификатор ЦСА профиля не может быть равен null");
		}

		IdentificationData identificationData = new IdentificationData();
		identificationData.setUserName(csaProfileId.toString());
		identificationData.setUserLoginName(initializationData.getUserAlias());
		identificationData.setOrgName(initializationData.getTb());
		identificationData.setUserType(WSUserType.PERSISTENT);
		identificationData.setUserStatus(UserStatus.VERIFIED);
		identificationData.setClientTransactionId(FraudMonitoringRequestHelper.generateClientTransactionId(csaProfileId));

		return identificationData;
	}
}
