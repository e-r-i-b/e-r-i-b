package com.rssl.phizic.web.atm.ext.sbrf.security;

import com.rssl.common.forms.Form;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.business.login.exceptions.CanNotLoginException;
import com.rssl.phizic.business.login.exceptions.LoginOrPasswordWrongLoginExeption;
import com.rssl.phizic.operations.security.GetAuthDataFromMobileBankOperation;
import com.rssl.phizic.operations.security.GetAuthDataOperation;
import com.rssl.phizic.web.common.client.ext.sbrf.security.LoginNamePasswordAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 24.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class LoginNamePasswordATMAction extends LoginNamePasswordAction
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map <String, String> keyMap= new HashMap<String,String>();
	    keyMap.put("login", "login");
		return keyMap;
	}

	@Override
	protected Form getLoginForm()
	{
		return LoginATMForm.ATM_LOGIN_FORM;
	}

	@Override
	protected AuthentificationSource getAuthSource()
	{
		return AuthentificationSource.atm_version;
	}

	@Override
	protected GetAuthDataOperation createGetAuthDataOperation(Map<String, Object> data) throws CanNotLoginException, LoginOrPasswordWrongLoginExeption
	{
		return new GetAuthDataFromMobileBankOperation(data);
	}
}
