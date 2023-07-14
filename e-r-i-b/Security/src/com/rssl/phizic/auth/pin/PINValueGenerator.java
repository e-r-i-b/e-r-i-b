package com.rssl.phizic.auth.pin;

import com.rssl.phizic.auth.UserIdValueGenerator;
import com.rssl.phizic.security.crypto.CryptoProvider;
import com.rssl.phizic.security.crypto.CryptoProviderHelper;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.RandomHelper;

/**
 * √енератор случайной последовательности
 * @author Roshka
 * @ created 17.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class PINValueGenerator implements UserIdValueGenerator
{
	public String newUserId(int length)
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		return RandomHelper.rand( length, securityConfig.getLoginAllowedChars() );
	}
}