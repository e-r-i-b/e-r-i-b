package com.rssl.phizic.web.common.client.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author eMakarov
 * @ created 25.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmSmsLoginForm extends ActionFormBase
{
	private String password;
	private Boolean needGetPassword = false;

	public String getPassword ()
	{
		return password;
	}

	public void setPassword ( String password )
	{
		this.password = password;
	}

	public Boolean getNeedGetPassword()
	{
		return needGetPassword;
	}

	public void setNeedGetPassword(Boolean needGetPassword)
	{
		this.needGetPassword = needGetPassword;
	}
}
