package com.rssl.phizic.operations.userprofile;

import java.util.Map;

/**
 * @author basharin
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginSettings extends Settings
{
	private Map<String, String> loginSettings;

	LoginSettings(Map<String, String> securitySettings)
	{
		this.loginSettings = securitySettings;
	}

	protected Map<String, String> getSettings()
	{
		return loginSettings;
	}

	public String getNewLogin()
	{
		return loginSettings.get("newLogin");
	}
}
