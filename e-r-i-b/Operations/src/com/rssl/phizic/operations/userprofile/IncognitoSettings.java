package com.rssl.phizic.operations.userprofile;

import java.util.Map;

/**
 * @author EgorovaA
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 */
public class IncognitoSettings extends Settings
{
	private Map<String, String> incognitoSettings;

	IncognitoSettings(Map<String, String> securitySettings)
	{
		this.incognitoSettings = securitySettings;
	}

	protected Map<String, String> getSettings()
	{
		return incognitoSettings;
	}
}
