package com.rssl.phizic.operations.ext.sbrf.strategy;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.*;

/**
 * @author basharin
 * @ created 19.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class EmployeeConfirmStrategyProvider implements ConfirmStrategyProvider
{
	private UserPrincipal principal;

	public EmployeeConfirmStrategyProvider(UserPrincipal principal)
	{
		this.principal = principal;
	}

	public ConfirmStrategy getStrategy()
	{
		Login login = (Login) principal.getLogin();
		if (UserVisitingMode.EMPLOYEE_INPUT_BY_CARD == login.getLastUserVisitingMode())
		{
			return new CardClientConfirmStrategy();
		}
		return new NotConfirmStrategy();
	}

	public UserPrincipal getPrincipal()
	{
		return principal;
	}
}
