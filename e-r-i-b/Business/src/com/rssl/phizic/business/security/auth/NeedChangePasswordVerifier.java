package com.rssl.phizic.business.security.auth;

import com.rssl.phizic.auth.LoginInfoProvider;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.StageVerifier;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.SecurityFactory;

/**
 * Проверка необходимости смены пароля
 * @author Evgrafov
 * @ created 14.12.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 4036 $
 */
public class NeedChangePasswordVerifier implements StageVerifier
{
	public boolean isRequired(AuthenticationContext context, Stage stage)
	{
		try
		{
			LoginInfoProvider loginInfoProvider = SecurityFactory.createLoginInfoProvider();
			return loginInfoProvider.needChangePassword(context.getLogin());
		}
		catch (SecurityDbException e)
		{
			throw new SecurityException(e);
		}
	}
}