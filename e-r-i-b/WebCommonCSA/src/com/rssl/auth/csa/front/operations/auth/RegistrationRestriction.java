package com.rssl.auth.csa.front.operations.auth;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.auth.payOrder.PayOrderHelper;

/**
 * Ограничение на самостоятельную регистрацию, задаются настройками приложения
 * @author niculichev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationRestriction implements Restriction
{
	public boolean check()
	{
		// Для оплаты УЭК регистрация не доступна
		if (PayOrderHelper.isUECPaymentSession())
			return false;

		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.isAccessInternalRegistration();
	}
}
