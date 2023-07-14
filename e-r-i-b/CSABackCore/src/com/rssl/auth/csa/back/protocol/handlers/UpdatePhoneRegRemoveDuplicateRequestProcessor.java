package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.integration.erib.ERIBService;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorState;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;

import java.util.List;

/**
 * @author osminin
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ���������� ��������� �������, ��������� ���������
 */
public class UpdatePhoneRegRemoveDuplicateRequestProcessor extends UpdatePhoneRegistrationsRequestProcessor
{
	private static final String REQUEST_TYPE  = "updatePhoneRegRemoveDuplicateRq";
	private static final String RESPONSE_TYPE = "updatePhoneRegRemoveDuplicateRs";

	private static ERIBService eribService = new ERIBService();

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected void processDuplicates(List<String> duplicatePhones) throws Exception
	{
		for (String duplicatePhone : duplicatePhones)
		{
			processDuplicate(duplicatePhone);
		}
	}

	private void processDuplicate(String duplicatePhone) throws Exception
	{
		//�������� �������� � ����������� �������
		Connector duplicate = Connector.findByPhoneNumber(duplicatePhone);
        //���� �������� ��� ������, ������ �� ������
		if (duplicate == null)
		{
			return;
		}

		Profile profile = duplicate.getProfile();

		// ���������� ������ �� �������� �������� �� ����� ����
		// ���� ������ � ���� �� ��������� (������ ��� ����-���), ������� ���������� � ��������� �� ����� ����� (��. ����)
		eribService.removeErmbPhone(profile, duplicate.getPhoneNumber());

		//������� �������� �� �� ���
		duplicate.delete();

		if (ConnectorState.ACTIVE == duplicate.getState())
		{
			//���� �������� ��� ��������, ������ �������� ����� ������ ���������
			ERMBConnector.setActiveFirstConnectorByProfile(profile.getId());
		}
	}
}
