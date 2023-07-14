package com.rssl.phizic.business.fraudMonitoring.senders.events.builders;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.EventData;
import com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData;
import com.rssl.phizic.rsa.integration.ws.control.generated.UserStatus;
import com.rssl.phizic.rsa.integration.ws.control.generated.WSUserType;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.rsa.senders.builders.AnalyzeRequestBuilderBase;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;

/**
 * Базовый класс билдера запросов по событию (тип Analyze)
 *
 * @author khudyakov
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeEventRequestBuilderBase<ID extends InitializationData> extends AnalyzeRequestBuilderBase
{
	protected IdentificationData createIdentificationData()
	{
		AuthenticationContext context = AuthenticationContext.getContext();

		IdentificationData identificationData = new IdentificationData();
		identificationData.setUserLoginName(context.getUserAlias());
		identificationData.setOrgName(context.getTB());
		identificationData.setUserType(WSUserType.PERSISTENT);
		identificationData.setUserStatus(UserStatus.VERIFIED);

		Long profileId = FraudMonitoringRequestHelper.getUserName(context);
		if (profileId != null)
		{
			identificationData.setUserName(Long.toString(profileId));
		}

		ActivePerson person = PersonHelper.getContextPerson();
		identificationData.setClientTransactionId(FraudMonitoringRequestHelper.generateAndStoreClientTransactionId(profileId, person.getId(), person.getLogin().getId()));
		return identificationData;
	}

	@Override
	protected EventData createEventData() throws GateException, GateLogicException
	{
		EventData eventData = super.createEventData();
		eventData.setEventDescription(getEventDescription());
		return eventData;
	}

	protected abstract String getEventDescription();
}
