package com.rssl.phizic.web.common.mobile.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author osminin
 * @ created 05.08.13
 * @ $Author$
 * @ $Revision$
 *
 * форма аутентификации пользователя МАПИ
 */
public class MobilePostCSALoginForm extends ActionFormBase
{
	private String token;

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
}
