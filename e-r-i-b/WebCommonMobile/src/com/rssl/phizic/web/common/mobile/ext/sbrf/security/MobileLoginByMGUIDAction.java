package com.rssl.phizic.web.common.mobile.ext.sbrf.security;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.web.security.LoginForm;
import com.rssl.phizic.operations.security.GetAuthDataOperation;
import com.rssl.phizic.operations.security.GetAuthDataSimpleMobileClientFromERIBCSAOperation;

import java.util.Map;

/**
 * Аутентификация по mGUID
 * @author Pankin
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class MobileLoginByMGUIDAction extends MobileLoginNamePasswordAction
{
	@Override
	protected Form getLoginForm()
	{
		return LoginForm.MOBILE_LOGIN_BY_MGUID_FORM;
	}

	@Override
	protected GetAuthDataOperation createGetAuthDataOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException
	{
		return new GetAuthDataSimpleMobileClientFromERIBCSAOperation(data);
	}

	@Override
	protected void updateAuthenticationContext(AuthenticationContext context, Map<String, Object> data, AuthData authData) throws BusinessException
	{
		super.updateAuthenticationContext(context, data, authData);
		context.setMobileAppScheme(authData.getMobileAppScheme());
	}
}