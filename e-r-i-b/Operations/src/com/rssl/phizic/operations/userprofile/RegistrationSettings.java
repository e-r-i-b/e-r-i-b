package com.rssl.phizic.operations.userprofile;

import java.util.Map;

/**
 * объект для подтверждения удаленной регистрации изнутри СБОЛ.
 *
 * @author bogdanov
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class RegistrationSettings extends Settings
{
	private Map<String, String> settings;

	public RegistrationSettings (Map<String, String> fields)
	{
		settings = fields;
	}

	protected Map<String, String> getSettings()
	{
		return settings;
	}
}
