package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.client.CSAType;
import com.rssl.phizic.common.types.client.LoginType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * PrincipalPermissionCalculator ��� ����� � �������� ���������
 *
 * @author khudyakov
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class ERIBCSAPrincipalPermissionCalculator extends PrincipalPermissionCalculator
{
	private static final Set<String> deniedOperationsForERIBCSATerminalClient = new HashSet<String>();      //�������� ����������� ��� �������, ��������� ����� ���� ��� (�����/����� iPas)
	private static final Set<String> deniedOperationsForERIBCSACSAClient = new HashSet<String>();           //�������� ����������� ��� �������, ��������� ����� ���� ��� (����� ���� ���)
	private static final Set<String> deniedOperationsForIPasClient = new HashSet<String>();                 //�������� ����������� ��� �������, ��������� ����� ���� ��
	private static final Set<String> deniedOperationsForDisposableClient = new HashSet<String>();                //�������� ����������� ��� �������, ��������� ���������� ������

	static
	{
		deniedOperationsForERIBCSATerminalClient.add("ChangeClientPasswordOperation");
		deniedOperationsForERIBCSATerminalClient.add("GeneratePasswordOperation");

		deniedOperationsForERIBCSACSAClient.add("GenerateIPasPasswordOperation");
		deniedOperationsForERIBCSACSAClient.add("GeneratePasswordOperation");

		deniedOperationsForIPasClient.add("GenerateIPasPasswordOperation");
		deniedOperationsForIPasClient.add("ChangeClientPasswordOperation");
		deniedOperationsForIPasClient.add("ChangeClientLoginOperation");

		deniedOperationsForDisposableClient.add("GeneratePasswordOperation");
		deniedOperationsForDisposableClient.add("GenerateIPasPasswordOperation");
	}

	public ERIBCSAPrincipalPermissionCalculator(UserPrincipal principal) throws BusinessException
	{
		super(principal);
	}

	Set<String> getBlockedOperations()
	{
		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		if (CSAType.CBOL_CA == authenticationContext.getCsaType())
		{
			//���� ������ ����� ����� ���� ��
			return Collections.unmodifiableSet(deniedOperationsForIPasClient);
		}

		if (CSAType.ERIB_CSA == authenticationContext.getCsaType())
		{
			//���� ������ ����� ����� ���� ���, ��
			//1. ���� ����� ����� �����/����� iPas
			if (LoginType.TERMINAL == authenticationContext.getLoginType())
			{
				return Collections.unmodifiableSet(deniedOperationsForERIBCSATerminalClient);
			}
			//2. ���� ����� ����� ����� ���� ���
			if (LoginType.CSA == authenticationContext.getLoginType())
			{
				return Collections.unmodifiableSet(deniedOperationsForERIBCSACSAClient);
			}
			//3. ���� ����� �� ���������� ������
			if (LoginType.DISPOSABLE == authenticationContext.getLoginType())
			{
				return Collections.unmodifiableSet(deniedOperationsForDisposableClient);
			}
		}
		return Collections.emptySet();
	}
}
