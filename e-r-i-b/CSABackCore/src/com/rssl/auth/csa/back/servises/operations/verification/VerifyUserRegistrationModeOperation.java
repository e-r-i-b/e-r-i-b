package com.rssl.auth.csa.back.servises.operations.verification;

import com.rssl.auth.csa.back.integration.erib.ERIBService;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizic.auth.modes.UserRegistrationMode;

/**
 * ��������� ���������� � �������������� ���������� ��������������� ����������� ��� ������� �� ����
 * @author basharin
 * @ created 19.11.14
 * @ $Author$
 * @ $Revision$
 */

public class VerifyUserRegistrationModeOperation extends Operation
{
	private static final ERIBService eribService = new ERIBService();

	public UserRegistrationMode getUserRegistrationMode(Profile profile)
	{
		try
		{
			return eribService.getUserRegistrationMode(profile);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ���������� � �������������� ���������� ��������������� ����������� ��� ������� �� ����.", e);
			return null;
		}
	}
}
