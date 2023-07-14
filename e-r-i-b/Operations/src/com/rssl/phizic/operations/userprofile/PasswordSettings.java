package com.rssl.phizic.operations.userprofile;

import java.util.Map;

/**
 * @author basharin
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class PasswordSettings extends Settings
{
	private Map<String, String> passwordSettings;

	PasswordSettings(Map<String, String> securitySettings)
	{
		this.passwordSettings = securitySettings;
	}

	protected Map<String, String> getSettings()
	{
		return passwordSettings;
	}

	public String getNewPassword()
	{
		return passwordSettings.get("newPassword");
	}
}
