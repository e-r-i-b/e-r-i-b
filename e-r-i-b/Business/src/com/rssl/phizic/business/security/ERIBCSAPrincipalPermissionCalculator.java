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
 * PrincipalPermissionCalculator при входе в основное приложени
 *
 * @author khudyakov
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class ERIBCSAPrincipalPermissionCalculator extends PrincipalPermissionCalculator
{
	private static final Set<String> deniedOperationsForERIBCSATerminalClient = new HashSet<String>();      //операции запрещенные для клиента, вошедшего через ЕРИБ ЦСА (логин/алиас iPas)
	private static final Set<String> deniedOperationsForERIBCSACSAClient = new HashSet<String>();           //операции запрещенные для клиента, вошедшего через ЕРИБ ЦСА (логин ЕРИБ ЦСА)
	private static final Set<String> deniedOperationsForIPasClient = new HashSet<String>();                 //операции запрещенные для клиента, вошедшего через СБОЛ ЦА
	private static final Set<String> deniedOperationsForDisposableClient = new HashSet<String>();                //операции запрещенные для клиента, вошедшего временному логину

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
			//если клиент зашел через СБОЛ ЦА
			return Collections.unmodifiableSet(deniedOperationsForIPasClient);
		}

		if (CSAType.ERIB_CSA == authenticationContext.getCsaType())
		{
			//если клиент зашел через ЕРИБ ЦСА, то
			//1. если зашел через логин/алиас iPas
			if (LoginType.TERMINAL == authenticationContext.getLoginType())
			{
				return Collections.unmodifiableSet(deniedOperationsForERIBCSATerminalClient);
			}
			//2. если зашел через логин ЕРИБ ЦСА
			if (LoginType.CSA == authenticationContext.getLoginType())
			{
				return Collections.unmodifiableSet(deniedOperationsForERIBCSACSAClient);
			}
			//3. если зашел по временному логину
			if (LoginType.DISPOSABLE == authenticationContext.getLoginType())
			{
				return Collections.unmodifiableSet(deniedOperationsForDisposableClient);
			}
		}
		return Collections.emptySet();
	}
}
