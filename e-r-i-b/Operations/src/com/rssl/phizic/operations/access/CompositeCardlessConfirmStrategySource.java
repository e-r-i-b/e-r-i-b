package com.rssl.phizic.operations.access;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.auth.modes.CompositeConfirmStrategy;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Dorzhinov
 * @ created 15.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class CompositeCardlessConfirmStrategySource implements ConfirmStrategySource
{
    public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
    {
        if (strategy instanceof CompositeConfirmStrategy)
		{
			if (StringHelper.isNotEmpty(userChoice))
				((CompositeConfirmStrategy)strategy).setDefaultStrategy(ConfirmStrategyType.valueOf(userChoice));
            ((CompositeConfirmStrategy)strategy).removeStrategy(ConfirmStrategyType.card);
		}
        return strategy;
    }
}