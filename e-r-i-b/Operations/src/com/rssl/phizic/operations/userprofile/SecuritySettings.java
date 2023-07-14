package com.rssl.phizic.operations.userprofile;

import java.util.Map;
import java.util.Properties;
import java.util.HashMap;

/**
 * @ author: filimonova
 * @ created: 21.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class SecuritySettings extends Settings
{
	private Map<String, String> securitySettings;

	SecuritySettings(Map<String, String> securitySettings)
	{
		this.securitySettings = securitySettings;
	}

	protected Map<String, String> getSettings()
	{
		return securitySettings;
	}

	public String getAuthConfirmType()
	{
		return securitySettings.get("authConfirmType");
	}

	public String getDeliveryType()
	{
		return securitySettings.get("deliveryType");
	}

	public String getUserConfirmType()
	{
		return securitySettings.get("userConfirmType");
	}
}
