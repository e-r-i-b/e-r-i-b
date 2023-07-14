package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.CompositeConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * Соурс стратегий для смс шаблонов
 *
 * @author khudyakov
 * @ created 10.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateSmsConfirmStrategySource extends CompositeCardlessConfirmStrategySource
{
	public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
	{
		if (strategy instanceof CompositeConfirmStrategy)
		{
			return super.getStrategy(object,strategy,userChoice);
		}

		return new SmsPasswordConfirmStrategy();
	}
}
