package com.rssl.phizic.operations.loans.claims;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Krenev
 * @ created 16.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimOperation extends OperationBase
{
	private CommonLogin login;

	public void initialize()
	{
		login = AuthModule.getAuthModule().getPrincipal().getLogin();
	}

	public CommonLogin getLogin()
	{
		return login;
	}
}
