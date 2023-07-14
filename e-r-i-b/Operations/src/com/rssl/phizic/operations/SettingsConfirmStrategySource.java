package com.rssl.phizic.operations;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.iPasSmsPasswordConfirmStrategy;
import com.rssl.phizic.auth.modes.CompositeConfirmStrategy;
import com.rssl.phizic.operations.access.CompositeCardPushLessConfirmStrategySource;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * Стратегия для подтверждения изменения профиля клиента
 * @ author: filimonova
 * @ created: 22.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class SettingsConfirmStrategySource extends CompositeCardPushLessConfirmStrategySource
{
	public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
	{
         if (strategy instanceof CompositeConfirmStrategy)
            return super.getStrategy(object,strategy,userChoice);
		return new iPasSmsPasswordConfirmStrategy();
	}
}
