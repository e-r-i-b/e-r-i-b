package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.iPasSmsPasswordConfirmStrategy;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * Стратегия подтверждения при самостоятельной регистрации.
 *
 * @author bogdanov
 * @ created 14.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class RegistrationConfirmStrategySource implements ConfirmStrategySource
{
	public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
	{
		if (strategy.getType() == ConfirmStrategyType.card || strategy.getType() == ConfirmStrategyType.sms
				|| strategy.getType() == ConfirmStrategyType.conditionComposite)
			return strategy;

		return new iPasSmsPasswordConfirmStrategy();
	}
}
