package com.rssl.phizic.rsa.senders.builders.events;

import com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData;
import com.rssl.phizic.rsa.integration.ws.control.generated.UserStatus;
import com.rssl.phizic.rsa.integration.ws.control.generated.WSUserType;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.rsa.senders.initialization.EnrollInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * ������ ������� ��������������� �����������
 *
 * @author khudyakov
 * @ created 20.08.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeEnrollEventBuilder extends AnalyzeEventRequestBuilderBase
{
	private EnrollInitializationData initializationData;

	/**
	 * �������� ���������������� ����������
	 * @param initializationData ���������������� ����������
	 * @return ������
	 */
	public AnalyzeEnrollEventBuilder append(EnrollInitializationData initializationData)
	{
		this.initializationData = initializationData;
		return this;
	}

	protected EnrollInitializationData getInitializationData()
	{
		return initializationData;
	}

	@Override
	protected String getEventDescription()
	{
		return "����������� �������";
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.ENROLL;
	}

	@Override
	protected IdentificationData createIdentificationData()
	{
		Long csaProfileId = initializationData.getCsaProfileId();
		if (csaProfileId == null)
		{
			throw new IllegalArgumentException("������������� ��� ������� �� ����� ���� ����� null");
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
