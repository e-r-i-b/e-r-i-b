package com.rssl.phizic.operations.userprofile;

import java.util.Map;

/**
 * @author basharin
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class IPasPasswordSettings extends Settings
{
	private Map<String, String> passwordSettings;

	IPasPasswordSettings(Map<String, String> securitySettings)
	{
		this.passwordSettings = securitySettings;
	}

	protected Map<String, String> getSettings()
	{
		return passwordSettings;
	}
}
