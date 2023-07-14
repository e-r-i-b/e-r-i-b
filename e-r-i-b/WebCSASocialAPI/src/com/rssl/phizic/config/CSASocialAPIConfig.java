package com.rssl.phizic.config;

import com.rssl.phizic.auth.modes.RegistrationMode;
import com.rssl.phizic.common.types.Application;

/**
 * @author osminin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 *
 * настройки для ЦСА МАПИ
 */
public class CSASocialAPIConfig extends Config
{
	private static final String MAPI_PIN_LENGTH     = "mobile.api.pin.length";
	private static final String MAPI_PIN_REGEXP     = "mobile.api.pin.regexp";
	private static final String LOGIN_ATTEMPTS      = "com.rssl.iccs.login.attempts";
	private static final String PASSWORD_LIFETIME   = "com.rssl.iccs.password.lifetime";
	private static final String REGISTRATION_MODE   = "com.rssl.iccs.registration.mode";

	private RegistrationMode registrationMode;

	private String mApiPINRegexp;

	private int mApiPINLength;
	private int loginAttempts;
	private int passwordLifetime;

	public CSASocialAPIConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	public void doRefresh() throws ConfigurationException
	{
		mApiPINLength    = getIntProperty(MAPI_PIN_LENGTH);
		mApiPINRegexp    = getProperty(MAPI_PIN_REGEXP);
		loginAttempts    = getIntProperty(LOGIN_ATTEMPTS);
		passwordLifetime = getIntProperty(PASSWORD_LIFETIME);
		registrationMode = RegistrationMode.valueOf(getProperty(REGISTRATION_MODE));
	}

	public int getMobilePINLength()
	{
		return mApiPINLength;
	}

	public int getLoginAttempts()
	{
		return loginAttempts;
	}

	public int getPasswordLifetime()
	{
		return passwordLifetime;
	}

	public String getMobilePINRegExp()
	{
		return mApiPINRegexp;
	}

	public RegistrationMode getRegistrationMode()
	{
		return registrationMode;
	}
}
