package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.CompositeConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * —тратеги€ подтверждени€ без карт и push сообщений
 * @author basharin
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */

public class CompositeCardPushLessConfirmStrategySource implements ConfirmStrategySource
{
    public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
    {
        if (strategy instanceof CompositeConfirmStrategy)
        {
            ((CompositeConfirmStrategy)strategy).removeStrategy(ConfirmStrategyType.card);
            ((CompositeConfirmStrategy)strategy).removeStrategy(ConfirmStrategyType.push);
        }
        return strategy;
    }
}