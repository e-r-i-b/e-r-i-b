package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * Стратегии подтверждения для mAPI
 * @ author: Gololobov
 * @ created: 20.02.14
 * @ $Author$
 * @ $Revision$
 */
public class MobilePlatformIPasCompositeConfirmStrategy extends iPasCompositeConfirmStrategy
{
	public void removeStrategy(ConfirmStrategyType type)
	{
		super.removeStrategy(type);

		//Удалять sms стратегию для mAPI можно!
		if (type==ConfirmStrategyType.sms)
		{
			strategies.remove(type);
			if (defaultStrategy.equals(type))
				defaultStrategy = ConfirmStrategyType.sms;
		}
	}

	public Object clone() throws CloneNotSupportedException
	{
		MobilePlatformIPasCompositeConfirmStrategy newStrategy = new MobilePlatformIPasCompositeConfirmStrategy();
		newStrategy.setDefaultStrategy(getDefaultStrategy());
		for (ConfirmStrategy strategy: strategies.values())
		{
			newStrategy.addStrategy(strategy);
		}
		return newStrategy;
	}

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId,PreConfirmObject preConfrim) throws SecurityException
    {
        // в mAPI вообще нет возможности подтверждать операции паролем с чека.
        // операции должны корректно подтверждаться по-умолчанию смс-паролем
        // BUG077063
        if (defaultStrategy == ConfirmStrategyType.card)
            defaultStrategy = ConfirmStrategyType.sms;
        return super.createRequest(login, value, sessionId, preConfrim);
    }
}
