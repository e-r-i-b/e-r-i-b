package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.iPasSmsPasswordConfirmStrategy;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * @author Erkin
 * @ created 16.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Подтверждение "только с помощью СМС-пароля"
 */
public class SmsConfirmStrategySource implements ConfirmStrategySource
{
	public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
	{
		if (strategy instanceof iPasSmsPasswordConfirmStrategy)
			return strategy;
		else return new iPasSmsPasswordConfirmStrategy();
	}
}
