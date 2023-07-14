package com.rssl.phizic.web.client.security;

import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.Constants;
import com.rssl.phizic.operations.security.GetAuthDataClientOperation;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.client.ext.sbrf.security.PostCSALoginAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;

/**
 * логинимся через новую ЦСА.
 *
 * @author bogdanov
 * @ created 10.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginFromCSAFrontAction extends PostCSALoginAction
{
	/**
	 * @param authToken токен авторизации.
	 * @return параметры авторизации.
	 */
	protected AuthData getAuthData(String authToken, String browserInfo) throws BusinessLogicException, BusinessException
	{
		GetAuthDataClientOperation operation = new GetAuthDataClientOperation(authToken, browserInfo);
		storeRSAData(operation);
		AuthData authData = operation.getAuthData();
		writeFindProfileEvent(authData, authData.getBrowserInfo());
		return authData;
	}

	/**
	 * Возврат на ЦСА, для того чтобы клиент залогинился.
	 *
	 * @param request реквест.
	 * @return форвард.
	 */
	protected ActionForward csaForward(HttpServletRequest request)
	{
		AuthConfig config = AuthGateSingleton.getAuthService().getConfig();
		return new ActionRedirect(config.getProperty("csaFront.login.url"));
	}


	private void storeRSAData(GetAuthDataClientOperation operation)
	{
		WebContext.getCurrentRequest().getSession().setAttribute(Constants.CONTEXT_DATA_NAME, operation.getRsaData());
	}
}
