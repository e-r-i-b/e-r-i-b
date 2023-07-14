package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.back.servises.UserLogonType;
import com.rssl.phizic.authgate.AuthConfig;

/**
 * @ author: Vagin
 * @ created: 06.02.2013
 * @ $Author
 * @ $Revision
 * Операция входа клиента при оплате с внешней ссылки.
 */
public class LoginPayOrderOperation extends LoginOperation
{
	@Override
	protected String getRedirectUrlPattern(UserLogonType userType)
	{
		AuthConfig authConfig = authService.getConfig();
		return authConfig.getProperty("csa.front.ikfl.login.payorder.url");
	}
}
