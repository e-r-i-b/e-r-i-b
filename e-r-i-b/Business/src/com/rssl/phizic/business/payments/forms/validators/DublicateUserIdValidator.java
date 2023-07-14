package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.security.SecurityDbException;

/**
 * @author Evgrafov
 * @ created 14.09.2006
 * @ $Author: omeliyanchuk $
 * @ $Revision: 16029 $
 */

public class DublicateUserIdValidator extends FieldValidatorBase
{
	private static final SecurityService securityService = new SecurityService();
	public static final String SCOPE_PARAM = "scope";

	private String scope;

	public DublicateUserIdValidator()
	{
	}

	public void setParameter(String name, String value)
	{
		if(SCOPE_PARAM.equals(name))
			scope = value;
	}

	public String getParameter(String name)
	{
		if (SCOPE_PARAM.equals(name))
			return scope;

		return null;
	}

	public DublicateUserIdValidator(String scope, String message)
	{
		this.scope = scope;
		setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if(isValueEmpty(value))
			return true;

		return findLogin(value) == null;
	}

	private CommonLogin findLogin(String value)
	{
		if(scope == null)
			throw new NullPointerException("Не установлен параметр " + SCOPE_PARAM);
		try
		{
			return securityService.getLogin(value.trim(), scope);
		}
		catch (SecurityDbException e)
		{
			throw new RuntimeException(e);
		}
	}
}