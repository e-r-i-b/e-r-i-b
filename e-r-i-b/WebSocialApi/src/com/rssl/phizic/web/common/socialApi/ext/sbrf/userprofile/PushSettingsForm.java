package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * форма настройки Push-уведомлений
 * @author Pankin
 * @ created 05.08.13
 * @ $Author$
 * @ $Revision$
 */
public class PushSettingsForm extends ActionFormBase
{
	private String securityToken;

	public String getSecurityToken()
	{
		return securityToken;
	}

	public void setSecurityToken(String securityToken)
	{
		this.securityToken = securityToken;
	}
}
