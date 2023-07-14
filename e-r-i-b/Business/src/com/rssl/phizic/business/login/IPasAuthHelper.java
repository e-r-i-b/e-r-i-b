package com.rssl.phizic.business.login;

import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.auth.modes.AuthenticationContext;

/**
 * @author khudyakov
 * @ created 29.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class IPasAuthHelper
{
	/**
	 * Возвращает контейнер для перемещения сессии в СБОЛ ЦА
	 * @param authenticationContext контекст
	 * @param authConfig конфиг
	 * @return контейнер
	 */
	public static AuthParamsContainer createMoveSessionContainer(AuthenticationContext authenticationContext, AuthConfig authConfig)
	{
		AuthParamsContainer container = new AuthParamsContainer();
		container.addParameter("SID", authenticationContext.getCSA_SID());
		container.addParameter("AuthTokenRq", "4");
		container.addParameter("NextService", "WB");
		container.addParameter("ASPKey", authenticationContext.getRandomString());
		container.addParameter("BackRef", authConfig.getProperty("ikfl.login.back.url"));
		container.addParameter("Page", "CARDS");
		container.addParameter("ISEDBO", "0");
		return container;
	}

	public static AuthParamsContainer createVerifyPasswordContainer(String login, String password)
	{
		AuthParamsContainer container = new AuthParamsContainer();
		container.addParameter("UserId",    login);
		container.addParameter("password",  password);
		return container;
	}

	public static AuthParamsContainer createCheckSessionContainer(String authToken)
	{
		AuthParamsContainer container = new AuthParamsContainer();
		container.addParameter("AuthToken", authToken);
		container.addParameter("Service", "ESK");
		return container;
	}


}
