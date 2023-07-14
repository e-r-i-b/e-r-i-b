package com.rssl.phizic.auth;

import com.rssl.phizic.security.password.UserPassword;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @ author: filimonova
 * @ created: 28.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class ChangePasswordByDaysProvider extends LoginInfoProviderImpl
{
	public boolean needChangePassword (CommonLogin login ) throws SecurityDbException
	{
		UserPassword userPassword=getUserPassword(login);
		Calendar expire = userPassword.getExpireDate();
        return userPassword.getNeedChange() || DateHelper.getCurrentDate().after(expire);
	}
}
