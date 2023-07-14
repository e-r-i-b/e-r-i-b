package com.rssl.phizic.operations.ext.sbrf.strategy;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;


/**
 * @author shapin
 * @ created 10.02.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestStrategyProvider implements ConfirmStrategyProvider
{
	private UserPrincipal principal;

	public GuestStrategyProvider(UserPrincipal principal)
	{
		this.principal = principal;
	}

	public ConfirmStrategy getStrategy()
	{
		return new iPasSmsPasswordConfirmStrategy();
	}

	public UserPrincipal getPrincipal()
	{
		return principal;
	}
}
