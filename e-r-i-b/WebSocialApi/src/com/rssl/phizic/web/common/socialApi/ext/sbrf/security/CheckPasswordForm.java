package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * форма проверки пароля
 * @author Pankin
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class CheckPasswordForm extends ActionFormBase
{
	private String password;
	private Long badAuthDelay;

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Long getBadAuthDelay()
	{
		return badAuthDelay;
	}

	public void setBadAuthDelay(Long badAuthDelay)
	{
		this.badAuthDelay = badAuthDelay;
	}
}
