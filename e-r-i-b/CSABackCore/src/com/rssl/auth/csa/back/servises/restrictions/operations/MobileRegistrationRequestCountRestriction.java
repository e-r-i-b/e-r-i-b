package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.servises.operations.MobileRegistrationOperation;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 * Ограничение на количество запросов регистрации мобильных приложений
 */
public class MobileRegistrationRequestCountRestriction extends RequestCountRestrictionBase<MobileRegistrationOperation>
{
	private static final Restriction INSTANCE = new MobileRegistrationRequestCountRestriction();

	public static Restriction getInstance()
	{
		return INSTANCE;
	}

	protected Class<MobileRegistrationOperation> getOperationClass()
	{
		return MobileRegistrationOperation.class;
	}

	protected int getRequestCheckInterval()
	{
		return ConfigFactory.getConfig(Config.class).getMobileRegistrationRequestCheckInterval();
	}

	protected int getMaxRequestCount()
	{
		return ConfigFactory.getConfig(Config.class).getMaxMobileRegistrationRequestCount();
	}
}
