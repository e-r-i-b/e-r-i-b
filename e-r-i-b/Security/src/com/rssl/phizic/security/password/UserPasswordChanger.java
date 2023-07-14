package com.rssl.phizic.security.password;

import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author Kidyaev
 * @ created 23.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class UserPasswordChanger extends PasswordChangerBase
{
	public UserPasswordChanger()
	{
		calculateExpireDate();
	}

	public UserPasswordChanger(Boolean needChangePassword)
	{
		setNeedChangePassword(needChangePassword);
		calculateExpireDate();
	}

	public void changePassword(final CommonLogin login, final char[] newPassword, String instanceName) throws SecurityDbException
	{
		final char[] passwordHash = new ManualPasswordGenerator(newPassword).newPassword(0, null);
		saveNewPassword(login,passwordHash,instanceName);
	}
}
