package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.NotConfirmStrategy;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * Предназначен для замены стратегий подтверждения у операций на none-стратегию (без подтверждения)
 * @ author: filimonova
 * @ created: 19.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class NullConfirmStrategySource implements ConfirmStrategySource
{
	public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
	{
		return new NotConfirmStrategy();
	}
}
