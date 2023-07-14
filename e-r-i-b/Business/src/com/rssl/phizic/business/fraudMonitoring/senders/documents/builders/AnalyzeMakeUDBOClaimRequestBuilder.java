package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.ClientDefinedFactConstants;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.rsa.senders.builders.AnalyzeRequestBuilderBase;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * @author khudyakov
 * @ created 10.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeMakeUDBOClaimRequestBuilder extends AnalyzeRequestBuilderBase
{
	@Override
	protected EventsType getEventsType()
	{
		return EventsType.CLIENT_DEFINED;
	}

	@Override
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
		eventData.setClientDefinedEventType(ClientDefinedEventType.UNIVERSAL_AGREEMENT.getType());
		eventData.setEventDescription(ClientDefinedEventType.UNIVERSAL_AGREEMENT.getDescription());
		eventData.setClientDefinedAttributeList(new ClientDefinedFactBuilder().append(ClientDefinedFactConstants.OFFLINE_LOAD_FIELD_NAME, ClientDefinedFactConstants.NO, DataType.STRING).build());
		return eventData;
	}
}
