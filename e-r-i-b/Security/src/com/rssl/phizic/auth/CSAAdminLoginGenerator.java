package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.password.HashPasswordGenerator;

import java.util.Calendar;
import java.util.Properties;

/**
 * @author mihaylov
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Генератор логина в блоке ЕРИБ на основе данных из ЦСА Админ
 */
public class CSAAdminLoginGenerator extends LoginGeneratorBase<BankLogin>
{
	private static final SecurityService securityService = new SecurityService();

	private Calendar lastSynchronizationDate;

	/**
	 * конструктор
	 * @param userId логин
	 * @param password пароль
	 * @param lastSynchronizationDate - дата последней синхронизации в ЦСА Админ
	 */
	public CSAAdminLoginGenerator(String userId, String password, Calendar lastSynchronizationDate)
	{
		super(new ManualUserIdValueGenerator(userId), new HashPasswordGenerator(password), null);
		setNeedChangePasword(false); // смена паролей определяется на уровне ЦСА Админ
		setCsaUserId(userId);
		this.lastSynchronizationDate = lastSynchronizationDate;
	}

	@Override
	protected void enableDefaultAccess(CommonLogin login) throws SecurityDbException, SecurityLogicException
	{
		accessModeService.enableAccess(login, AccessType.employee, new Properties(), null);
	}

	@Override
	protected AuthenticationConfig getAuthenticationConfig()
	{
		return ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIA);
	}

	@Override protected void checkDublicates(String userId) throws SecurityDbException, DublicateUserIdException
	{
		if (securityService.getBankLogin(userId) != null)
				throw new DublicateUserIdException(userId, "");
	}

	@Override
	protected LoginBase newInstance()
	{
		BankLoginImpl bankLogin = new BankLoginImpl();
		bankLogin.setLastSynchronizationDate(lastSynchronizationDate);
		return bankLogin;
	}
}
