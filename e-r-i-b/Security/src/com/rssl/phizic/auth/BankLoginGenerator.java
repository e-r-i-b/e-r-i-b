package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.password.ManualPasswordGenerator;

import java.util.Properties;

/**
 * @author Kosyakov
 * @ created 12.01.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
public class BankLoginGenerator extends LoginGeneratorBase<BankLogin>
{
	private static final SecurityService securityService = new SecurityService();

	public BankLoginGenerator(String userId, String password)
	{
		super(new ManualUserIdValueGenerator(userId), new ManualPasswordGenerator(password), null);
		setNeedChangePasword(true); // после создания надо поменять пароль
	}

	protected LoginBase newInstance()
	{
		return new BankLoginImpl();
	}

	protected void checkDublicates(String userId) throws SecurityDbException, DublicateUserIdException
	{
		if (securityService.getBankLogin(userId) != null)
			throw new DublicateUserIdException(userId, "");
	}

	protected AuthenticationConfig getAuthenticationConfig()
	{
		return ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIA);
	}

	protected void enableDefaultAccess(CommonLogin login) throws SecurityDbException, SecurityLogicException
	{
		accessModeService.enableAccess(login, AccessType.employee, new Properties(), null);
	}
}
